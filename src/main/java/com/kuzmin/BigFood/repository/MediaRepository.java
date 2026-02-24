package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends PagingAndSortingRepository<Media, Long>, CrudRepository<Media, Long> {

    List<Media> findByRecipeId(Long recipeId);

    List<Media> findByCookingStepId(Long cookingStepId);

    List<Media> findByRecipeIdAndCookingStepIsNull(Long recipeId);

    List<Media> findByCookingStepIdOrderById(Long cookingStepId);

    void deleteByRecipeId(Long id);

    void deleteByRecipeIdAndCookingStepIsNull(Long id);

    void deleteByCookingStepId(Long id);

}
