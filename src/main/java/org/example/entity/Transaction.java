package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String externalTxnId;
    @ManyToOne
    @JoinColumn
    private Card card;
    @ManyToOne
    @JoinColumn
    private Book book;
    @ManyToOne
    @JoinColumn
    private Student student;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    @CreationTimestamp
    private Date transactionTime;
    private Double fineAmount;
    @UpdateTimestamp
    private Date updatedOn;


}
