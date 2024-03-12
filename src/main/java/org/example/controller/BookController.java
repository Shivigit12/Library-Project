package org.example.controller;

import org.example.entity.Book;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }
    @PostMapping("/create")
    public ResponseEntity createBook(@RequestBody Book book) {
        if(book == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else if (bookService.exists(book.getName())) {
            return new ResponseEntity<>("Book is already present", HttpStatus.CONFLICT);
        }
        bookService.addBook(book);
        return new ResponseEntity<>("The given book has been added", HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity updateBook(@RequestBody Book book) {
        if(book == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        bookService.updateBook(book);
        return new ResponseEntity("Book details has been updated", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable int id) {
        bookService.deleteTheBook(id);
        return new ResponseEntity<>("Book has been removed",HttpStatus.NO_CONTENT);
    }
}
