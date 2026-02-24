package com.kuzmin.BigFood.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeFormDto {

    private Long id;
    private String name;
    private String description;
    private Integer cookingTime;
    private Integer serving;
    private Long nationalCuisineId;

    private List<Long> dishTypeIds;

    @Valid
    private List<RecipeIngredientDto> ingredients = new ArrayList<>();

    @Valid
    @Size(max = 50, message = "Не более 50 шагов приготовления")
    private List<CookingStepDto> cookingSteps = new ArrayList<>();

//    private List<Media> existingMedia;
    private List<MultipartFile> media;
    private Integer mainMediaIndex;

    private List<MediaDto> existingMedia;

    public void setExistingMedia(List<MediaDto> existingMedia) {
        this.existingMedia = existingMedia;
    }

    public List<MediaDto> getExistingMedia() {
        return existingMedia;
    }

}
