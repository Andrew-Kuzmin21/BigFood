package com.kuzmin.BigFood.model;

import jakarta.validation.constraints.Min;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "cooking_steps")
@Table(name = "cooking_steps", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "cooking_step_number"})
})
public class CookingStep {

    @Id
    @SequenceGenerator(name = "cooking_step_id_seq", sequenceName = "cooking_step_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cooking_step_id_seq")
    private Long id;

    @Column(name = "cooking_step_title", length = 100)
    private String title;

    @Min(1)
    @Column(name = "cooking_step_number", nullable = false)
    private int number;

    @Column(name = "cooking_step_description", nullable = false, length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

}
