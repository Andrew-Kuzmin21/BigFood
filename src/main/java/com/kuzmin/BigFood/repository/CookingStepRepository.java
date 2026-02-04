package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.CookingStep;
import com.kuzmin.BigFood.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookingStepRepository extends PagingAndSortingRepository<CookingStep, Long>, JpaRepository<CookingStep, Long> {

    List<CookingStep> findByRecipeIdOrderByNumber(Long recipeId);

    void deleteByRecipe(Recipe savedRecipe);

    void flush();

}
