package com.cardozo.multidb.models;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "equipment")
@ToString
@Getter
public class Equipment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "equipment_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;
}
