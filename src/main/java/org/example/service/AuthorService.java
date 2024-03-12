package org.example.service;

import org.example.entity.Author;
import org.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public List<Author> getAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        return authorList;
    }

    public boolean exists(String email) {
        Optional<Author> author = authorRepository.findByEmail(email);
        return author.isPresent();
    }

    public void create(Author author) {
        authorRepository.findById(author.getId());
        authorRepository.save(author);
    }

    public void update(Author author) {
        Author author1 = authorRepository.findById(author.getId())
                .orElseThrow(()-> new RuntimeException("Author not found"));
        author1.setName(author.getName());
        author1.setAge(author.getAge());
        author1.setCountry(author.getCountry());
        author1.setEmail(author.getEmail());
        author1.setBooksWritten(author.getBooksWritten());
        authorRepository.save(author1);
    }

    public void deleteAuthorById(int id) {
        authorRepository.deleteById(id);
    }
}
