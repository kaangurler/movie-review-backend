package com.example.castservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "casts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    @ElementCollection
    @CollectionTable(name = "cast_movie",
            joinColumns = @JoinColumn(name = "cast_id"))
    private List<Long> movies;
    private byte[] photo;
}
