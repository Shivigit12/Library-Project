package org.example.controller;

import org.example.entity.Author;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/getAllAuthors")
    public ResponseEntity<?> getAllAuthors() {

        List<Author> authorsList = authorService.getAllAuthors();
        if(authorsList == null)
            return new ResponseEntity<>("No authors Present", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(authorsList, HttpStatus.OK);
    }

}
