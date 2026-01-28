package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.RecipeCategories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeCategoriesRepository extends PagingAndSortingRepository<RecipeCategories, Long>, CrudRepository<RecipeCategories, Long> {

    List<RecipeCategories> findByRecipeId(Long recipeId);

    List<RecipeCategories> findByCategoryId(Long categoryId);

}
