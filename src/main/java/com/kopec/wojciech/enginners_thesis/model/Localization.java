package com.kopec.wojciech.enginners_thesis.model;

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
    @Size(min = 5, max = 30)
    private String country;

    private String state;

    @NotNull
    @Size(min = 5, max = 30)
    private String city;

    private String address;


}
