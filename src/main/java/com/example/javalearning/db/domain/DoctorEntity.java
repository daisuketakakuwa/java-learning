package com.example.javalearning.db.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// DO NOT USE @Data to Entity class which creates [equals] and [hashcode] implmentations
// which includes checking the content of @OneToMany collections
// @Data
@Entity
@Table(name = "doctor")
public class DoctorEntity {

    @EmbeddedId
    private DoctorPk id;

    @OneToOne(fetch = FetchType.LAZY)
    private DoctorGenreEntity doctorGenre;

    @Column(name = "name")
    private String name;

    public DoctorPk getId() {
        return id;
    }

    public void setId(DoctorPk id) {
        this.id = id;
    }

    public DoctorGenreEntity getDoctorGenre() {
        return doctorGenre;
    }

    public void setDoctorGenre(DoctorGenreEntity doctorGenre) {
        this.doctorGenre = doctorGenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
