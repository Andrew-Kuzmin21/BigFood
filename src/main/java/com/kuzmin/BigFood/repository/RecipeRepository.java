package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long>, CrudRepository<Recipe, Long> {
//    @Query("""
//        SELECT DISTINCT r FROM recipes r
//        LEFT JOIN FETCH r.nationalCuisine
//        LEFT JOIN FETCH r.author
//    """)
//    List<Recipe> findByIngredient(Ingredient ingredient);

//    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findByNationalCuisineId(Long nationalCuisineId);

    List<Recipe> findByAuthorId(Long authorId);

    @Query("""
        select distinct r
        from recipes r
        left join fetch r.cookingSteps s
        left join fetch s.media
        left join fetch r.media rm
        where r.id = :id
    """)
    Optional<Recipe> findByIdWithStepsAndMedia(@Param("id") Long id);
}
