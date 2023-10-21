package com.example.minor1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(unique = true, nullable = false)  // If adding two author with the same email, it will give the error
    private String email;
    @CreationTimestamp
    private Date createdOn;
    @OneToMany(mappedBy = "my_author") // This is the back reference. In database we will not be storing bookList coresponding to a author. To internally executes the queries and findout the list of books corresponding to a autor we need to map back-refrence (my_author is the field name in Book class) from Book entity. Otherwise bookList will be null as we are not stroing list of books in Author table.
    @JsonIgnoreProperties({"my_author"})
    private List<Book>  bookList;

}
