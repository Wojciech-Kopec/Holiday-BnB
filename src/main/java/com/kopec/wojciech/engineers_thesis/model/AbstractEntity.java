package com.kopec.wojciech.engineers_thesis.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    protected Integer id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}
