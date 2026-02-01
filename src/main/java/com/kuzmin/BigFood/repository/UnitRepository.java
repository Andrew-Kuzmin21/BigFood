package com.kuzmin.BigFood.repository;

import com.kuzmin.BigFood.model.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long> {
    List<Unit> findAll();

    Unit getReferenceById(Long unitId);
}
