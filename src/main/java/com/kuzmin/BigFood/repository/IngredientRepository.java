package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findAll();

    Ingredient getReferenceById(Long ingredientId);
}
