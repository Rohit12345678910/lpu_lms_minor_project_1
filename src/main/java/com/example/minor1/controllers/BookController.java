package com.example.minor1.controllers;

import com.example.minor1.domain.Book;
import com.example.minor1.dtos.CreateBookRequest;
import com.example.minor1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    @PostMapping("/book")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        bookService.createBook(createBookRequest.to());
    }

    // In place of difing multiple get requests, below method will work with all the parameters with which we want to search
    @GetMapping("/book")
    public List<Book> searchBook(@RequestParam("key") String key, @RequestParam("value") String value){

        return bookService.find(key, value);
    }
}
