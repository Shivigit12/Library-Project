package org.example.service;

import org.example.entity.Book;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();

    }

    public boolean exists(String name) {
        Optional<Book> book1 = bookRepository.findByName(name);
        return book1.isPresent();
    }

    public void addBook(Book book) {
        bookRepository.findById(book.getId());
        bookRepository.save(book);
    }

    public void updateBook(Book book) {
        Book book1 = bookRepository.findById(book.getId())
                .orElseThrow(()-> new RuntimeException("Student id not found"));
        book1.setId(book.getId());
        book1.setAuthor(book.getAuthor());
        book1.setAvailable(book.isAvailable());
        book1.setName(book.getName());
        book1.setGenre(book.getGenre());
        book1.setLanguage(book.getLanguage());
        book1.setISBNNumber(book.getISBNNumber());
        book1.setNumberOfPages(book.getNumberOfPages());
        book1.setPublishedDate(book.getPublishedDate());
        bookRepository.save(book1);


    }

    public void deleteTheBook(int id) {
        bookRepository.deleteById(id);
    }
    public void setDueDate(Book book, int daysUntilDue) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilDue);
        book.setDueDate(calendar.getTime());
    }
}
