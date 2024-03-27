package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String name;
    @ManyToOne
    @JoinColumn
    private Author author;
    @ManyToOne
    @JoinColumn
    private Student student;
    private int pages;

    private String language;

    private boolean available;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int ISBNNumber;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Transaction> transactions;
    @ManyToOne
    @JoinColumn
    private Card card;

    public void setDueDate(Date time) {
    }

    public void setDueDate(Book book, int i) {
    }
}
