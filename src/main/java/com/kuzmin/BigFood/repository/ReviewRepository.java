package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long>, CrudRepository<Review, Long> {

    List<Review> findAllByRecipeId(Long recipeId);

    List<Review> findByAuthorId(Long authorId);
}
