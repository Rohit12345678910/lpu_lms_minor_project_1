package com.example.minor1.repositories;

import com.example.minor1.domain.Book;
import com.example.minor1.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByGenre(Genre genre);
    List<Book> findByName(String bookName);
    @Query("select b from Book b, Author a where b.my_author.id= a.id and a.name = ?1")
    List<Book> findByMy_author_name(String authorName);
}
