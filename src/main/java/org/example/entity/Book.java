package org.example.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity

public class Book {
    @Id
    @GeneratedValue

    private int id;

    private String name;
    @ManyToOne
    private Author author;

    private int numberOfPages;

    private String language;

    private boolean available;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int ISBNNumber;

    private Date publishedDate;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    @ManyToOne
    @JoinColumn
    private Card card;

    public Book() {
    }

    public Book(int id, String name, Author author, int numberOfPages, String language, boolean available, Genre genre, int ISBNNumber, Date publishedDate, List<Transaction> transactions, Card card) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.language = language;
        this.available = available;
        this.genre = genre;
        this.ISBNNumber = ISBNNumber;
        this.publishedDate = publishedDate;
        this.transactions = transactions;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getISBNNumber() {
        return ISBNNumber;
    }

    public void setISBNNumber(int ISBNNumber) {
        this.ISBNNumber = ISBNNumber;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", numberOfPages=" + numberOfPages +
                ", language='" + language + '\'' +
                ", available=" + available +
                ", genre='" + genre + '\'' +
                ", ISBNNumber=" + ISBNNumber +
                ", publishedDate=" + publishedDate +
                ", transactions=" + transactions +
                ", card=" + card +
                '}';
    }

    public void setDueDate(Date time) {
    }

    public void setDueDate(Book book, int i) {
    }
}
