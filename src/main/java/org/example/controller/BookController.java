package org.example.controller;

import org.example.dto.CreateBookRequest;
import org.example.dto.SearchBookRequest;
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
    public List<Book> getBooksBySearching(@RequestBody SearchBookRequest searchBookRequest) throws Exception {
        return bookService.search(searchBookRequest);
    }


    @GetMapping("/getAllBooks")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }
    @PostMapping("/create")
    public ResponseEntity <Book>createBook(@RequestBody CreateBookRequest createBookRequest) {

         return new ResponseEntity<>(bookService.addBook(createBookRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book book = bookService.deleteTheBook(id);
        return new ResponseEntity<>(book,HttpStatus.NO_CONTENT);
    }
}
