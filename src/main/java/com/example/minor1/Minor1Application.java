package com.example.minor1;

import com.example.minor1.domain.Book;
import com.example.minor1.domain.Genre;
import com.example.minor1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Minor1Application implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;
	public static void main(String[] args) {
		SpringApplication.run(Minor1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Book book = Book.builder()
//				.name("Intro to programming")
//				.genre(Genre.Matematic)
//				.build();
//		bookRepository.save(book);
	}
}
