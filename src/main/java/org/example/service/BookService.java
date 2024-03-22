package org.example.service;

import org.example.dto.CreateBookRequest;
import org.example.dto.SearchBookRequest;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Genre;
import org.example.entity.Student;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public void assignBookToStudent(Book book, Student student){
        bookRepository.assignBookToStudent(book.getId(), student);
    }

    public void unassignBookFromStudent(Book book){
        bookRepository.unassignBook(book.getId());
    }


    /*
    I want to search book by name
    genre
    id
     */

    public List<Book> search(SearchBookRequest searchBookRequest) throws Exception{
        boolean isValidRequest = searchBookRequest.validate();
        if(!isValidRequest)
            throw new Exception("Invalid request");
        switch (searchBookRequest.getSearchKey()) {
            case "name":
                return bookRepository.findByName(searchBookRequest.getSearchValue());
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            case "id":
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);
            default:
                throw new Exception("Invalid search key");
        }
    }
//    public List<Book> search( String key,  String value) throws Exception{
//
//        switch (key) {
//            case "name":
//                return bookRepository.findByName(value);
//            case "genre":
//                return bookRepository.findByGenre(Genre.valueOf(value));
//            case "id":
////                return new ArrayList<Book>(bookRepository.findAllById(Integer.valueOf(value)).get());
//        }
//        return null;
//    }

//    public boolean exists(String name) {
//        Optional<Book> book1 = bookRepository.findByName(name);
//        return book1.isPresent();
//    }

    public Book addBook(CreateBookRequest createBookRequest) {
        Book book = createBookRequest.to();
        Author author = authorService.createOrGet(book.getAuthor());
        book.setAuthor(author);
        return bookRepository.save(book);
    }



    public Book deleteTheBook(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        bookRepository.deleteById(id);
        return book;
    }

}
