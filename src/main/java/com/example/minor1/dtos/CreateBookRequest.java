package com.example.minor1.dtos;

import com.example.minor1.domain.Author;
import com.example.minor1.domain.Book;
import com.example.minor1.domain.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;
    @NotNull   // We can use NotBlank for strings only
    private Genre genre;
    @NotBlank
    private String authorName;
    @NotBlank
    private String authorEmail;

    public Book to(){
        return Book.builder()
                .name(this.name)
                .genre(this.genre)
                .my_author(
                        Author.builder()
                                .name(this.authorName)
                                .email(this.authorEmail)
                                .build()
                )
                .build();
    }

}
