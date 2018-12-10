package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"}, callSuper = true)
@ToString
@Builder

@Entity
@Table(name = "LOCALIZATIONS")

public class Localization extends AbstractEntity {

    private String country;
    private String state;
    private String city;
    private String address;

}
