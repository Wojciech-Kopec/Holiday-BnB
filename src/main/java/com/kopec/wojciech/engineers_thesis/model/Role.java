package com.kopec.wojciech.engineers_thesis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "roles" ,uniqueConstraints = @UniqueConstraint(columnNames = {"username", "role"}))
public class Role extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore //TODO needed?
    private User user;

    @Column(name = "role")
    private String name;


}
