package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@Entity(name = "media")
@Table(name = "media")
public class Media {

    @Id
    @SequenceGenerator(name = "media_id_seq", sequenceName = "media_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_id_seq")
    private Long id;

    @Column(name = "media_url", nullable = false, length = 500)
    private String url;

    @Column(name = "media_is_main", nullable = false)
    private boolean main;

    @CreationTimestamp
    @Column(name = "media_created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "media_type_id")
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "cooking_step_id")
    private CookingStep cookingStep;

}
