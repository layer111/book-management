package com.example.bookmanagement.validator;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.service.BookService;
import com.example.bookmanagement.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmptyT");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "NotEmptyA");
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "publishedDate", "NotEmptyP");
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfPages", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "NotEmptyC");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmptyPR");

        if(((Book) o).getPrice() != null && ((Book) o).getPrice() < 0) {
            errors.rejectValue("price", "priceNegative.bookForm.book");
        }

        Long bookId = book.getId();
        if(bookId != null) {
            // Edit
            Book bookInDb = bookService.findById(bookId);
            if(!bookInDb.getTitle().equals(book.getTitle())) {
                if (bookService.findByBookTitle(book.getTitle()) != null) {
                    errors.rejectValue("title", "Duplicate.bookForm.book");
                }
            }
        } else {
            // Create
            if (bookService.findByBookTitle(book.getTitle()) != null) {
                errors.rejectValue("title", "Duplicate.bookForm.book");
            }
        }
        
        
    }
}