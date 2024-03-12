package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity

public class Transaction {
    @Id
    @GeneratedValue

    private int id;
    @ManyToOne
    @JoinColumn
    private Card card;
    @ManyToOne
    @JoinColumn
    private Book book;

    private LocalDate transactionDate;

    private LocalDate bookDueDate;

    private boolean isIssued;

    private boolean isReturned;

    private int fineAmount;

    private String status;

    private Date createdOn;

    private Date updatedOn;

    public Transaction() {
    }

    public Transaction(int id, Card card, Book book, LocalDate transactionDate, LocalDate bookDueDate, boolean isIssued, boolean isReturned, int fineAmount, String status, Date createdOn, Date updatedOn) {
        this.id = id;
        this.card = card;
        this.book = book;
        this.transactionDate = transactionDate;
        this.bookDueDate = bookDueDate;
        this.isIssued = isIssued;
        this.isReturned = isReturned;
        this.fineAmount = fineAmount;
        this.status = status;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getBookDueDate() {
        return bookDueDate;
    }

    public void setBookDueDate(LocalDate bookDueDate) {
        this.bookDueDate = bookDueDate;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", card=" + card +
                ", book=" + book +
                ", transactionDate=" + transactionDate +
                ", bookDueDate=" + bookDueDate +
                ", isIssued=" + isIssued +
                ", isReturned=" + isReturned +
                ", fineAmount=" + fineAmount +
                ", status='" + status + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
