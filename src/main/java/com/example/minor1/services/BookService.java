package com.example.minor1.services;

import com.example.minor1.domain.Author;
import com.example.minor1.domain.Book;
import com.example.minor1.domain.Genre;
import com.example.minor1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookRepository bookRepository;
    public void createBook(Book book){

        Author bookAuthor = book.getMy_author(); // here bookAuthor will not be having id and other auto generated fields
        Author savedAuthor = authorService.getOrCreateAuthor(bookAuthor); // now it will be having all the fields

        // map author to book
        book.setMy_author(savedAuthor);

        bookRepository.save(book);

    }

    public List<Book> find(String key, String value) {
        switch (key) {
            case "id":
                Optional<Book> book = bookRepository.findById(Integer.parseInt(value));
                if(book.isPresent()){
                    return Arrays.asList(book.get());
                }
                else {
                    return new ArrayList<>(); // empty list
                }
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(value));
            case "author_name":
                return bookRepository.findByMy_author_name(value);
            case "name":
                return bookRepository.findByName(value);
            default:
                throw new RuntimeException("Search key not valie: "+key);
        }
    }
}
