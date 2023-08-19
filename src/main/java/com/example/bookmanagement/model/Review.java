package com.example.bookmanagement.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long star;

    private String comment;

    @ManyToOne
    @JoinColumn(name="book", nullable=false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="user", nullable=false)
    private User user;

    public Review() {

    }

    public Review(Long star, String comment, Book book, User user) {
        this.star = star;
        this.comment = comment;
        this.book = book;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStar() {
        return star;
    }

    public void setStar(Long star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
