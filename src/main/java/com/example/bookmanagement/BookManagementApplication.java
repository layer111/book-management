package com.example.bookmanagement;

import com.example.bookmanagement.model.BookCategory;
import com.example.bookmanagement.model.Cart;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.repository.BookCategoryRepository;
import com.example.bookmanagement.repository.CartRepository;
import com.example.bookmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BookManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(BookCategoryRepository bookCategoryRepository, UserRepository userRepository, CartRepository cartRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		return args -> {
			BookCategory bookCategory = new BookCategory();
			bookCategory.setName("Cat 1");
			if(bookCategoryRepository.findAll().size() == 0) {
				bookCategoryRepository.save(bookCategory);
			}
			if(userRepository.findByEmail("admin@gmail.com") == null) {
				User user = new User();
				user.setFirstName("ADMIN");
				user.setLastName("ADMIN");
				user.setEmail("admin@gmail.com");
				user.setAdmin(true);
				user.setPassword(bCryptPasswordEncoder.encode("admin"));
				Cart cart = new Cart();
				cart = cartRepository.save(cart);
				user.setCart(cart);
				userRepository.save(user);
			}
		};
	}

}
