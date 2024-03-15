package org.example.repository;

import org.example.entity.Book;
import org.example.entity.Card;
import org.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByBook(Optional<Book> book);

    List<Transaction> findByCard(Optional<Card> card);
}
