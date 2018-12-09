package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@ToString
@Builder

@Entity
@Table(name = "AMENITIES")

public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private long id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private AmenityType type;

    @NotBlank
    private String description;
}
