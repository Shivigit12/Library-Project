package org.example.controller;


import org.example.entity.Transaction;
import org.example.exception.CardNotFoundException;
import org.example.exception.TransactionException;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transact")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/issueBook/{bookId}, {cardId}")
    public ResponseEntity<String> issueBook(@PathVariable int bookId, @PathVariable int cardId) throws TransactionException, CardNotFoundException {
        transactionService.issueBooks(bookId, cardId);
        return new ResponseEntity<>("Book has been issued",HttpStatus.OK);
    }
    @PostMapping("/returnBook/{bookId}/{cardId}")
    public ResponseEntity<String> returnBook(@PathVariable int bookId, @PathVariable int cardId) throws TransactionException, CardNotFoundException {
        transactionService.returnBook(bookId, cardId);
        return new ResponseEntity<>("Book has been returned", HttpStatus.OK);
    }
    @GetMapping("/getDetails/{transactionId}")
    public ResponseEntity<Transaction> getTransactionDetails(@PathVariable int id) throws TransactionException {
        transactionService.getTransactionDetails(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/transactionDetails/{bookId}")
    public ResponseEntity<List<Transaction>> getTransactionDetailsByBookId(@PathVariable int bookId) {
        List<Transaction> transactionList = transactionService.getDetailsByBookId(bookId);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @GetMapping("/transactionDetails/{cardId}")
    public ResponseEntity<List<Transaction>> getTransactionDetailsByCardId(@PathVariable int cardId) {
         List<Transaction> transactionList1 = transactionService.getDetailsByCardId(cardId);
        return new ResponseEntity<>(transactionList1, HttpStatus.OK);
    }

}
