package com.example.bookmanagement.model;

import javax.persistence.*;

@Entity
public class BookCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @Column(nullable = false)
    private Long number;

    public Cart getCart() {
        return cart;
    }

    public Book getBook() {
        return book;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

}
