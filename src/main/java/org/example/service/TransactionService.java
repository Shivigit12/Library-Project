package org.example.service;

import org.example.entity.Book;
import org.example.entity.Card;

import org.example.entity.Transaction;
import org.example.exception.CardNotFoundException;
import org.example.exception.TransactionException;
import org.example.repository.BookRepository;
import org.example.repository.CardRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;



import static org.example.entity.CardStatus.DEACTIVATED;

@Service
public class TransactionService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;


    public void issueBooks(int bookId, int cardId) throws TransactionException, CardNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new TransactionException("Book is not found"));


        Card card = cardRepository.findById(cardId)
        .orElseThrow(()-> new TransactionException("card id not found"));
        if(card.getCardStatus() == DEACTIVATED)
            throw new CardNotFoundException("Card is not active");


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
