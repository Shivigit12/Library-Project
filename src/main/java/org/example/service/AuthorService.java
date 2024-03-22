package org.example.service;

import org.example.entity.Author;
import org.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authorRepository.findAll());

    }


    public Author createOrGet(Author author) {
        Author authorFromDB = this.authorRepository.findByEmail(author.getEmail());
        if(authorFromDB != null)
            return authorFromDB;
        return this.authorRepository.save(author);
    }
}
