package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.BookCart;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.service.BookService;
import com.example.bookmanagement.service.CartService;
import com.example.bookmanagement.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    private final BookService bookService;

    public CartController(CartService cartService, UserService userService, BookService bookService) {
        this.cartService = cartService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        List<BookCart> bookCarts = user.getCart().getBookCarts();
        Double totalPrice = 0.00;
        for(BookCart bookCart: bookCarts) {
            totalPrice += bookCart.getNumber() * bookCart.getBook().getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("bookCarts", bookCarts);
        return "cart";
    }

    @PostMapping("/cart")
    public String addCart(Model model, @RequestParam Long id, @RequestParam Long number,  Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        Book book = bookService.findById(id);
        BookCart bookCart = new BookCart();
        bookCart.setCart(user.getCart());
        bookCart.setBook(book);
        bookCart.setNumber(number);
        user.getCart().getBookCarts().add(bookCart);
        userService.update(user);
        Double totalPrice = 0.00;

        List<BookCart> bookCarts = user.getCart().getBookCarts();
        for(BookCart bc: bookCarts) {
            totalPrice += bc.getNumber() * bc.getBook().getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("bookCarts", bookCarts);
        return "cart";
    }

    @GetMapping("/cart/{id}")
    public String deleteBookInCart(Model model, @PathVariable Long id, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        List<BookCart> bookCarts = user.getCart().getBookCarts();
        bookCarts.removeIf(bookCart -> bookCart.getBook().getId().equals(id));
        userService.update(user);
        Double totalPrice = 0.00;
        for(BookCart bookCart: bookCarts) {
            totalPrice += bookCart.getNumber() * bookCart.getBook().getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("bookCarts", bookCarts);
        return "cart";
    }

}
