package com.example.javalearning.db.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// DO NOT USE @Data to Entity class which creates [equals] and [hashcode] implmentations
// which includes checking the content of @OneToMany collections
// @Data
@Entity
@Table(name = "hospital_category")
public class HospitalCategoryEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "hospitalCategories", fetch = FetchType.LAZY)
    private Set<HospitalEntity> hospitals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<HospitalEntity> getHospitals() {
        return hospitals;
    }

    public void setHospitals(Set<HospitalEntity> hospitals) {
        this.hospitals = hospitals;
    }

}
