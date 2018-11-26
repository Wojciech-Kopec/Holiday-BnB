package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "LOCALIZATIONS")

public class Localization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "localization_id")
    private long id;
    private String country;
    private String state;
    private String city;
    private String address;

}
