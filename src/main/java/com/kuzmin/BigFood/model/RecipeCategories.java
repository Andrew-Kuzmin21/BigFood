package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "recipe_categories")
@Table(name = "recipe_categories")
public class RecipeCategories {

    @Id
    @SequenceGenerator(name = "recipe_categories_seq", sequenceName = "recipe_categories_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_categories_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
