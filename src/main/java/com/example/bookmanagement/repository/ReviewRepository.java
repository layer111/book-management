package com.example.bookmanagement.repository;

import com.example.bookmanagement.model.Review;
import com.example.bookmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
