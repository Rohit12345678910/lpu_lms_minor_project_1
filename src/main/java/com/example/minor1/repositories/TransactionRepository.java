package com.example.minor1.repositories;

import com.example.minor1.domain.Book;
import com.example.minor1.domain.Student;
import com.example.minor1.domain.Transaction;
import com.example.minor1.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    //@Query("select * from transaction where student_d = ?1 and book_id = ?2 and transactionType=?3 order by id desc limit 1")
    Transaction findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student,
                                                                       Book book,
                                                                       TransactionType transactionType);

    Transaction findByTxnId(String txnId);
}
