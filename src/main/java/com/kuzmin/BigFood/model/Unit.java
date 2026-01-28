package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "units")
@Table(name = "units")
public class Unit {

    @Id
    @SequenceGenerator(name = "unit_id_seq", sequenceName = "unit_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_id_seq")
    private Long id;

    @Column(name = "unit_name", unique = true, nullable = false)
    private String name;

}
