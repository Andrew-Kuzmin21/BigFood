package com.kuzmin.BigFood.model;

import jakarta.validation.constraints.Min;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Entity(name = "cooking_steps")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "cooking_steps", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "cooking_step_number"})
})
public class CookingStep {

    @Id
    @SequenceGenerator(name = "cooking_step_id_seq", sequenceName = "cooking_step_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cooking_step_id_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "cooking_step_title", length = 100)
    private String title;

    @Min(1)
    @Column(name = "cooking_step_number", nullable = false)
    private int number;

    @Column(name = "cooking_step_description", nullable = false, length = 500)
    private String description;

    @Transient
    private Boolean toDelete = false;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

//    @OneToMany(mappedBy = "cookingStep", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Media> media = new ArrayList<>();
    @OneToMany(mappedBy = "cookingStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Media> media = new LinkedHashSet<>();

}
