package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.NationalCuisine;
import com.kuzmin.BigFood.repository.NationalCuisineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationalCuisineService {

    private final NationalCuisineRepository nationalCuisineRepository;

    public NationalCuisineService(NationalCuisineRepository nationalCuisineRepository) {
        this.nationalCuisineRepository = nationalCuisineRepository;
    }

    /**
     * Получить все национальные кухни
     */
    public List<NationalCuisine> getAll() {
        return nationalCuisineRepository.findAll();
    }

    /**
     * Получить кухню по ID
     */
    public NationalCuisine getById(Long id) {
        return nationalCuisineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NationalCuisine не найдена: " + id));
    }
}
