package org.example.entity;

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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int studentId;

    private int age;

    private String name;

    private String country;

    private String email;

    private String phoneNumber;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
    @OneToOne
    @JoinColumn
    private Card card;
    @OneToMany(mappedBy = "student")
    private List<Book> bookList;
    @OneToMany(mappedBy = "student")
    private List<Transaction> transactionList;
    private Date validity;


}
