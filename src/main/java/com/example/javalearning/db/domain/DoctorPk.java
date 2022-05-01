package com.example.javalearning.db.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// DO NOT USE @Data to Entity class which creates [equals] and [hashcode] implmentations
// which includes checking the content of @OneToMany collections
// @Data
@Embeddable
public class DoctorPk implements java.io.Serializable {

    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id") // column in doctor table
    private HospitalEntity hospital;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public HospitalEntity getHospital() {
        return hospital;
    }

    public void setHospital(HospitalEntity hospital) {
        this.hospital = hospital;
    }

}
