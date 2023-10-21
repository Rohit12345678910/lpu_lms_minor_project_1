package com.example.minor1.services;

import com.example.minor1.domain.*;
import com.example.minor1.dtos.IntiateTransactionRequest;
import com.example.minor1.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    AdminService adminService;
    @Autowired
    BookService bookService;
    @Value("${student.allowed.max_books}") // from application.properties file
    int maxBooksAllowed;
    @Value("${student.allowed.duration}")  // student can keep the book for maximum of 15 days
    int duration;
    public String intiateTxn(IntiateTransactionRequest intiateTransactionRequest){
        /*
        * Issuence -
        * 1. Check if book is available or not and student is valid or not and we need to check if student has reached the maximum limit of issuence
        * 2. entry in the Txn table
        * 3 . assign book to student -> update student table
        * */

        /*
         * Return -
         * 1. Check if book is available to student or not and student is valid or not
         * 2. entry in the Txn table
         * 3. due date check and fine calculation
         * 4 .If there is no fine, de-allocate the book from student
         *
         * */

        return intiateTransactionRequest.getTransactionType() ==
                TransactionType.ISSUE?issueBook(intiateTransactionRequest)
                :returnBook(intiateTransactionRequest);

    }
    private String issueBook(IntiateTransactionRequest request){
        Student student = studentService.find(request.getStudentId());  // define find() in Student Service
        Admin admin = adminService.find(request.getAdminId()); // define find() in Admin Service
        List<Book> bookList = bookService.find("id", String.valueOf(request.getBookId()));  // Use existing find
        Book book = (bookList != null && bookList.size()>0)?bookList.get(0):null;

        if(student == null || admin == null || book == null || book.getStudent() != null || student.getBookList().size() > maxBooksAllowed){  // if any of them not created or book is already assigned to some other student or 3 books already issued to student
            throw new RuntimeException("Invalid Request");
        }
        Transaction transaction = null;
        try {
            transaction = Transaction.builder()    // create transaction
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(request.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();
            transactionRepository.save(transaction);  //save the transaction
            book.setStudent(student);
            bookService.createBook(book); // it will update the record in book table with student_id to whome the book has been assigned
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        }
        catch (Exception exception){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }
        finally {
            transactionRepository.save(transaction);
        }
        return transaction.getTxnId();
    }
    private String returnBook(IntiateTransactionRequest request){
        Student student = studentService.find(request.getStudentId());  // define find() in Student Service
        Admin admin = adminService.find(request.getAdminId()); // define find() in Admin Service
        List<Book> bookList = bookService.find("id", String.valueOf(request.getBookId()));  // Use existing find
        Book book = (bookList != null && bookList.size()>0)?bookList.get(0):null;

        if(student == null || admin == null || book == null || book.getStudent() == null || book.getStudent().getId() != student.getId() ){  // if any of them not created or book is not allocated to student or the same person to whome book was issued is not returning the book
            throw new RuntimeException("Invalid Request");
        }

        // get the last issue transaction
        Transaction issuanceTxn =  transactionRepository.findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(student, book, TransactionType.ISSUE);
        if(issuanceTxn == null){
            throw  new RuntimeException("Invalid request");
        }
        Transaction  transaction = null;

        try {
            int fine = calculateFine(issuanceTxn.getCreatedOn());
            transaction = Transaction.builder()    // create transaction
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(request.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .fine(fine)
                    .build();
            transactionRepository.save(transaction);
            if (fine == 0) {
                book.setStudent(null);
                bookService.createBook(book);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            }
        }
        catch (Exception ex){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }
        finally {
            transactionRepository.save(transaction);
        }
        return transaction.getTxnId();


    }
    private int calculateFine(Date issuance){
        long issueTimeInMillis = issuance.getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        long diff = currentTimeInMillis - issueTimeInMillis;
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(days>duration){
            return (int)(days-duration)*1; // 1 rupees per day fine
        }
        return 0;

    }

    public void payFine(int amount, int studentId, String transactionId) {
        Transaction returnTxn = transactionRepository.findByTxnId(transactionId);

        Book book = returnTxn.getBook();
        if(returnTxn.getFine() == amount && book.getStudent() != null && book.getStudent().getId() == studentId){
            returnTxn.setTransactionStatus(TransactionStatus.SUCCESS);
            book.setStudent(null);
            bookService.createBook(book);
            transactionRepository.save(returnTxn);
        }
        else {
            throw new RuntimeException("Invalid Request");
        }
    }
}
