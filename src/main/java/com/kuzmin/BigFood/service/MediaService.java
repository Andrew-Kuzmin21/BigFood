package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.CookingStep;
import com.kuzmin.BigFood.model.Media;
import com.kuzmin.BigFood.model.MediaType;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.repository.MediaRepository;
import com.kuzmin.BigFood.repository.MediaTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaTypeRepository mediaTypeRepository;

    public MediaService(MediaRepository mediaRepository, MediaTypeRepository mediaTypeRepository) {
        this.mediaRepository = mediaRepository;
        this.mediaTypeRepository = mediaTypeRepository;
    }

    public void saveRecipeMedia(
            Recipe recipe,
            List<MultipartFile> files,
            Integer mainIndex
    ) {
        if (files == null) return;

        MediaType imageType = mediaTypeRepository.findById(1L).orElseThrow();
        MediaType videoType = mediaTypeRepository.findById(2L).orElseThrow();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) continue;

            String url = storeFile(file);

            Media media = new Media()
                    .setRecipe(recipe)
                    .setUrl(url)
                    .setMain(mainIndex != null && i == mainIndex)
                    .setMediaType(
                            file.getContentType().startsWith("video")
                                    ? videoType
                                    : imageType
                    );

            mediaRepository.save(media);
        }
    }

    private String storeFile(MultipartFile file) {
        // TODO: сохранить в uploads и вернуть URL
        return "/uploads/" + file.getOriginalFilename();
    }

    public List<Media> findByRecipeId(Long recipeId) {
        return mediaRepository.findByRecipeIdAndCookingStepIsNull(recipeId);
    }

    public List<Media> findByCookingStepId(Long cookingStepId) {
        return mediaRepository.findByCookingStepIdOrderById(cookingStepId);
    }

    public void saveStepMedia(CookingStep step, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return;

        MediaType imageType = mediaTypeRepository.findById(1L).orElseThrow();
        MediaType videoType = mediaTypeRepository.findById(2L).orElseThrow();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) continue;

            String url = storeFile(file);

            Media media = new Media()
                    .setCookingStep(step)
                    .setUrl(url)
                    .setMain(false)
                    .setMediaType(
                            file.getContentType() != null &&
                                    file.getContentType().startsWith("video")
                                    ? videoType
                                    : imageType
                    );

            mediaRepository.save(media);
        }
    }
}
