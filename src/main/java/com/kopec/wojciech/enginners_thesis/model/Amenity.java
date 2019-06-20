package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"accommodationId"})
@ToString(callSuper = true, exclude = {"accommodationId"})
@Builder

@Entity
@Table(name = "AMENITIES")

public class Amenity extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private Amenity.AmenityType type;

    @NotBlank
    private String description;

    @ManyToOne
    private Accommodation accommodationId;

    public enum AmenityType {
        WIFI("WI-FI"),
        KITCHEN("Kitchen"),
        TV("TV"),
        POOL("Swimming Pool"),
        BACKYARD("Backyard"),
        SAUNA("Sauna"),
        PARKING("Parking"),
        TERRACE("Terrace"),
        AC("Air-conditioning"),
        OTHER("Other");


        private String type;

        AmenityType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
