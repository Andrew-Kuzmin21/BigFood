package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.Unit;
import com.kuzmin.BigFood.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> getAll() {
        return unitRepository.findAll();
    }
}
