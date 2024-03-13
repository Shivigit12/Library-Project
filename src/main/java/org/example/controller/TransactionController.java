package org.example.controller;


import org.example.entity.Transaction;
import org.example.exception.CardNotFoundException;
import org.example.exception.TransactionException;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transact")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/issueBook/{bookId}, {cardId}")
    public ResponseEntity<?> issueBook(@PathVariable int bookId, @PathVariable int cardId) throws TransactionException, CardNotFoundException {
        transactionService.issueBooks(bookId, cardId);
        return new ResponseEntity<>("Book has been issued",HttpStatus.OK);
    }
    @PostMapping("/returnBook/{bookId}/{cardId}")
    public ResponseEntity<?> returnBook(@PathVariable int bookId, @PathVariable int cardId) throws TransactionException, CardNotFoundException {
        transactionService.returnBook(bookId, cardId);
        return new ResponseEntity<>("Book has been returned", HttpStatus.OK);
    }
    @GetMapping("/getDetails/{transactionId}")
    public ResponseEntity<Transaction> getTransactionDetails(@PathVariable int id) throws TransactionException {
        transactionService.getTransactionDetails(id);
        return new ResponseEntity<Transaction>(HttpStatus.OK);
    }
}
