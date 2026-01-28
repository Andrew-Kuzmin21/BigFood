package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.Media;
import com.kuzmin.BigFood.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<Media> findByRecipeId(Long recipeId) {
        return mediaRepository.findByRecipeIdAndCookingStepIsNull(recipeId);
    }

    public List<Media> findByCookingStepId(Long cookingStepId) {
        return mediaRepository.findByCookingStepIdOrderById(cookingStepId);
    }

}
