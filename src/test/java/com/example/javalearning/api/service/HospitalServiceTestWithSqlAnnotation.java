package com.example.javalearning.api.service;

import java.util.List;

import com.example.javalearning.db.domain.HospitalEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// SpringBootTest start spring-boot app and executes flyway
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HospitalServiceTestWithSqlAnnotation {

    @Autowired
    HospitalService hospitalService;

    @Test
    @Sql(scripts = {
            "/api/service/clean_table.sql",
            "/api/service/insert_hospital.sql",
            "/api/service/insert_doctor_genre.sql",
            "/api/service/insert_doctor.sql",
            "/api/service/insert_hospital_category.sql",
            "/api/service/insert_hospital_hospital_category.sql",
    }, config = @SqlConfig(encoding = "utf-8"))
    public void givenNothing_whenFindHospitalsNoFetch_thenSuccess() {
        List<HospitalEntity> hospitals = hospitalService.findHospitalNoFetch();
        Assertions.assertEquals(5, hospitals.size());
    }

    @Test
    @Sql(scripts = {
            "/api/service/clean_table.sql",
            "/api/service/insert_hospital.sql",
            "/api/service/insert_doctor_genre.sql",
            "/api/service/insert_doctor.sql",
            "/api/service/insert_hospital_category.sql",
            "/api/service/insert_hospital_hospital_category.sql"
    }, config = @SqlConfig(encoding = "utf-8"))
    public void givenNothing_whenFindHospitalsFetchDoctors_thenSuccess() {
        List<HospitalEntity> hospitals = hospitalService.findHospitalFetchDoctors();
        Assertions.assertEquals(5, hospitals.size());
    }

}
