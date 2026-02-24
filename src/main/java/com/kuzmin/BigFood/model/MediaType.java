package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "media_types")
@Table(name = "media_types")
public class MediaType {

    @Id
    @SequenceGenerator(name = "media_type_id_seq", sequenceName = "media_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_type_id_seq")
    private Long id;

    @Column(name = "media_type_name", unique = true, nullable = false)
    private String name;

}
