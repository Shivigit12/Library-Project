package org.example.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.example.entity.Book;
import org.example.entity.Genre;
import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b where b.name like '%?1'")
    public List<Book> findByName(String name);
    @Query("select b from Book b where genre = ?1")
    public List<Book> findByGenre(Genre genre);
    @Query("select b from Book b where genre = ?1")
    public List<Book> findByGenreString(String genre);
    @Modifying // for DML support
    @Transactional // for updating any data
    @Query("update Book b set b.student = ?2 where b.id = ?1 and b.student is null")
    void assignBookToStudent(int bookId, Student student);

    @Modifying // for DML support
    @Transactional // for updating any data
    @Query("update Book b set b.student = null where b.id = ?1")
    void unassignBook(int bookId);

}
