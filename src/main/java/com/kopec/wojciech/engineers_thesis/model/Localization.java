package com.kopec.wojciech.engineers_thesis.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"accommodation"})
@ToString(callSuper = true, exclude = {"accommodation"})
@Builder

@Entity
@Table(name = "LOCALIZATIONS")

public class Localization extends AbstractEntity {

    @OneToOne(mappedBy = "localization")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accommodation accommodation;

    @NotNull
    @Size(min = 3, max = 50)
    private String country;

    @NotNull
    @Size(min = 3, max = 50)
    private String city;

    private String address;


}
