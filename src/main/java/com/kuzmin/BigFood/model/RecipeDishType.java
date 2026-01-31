package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "recipe_dish_types")
@Table(name = "recipe_dish_types")
public class RecipeDishType {

    @Id
    @SequenceGenerator(name = "recipe_dish_types_id_seq", sequenceName = "recipe_dish_types_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_dish_types_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_type_id", nullable = false)
    private DishType dishType;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

}
