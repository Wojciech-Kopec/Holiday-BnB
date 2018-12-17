package com.kopec.wojciech.enginners_thesis.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    protected long id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}
