package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Genre;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {
    @NotBlank
    private String name;
    @NotBlank
    private Genre genre;
    private int pages;
    @NotBlank
    private String authorName;
    private String authorCountry;
    @NotBlank
    private String authorEmail;

    public Book to() {

        return Book.builder()
                .name(name)
                .pages(pages)
                .genre(genre)
                .author(Author.builder()
                        .name(authorName)
                        .country(authorCountry)
                        .email(authorEmail).build()
                ).build();
    }
}
