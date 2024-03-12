package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity

public class Card {
    @Id
    @GeneratedValue

    private int id;
    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;

    private String email;
    @UpdateTimestamp
    private Date updatedOn;
    @CreationTimestamp
    private Date createdOn;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Transaction> transactions;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Book> books;

    public Card() {
    }

    public Card(int id, Student student, CardStatus cardStatus, String email, Date updatedOn, Date createdOn, List<Transaction> transactions, List<Book> books) {
        this.id = id;
        this.student = student;
        this.cardStatus = cardStatus;
        this.email = email;
        this.updatedOn = updatedOn;
        this.createdOn = createdOn;
        this.transactions = transactions;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Enum getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", student=" + student +
                ", cardStatus=" + cardStatus +
                ", email='" + email + '\'' +
                ", updatedOn=" + updatedOn +
                ", createdOn=" + createdOn +
                ", transactions=" + transactions +
                ", books=" + books +
                '}';
    }
}
