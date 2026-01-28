package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@Entity(name = "reviews")
@Table(name = "reviews", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "author_id"})
    }
)
public class Review {

    @Id
    @SequenceGenerator(name = "review_id_seq", sequenceName = "review_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_seq")
    private Long id;

    @Min(1)
    @Max(5)
    @Column(name = "review_rating", nullable = false)
    private Integer rating;

    @Column(name = "review_description", length = 1000)
    private String description;

    @CreationTimestamp
    @Column(name = "review_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "review_updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

}
