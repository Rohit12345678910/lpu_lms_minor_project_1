package com.example.minor1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(value = EnumType.STRING)  //Enumerated annot. is used to define , how we want to store it in database. EnumType can have value ordinal or String, default value is ordinal (i.e. in database it will store as numbers)
    private Genre genre;

    @CreationTimestamp // we want to supply the value of this field automatically by hibernate as and when book will be created
    private Date createdOn;
    @UpdateTimestamp//  we want to supply the value of this field automatically by hibernate as and when book will be updated
    private Date updatedOn;

    @JoinColumn  // to define join between Book and author, author(author id) will be added as foreign key in book
    @ManyToOne   // Many books can be written by one author
    @JsonIgnoreProperties({"bookList"}) // when fetching the data, we donot want to create an infinite loop. Here it will ignore the bookList from Author class when fetching the details of Author
    private Author my_author;

    @JoinColumn //// to define join between Book and Student, Student(student id) will be added as foreign key in book
    @ManyToOne
    @JsonIgnoreProperties({"bookList"})
    private Student student;

    @OneToMany(mappedBy = "book")
    private List<Transaction> transactionList;

}
