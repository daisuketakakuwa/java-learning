package com.example.javalearning.db.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// DO NOT USE @Data to Entity class which creates [equals] and [hashcode] implmentations
// which includes checking the content of @OneToMany collections
// @Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "hospital")
public class HospitalEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // SpringSecurityでログイン認証したユーザーIDが自動で入るはず
    // @CreatedBy
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_date", updatable = true)
    private LocalDateTime updatedDate;

    private String updatedBy;

    @Version
    private long version;

    // mapperBy ... fieldName in the DoctorENtity class
    @OneToMany(mappedBy = "id.hospital", fetch = FetchType.LAZY)
    private Set<DoctorEntity> doctors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "HOSPITAL_HOSPITAL_CATEGORY_REL")
    private Set<HospitalCategoryEntity> hospitalCategories;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Set<DoctorEntity> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<DoctorEntity> doctors) {
        this.doctors = doctors;
    }

    public Set<HospitalCategoryEntity> getHospitalCategories() {
        return hospitalCategories;
    }

    public void setHospitalCategories(Set<HospitalCategoryEntity> hospitalCategories) {
        this.hospitalCategories = hospitalCategories;
    }

}
