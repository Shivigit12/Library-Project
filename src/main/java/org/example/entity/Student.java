package org.example.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int studentId;

    private int age;

    private String name;

    private String country;

    private String email;

    private long phoneNumber;

    private Date createdOn;

    private Date updatedOn;
    @OneToOne
    @JoinColumn
    private Card card;

    public Student() {
    }

    public Student(int studentId, int age, String name, String country, String email, long phoneNumber, Date createdOn, Date updatedOn, Card card) {
        this.studentId = studentId;
        this.age = age;
        this.name = name;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.card = card;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", card=" + card +
                '}';
    }
}
