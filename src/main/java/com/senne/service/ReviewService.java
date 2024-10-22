package com.senne.service;

import java.util.List;

import com.senne.modal.Product;
import com.senne.modal.Review;
import com.senne.modal.User;
import com.senne.request.CreateReviewRequest;

public interface ReviewService {

    Review createReview(CreateReviewRequest createReviewRequest, User user, Product product);
    List<Review> getReviewsByProductId(Long productId);
    Review updatReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;
}
