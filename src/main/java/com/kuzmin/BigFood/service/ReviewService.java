package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.Review;
import com.kuzmin.BigFood.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAllByRecipeId(Long recipeId) {
        return reviewRepository.findAllByRecipeId(recipeId);
    }

    public List<Review> findAllByAuthorId(Long authorId) {
        return reviewRepository.findByAuthorId(authorId);
    }
}
