package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Ingredient;
import com.kuzmin.BigFood.model.NationalCuisine;
import com.kuzmin.BigFood.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, CrudRepository<Recipe, Long> {
    @Query("""
        SELECT DISTINCT r FROM recipes r
        LEFT JOIN FETCH r.nationalCuisine
        LEFT JOIN FETCH r.author
    """)
//    List<Recipe> findByIngredient(Ingredient ingredient);
    Page<Recipe> findAll(Pageable pageable);

    public List<Recipe> findByNationalCuisineId(Long nationalCuisineId);

    public List<Recipe> findByAuthorId(Long authorId);
}
