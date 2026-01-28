package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.NationalCuisine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationalCuisineRepository extends CrudRepository<NationalCuisine, Long> {
    List<NationalCuisine> findAll();
}
