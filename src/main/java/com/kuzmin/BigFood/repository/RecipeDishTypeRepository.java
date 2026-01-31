package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.DishType;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.RecipeDishType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDishTypeRepository extends CrudRepository<RecipeDishType, Long> {

    List<RecipeDishType> findAll();

    List<RecipeDishType> findByRecipeId(Long recipeId);

    @Modifying
    @Query("delete from recipe_dish_types r where r.recipe = :recipe")
    void deleteByRecipe(@Param("recipe") Recipe recipe);

    List<RecipeDishType> findByDishType(DishType dishType);

}
