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
@Table(name = "people")
@ToString
@Getter
public class People {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "people_id")
    private Long id;

    @Column(name = "name")
    private String name;
}
