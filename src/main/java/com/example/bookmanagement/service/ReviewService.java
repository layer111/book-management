package com.example.bookmanagement.service;

import com.example.bookmanagement.model.Review;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(Review review) {
        reviewRepository.deleteById(review.getId());
    }

}
