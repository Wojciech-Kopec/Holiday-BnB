package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"accommodation"})
@ToString(callSuper = true, exclude = {"accommodation"})
@Builder

@Entity
@Table(name = "AMENITIES")

public class Amenity extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private AmenityType type;

    @NotBlank
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;
}
