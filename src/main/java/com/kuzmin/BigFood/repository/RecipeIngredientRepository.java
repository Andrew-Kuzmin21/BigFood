package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.RecipeIngredients;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends PagingAndSortingRepository<RecipeIngredients, Long>, CrudRepository<RecipeIngredients, Long> {

    @Modifying
    @Query("DELETE FROM recipe_ingredients ri WHERE ri.recipe = :recipe")
    void deleteByRecipe(@Param("recipe") Recipe recipe);

}
