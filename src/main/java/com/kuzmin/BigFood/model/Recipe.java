package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity(name = "recipes")
@Table(name = "recipes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @Id
    @SequenceGenerator(name = "recipe_id_seq", sequenceName = "recipe_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "recipe_name", length = 100, nullable = false)
    private String name;

    @Column(name = "recipe_description", length = 700, nullable = false)
    private String description;

    @Column(name = "recipe_cooking_time")
    private Integer cookingTime;

    @Column(name = "recipe_serving")
    private Integer serving;

    @CreationTimestamp
    @Column(name = "recipe_created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "recipe_updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "national_cuisine_id")
    private NationalCuisine nationalCuisine;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecipeDishType> dishTypes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredients> ingredients = new ArrayList<>();

    @OrderBy("number ASC")
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CookingStep> cookingSteps = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> media = new ArrayList<>();
}
