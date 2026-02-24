package com.kuzmin.BigFood.dto;

import lombok.Data;

import java.util.List;

@Data
public class CookingStepDto {

    private Long id;
    private String title;
    private String description;

    private List<MediaDto> existingMedia;
}
