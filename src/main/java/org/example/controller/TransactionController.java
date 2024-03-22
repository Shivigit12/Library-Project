package org.example.controller;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/transact")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/issueBook")
    public ResponseEntity<String> issueBook(@RequestParam("name") String bookName, @RequestParam("studentId")int studentId) throws Exception {
        transactionService.issueBooks(bookName, studentId);
        return new ResponseEntity<>("Book has been issued",HttpStatus.OK);
    }
    @PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam("bookId") int bookId, @RequestParam("studentId")int studentId) throws Exception {
        transactionService.returnBook(bookId, studentId);
        return new ResponseEntity<>(bookId+" "+studentId+" ", HttpStatus.OK);
    }


}
