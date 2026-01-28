package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.RecipeIngredients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientsRepository extends PagingAndSortingRepository<RecipeIngredients, Long>, CrudRepository<RecipeIngredients, Long> {
}
