package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.DishType;
import com.kuzmin.BigFood.repository.DishTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishTypeService {

    private final DishTypeRepository dishTypeRepository;

    public DishTypeService(DishTypeRepository dishTypeRepository) {
        this.dishTypeRepository = dishTypeRepository;
    }

    public List<DishType> getAll() {
        return dishTypeRepository.findAllWithChildren();
    }

    public List<DishType> getRootTypes() {
        return dishTypeRepository.findByParentIsNull();
    }

    public List<DishType> getChildren(Long parentId) {
        return dishTypeRepository.findByParentId(parentId);
    }

}
