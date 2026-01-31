package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.DishType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishTypeRepository extends CrudRepository<DishType, Long> {

    @Query("select dt from dish_types dt left join fetch dt.children")
    List<DishType> findAllWithChildren();
    List<DishType> findByParentIsNull();
    List<DishType> findByParentId(Long parentId);

}
