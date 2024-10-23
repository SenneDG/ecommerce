package com.senne.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senne.modal.Product;
import com.senne.modal.Review;
import com.senne.modal.User;
import com.senne.repository.ReviewRepository;
import com.senne.request.CreateReviewRequest;
import com.senne.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest createReviewRequest, User user, Product product) {
        Review review = new Review();
        review.setReviewText(createReviewRequest.getReviewText());
        review.setRating(createReviewRequest.getReviewRating());
        review.setUser(user);
        review.setProduct(product);
        review.setProductImages(createReviewRequest.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updatReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if (!review.getUser().getId().equals(userId)) {
            throw new Exception("You cannot update this review");
        }

        review.setReviewText(reviewText);
        review.setRating(rating);

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if (!review.getUser().getId().equals(userId)) {
            throw new Exception("You cannot delete this review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
            new Exception("Review not found"));
    }

}
