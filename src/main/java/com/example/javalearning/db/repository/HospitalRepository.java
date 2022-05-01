package com.example.javalearning.db.repository;

import java.util.List;

import com.example.javalearning.db.domain.HospitalEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends CrudRepository<HospitalEntity, Integer> {

    // find hospitals with nothing
    @Query(value = "SELECT DISTINCT hospital FROM HospitalEntity hospital ")
    // the query equals to "SELECT DISTINCT hospital FROM HospitalEntity hospital
    // LEFT JOIN hospital.doctors"
    public List<HospitalEntity> findHospitalNoFetch();

    // find hospitals fetching doctors
    @Query(value = "SELECT DISTINCT hospital FROM HospitalEntity hospital " +
            "LEFT JOIN FETCH hospital.doctors")
    public List<HospitalEntity> findHospitalFetchDoctors();

}
