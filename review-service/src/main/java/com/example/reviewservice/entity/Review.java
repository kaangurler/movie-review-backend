package com.example.reviewservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(updatable = false)
    private Long userId;
    @Column(updatable = false)
    private Long movieId;
    @Lob
    @Column(nullable = false, length = 512000)
    private String comment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Column(nullable = false)
    private Boolean edited;
    @Column(nullable = false)
    private Boolean spoiler;
}
