package org.example.controller;

import org.example.entity.Author;
import org.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/getAuthors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
    @PostMapping("/create")
    public ResponseEntity addAuthor(@RequestBody Author author) {
        if(author == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else if(authorService.exists(author.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Author already exists");
        }
        authorService.create(author);
        return new ResponseEntity<>("Author created",HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity updateAuthor(@RequestBody Author author) {
        authorService.update(author);
        return new ResponseEntity<>("Author updated", HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        authorService.deleteAuthorById(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
