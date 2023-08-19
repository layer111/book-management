package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.BookCart;
import com.example.bookmanagement.model.Review;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.repository.ReviewRepository;
import com.example.bookmanagement.service.BookCategoryService;
import com.example.bookmanagement.service.BookService;
import com.example.bookmanagement.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {

    private final BookService bookService;

    private final UserService userService;

    private final ReviewRepository reviewRepository;

    private final BookCategoryService bookCategoryService;

    public ReviewController(BookService bookService, UserService userService, ReviewRepository reviewRepository, BookCategoryService bookCategoryService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
        this.bookCategoryService = bookCategoryService;
    }

    @PostMapping("/reviews")
    public String saveBook(@RequestParam Long id, @RequestParam String comment, @RequestParam Long rating, Model model, Authentication authentication){
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        Book book = bookService.findById(id);
        Review review = new Review(rating, comment, book, user);
        review = reviewRepository.save(review);
        book.getReviews().add(review);
        book = bookService.saveBook(book);
        model.addAttribute("book", book);
        model.addAttribute("categories", bookCategoryService.findAll());
        model.addAttribute("mode", "view");

        if(authentication != null) {
            List<BookCart> bookCarts = user.getCart().getBookCarts();

            Long num = -1L;
            boolean isBuyed = false;
            for (BookCart bookCart : bookCarts) {
                if (bookCart.getBook().getId().equals(id)) {
                    num = bookCart.getNumber();
                    isBuyed = true;
                }
            }

            model.addAttribute("isBuy", isBuyed);
            model.addAttribute("number", num);
            model.addAttribute("isAdmin", user.getAdmin());
        }

        return "create_book";
    }

}
