package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "national_cuisine")
@Table(name = "national_cuisine")
public class NationalCuisine {

    @Id
    @SequenceGenerator(name = "national_cuisine_id_seq", sequenceName = "national_cuisine_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "national_cuisine_id_seq")
    private Long id;

    @Column(name = "national_cuisine_name", nullable = false)
    private String name;

}
