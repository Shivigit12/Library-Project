package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.CreateBookRequest;
import org.example.dto.SearchBookRequest;
import org.example.entity.Book;
import org.example.exception.BookNotFoundException;
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

    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@Valid @RequestBody SearchBookRequest searchBookRequest) throws BookNotFoundException, Exception {
        List<Book> books =  bookService.search(searchBookRequest);
        return ResponseEntity.ok(books);

    }

    // Backward-compatible: avoid GET bodies; accept query params.
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksGet(
            @RequestParam("searchKey") String searchKey,
            @RequestParam("searchValue") String searchValue,
            @RequestParam(value = "operator", defaultValue = "=") String operator
    ) throws BookNotFoundException, Exception {
        SearchBookRequest req = SearchBookRequest.builder()
                .searchKey(searchKey)
                .searchValue(searchValue)
                .operator(operator)
                .build();
        List<Book> books = bookService.search(req);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/getAllBooks")
    public List<Book> getBooks() throws BookNotFoundException {
        List<Book> responseBookList = null;
        responseBookList = bookService.getAllBooks();
        return responseBookList;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        return new ResponseEntity<>(bookService.addBook(createBookRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        if(id < 1 || id > 99999999)
            return new ResponseEntity<>("Please provide a valid id",HttpStatus.BAD_REQUEST);
        Book book = bookService.deleteTheBook(id);
        return new ResponseEntity<>("Book Id deleted successfully",HttpStatus.OK);
    }
}
