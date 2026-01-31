package com.kuzmin.BigFood.controller;

import com.kuzmin.BigFood.dto.DishTypeDto;
import com.kuzmin.BigFood.service.DishTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dish-types")
public class DishTypeApiController {

    private final DishTypeService dishTypeService;

    public DishTypeApiController(DishTypeService dishTypeService) {
        this.dishTypeService = dishTypeService;
    }

    @GetMapping("/roots")
    public List<DishTypeDto> roots() {
        return dishTypeService.getRootTypes()
                .stream()
                .map(dt -> new DishTypeDto(dt.getId(), dt.getName()))
                .toList();
    }

    @GetMapping("/{parentId}/children")
    public List<DishTypeDto> children(@PathVariable Long parentId) {
        return dishTypeService.getChildren(parentId)
                .stream()
                .map(dt -> new DishTypeDto(dt.getId(), dt.getName()))
                .toList();
    }
}
