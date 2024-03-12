package org.example.service;

import org.example.entity.Book;
import org.example.entity.Card;
import org.example.entity.CardStatus;
import org.example.entity.Transaction;
import org.example.repository.BookRepository;
import org.example.repository.CardRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.example.entity.CardStatus.ACTIVATED;
import static org.example.entity.CardStatus.DEACTIVATED;

@Service
public class TransactionService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;


    public void issueBooks(int bookId, int cardId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Book is not found"));


        Card card = cardRepository.findById(cardId)
        .orElseThrow(()-> new RuntimeException("card id not found"));
        if(card.getCardStatus() == DEACTIVATED)
            throw new RuntimeException("Card is not active");

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setIssued(true);
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDate.now());
        book.setDueDate(book, 14);
        transaction.setFineAmount(200);
        transaction.setReturned(false);
        transaction.setStatus("book is issued");
        transactionRepository.save(transaction);

        book.setAvailable(false);

    }

}
