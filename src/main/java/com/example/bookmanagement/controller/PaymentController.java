package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.BookCart;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {

    private final UserService userService;

    public PaymentController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/payment")
    public String payment(Model model, @RequestParam String address, @RequestParam String paymentType, @RequestParam String shippingType, Authentication authentication) {
        model.addAttribute("address", address);
        model.addAttribute("paymentType", paymentType);
        model.addAttribute("shippingType", shippingType);

        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);

        List<BookCart> bookCarts = user.getCart().getBookCarts();
        List<BookCart> bookCarts2 = new ArrayList<>();
        Double totalPrice = 0.00;
        for(BookCart bookCart: bookCarts) {
            totalPrice += bookCart.getNumber() * bookCart.getBook().getPrice();
            bookCarts2.add(bookCart);
        }


        bookCarts.clear();
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("bookCarts", bookCarts2);

        userService.update(user);

        return "payment";
    }

}
