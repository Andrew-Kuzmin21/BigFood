package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
@Entity(name = "dish_types")
@Table(name = "dish_types")
public class DishType {

    @Id
    @SequenceGenerator(name = "dish_type_id_seq", sequenceName = "dish_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_type_id_seq")
    private Long id;

    @Column(name = "dish_type_name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "dish_type_parent_id")
    private DishType parent;

    @OneToMany(mappedBy = "parent")
    private List<DishType> children;

}
