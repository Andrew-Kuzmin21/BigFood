package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.MediaType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaTypeRepository extends CrudRepository<MediaType, Long> {
    List<MediaType> findAll();
}
