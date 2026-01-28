package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.CookingStep;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookingStepRepository extends PagingAndSortingRepository<CookingStep, Long>, CrudRepository<CookingStep, Long> {

    List<CookingStep> findByRecipeIdOrderByCookingStepNumber(Long recipeId);

}
