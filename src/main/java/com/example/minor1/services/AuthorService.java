package com.example.minor1.services;

import com.example.minor1.domain.Author;
import com.example.minor1.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    public Author getOrCreateAuthor(Author author){

        Author retrivedAuthor = authorRepository.findByEmail(author.getEmail());
        if(retrivedAuthor == null) {
            return authorRepository.save(author);
        }
        return retrivedAuthor;
    }
}
