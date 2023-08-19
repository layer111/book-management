package com.example.bookmanagement.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Cart() {

    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BookCart> bookCarts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookCart> getBookCarts() {
        return bookCarts;
    }

    public void setBookCarts(List<BookCart> bookCarts) {
        this.bookCarts = bookCarts;
    }
}
