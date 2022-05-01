package com.example.javalearning.api.service;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import com.example.javalearning.db.domain.HospitalEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// SpringBootTest start spring-boot app and executes flyway
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HospitalServiceTestWithScriptUtils {

    @Autowired
    DataSource dataSource;

    // @Sqlの方が楽でいいな
    @BeforeEach
    public void beforeEach() throws Exception {
        List<String> sqlList = Arrays.asList(
                "/api/service/clean_table.sql",
                "/api/service/insert_hospital.sql",
                "/api/service/insert_doctor_genre.sql",
                "/api/service/insert_doctor.sql",
                "/api/service/insert_hospital_category.sql",
                "/api/service/insert_hospital_hospital_category.sql");
        for (String sql : sqlList) {
            ScriptUtils.executeSqlScript(dataSource.getConnection(),
                    new ClassPathResource(sql));
        }
    }

    @Autowired
    HospitalService hospitalService;

    @Test
    public void givenNothing_whenFindHospitalsNoFetch_thenSuccess() {
        List<HospitalEntity> hospitals = hospitalService.findHospitalNoFetch();
        Assertions.assertEquals(5, hospitals.size());
    }

    @Test
    public void givenNothing_whenFindHospitalsFetchDoctors_thenSuccess() {
        List<HospitalEntity> hospitals = hospitalService.findHospitalFetchDoctors();
        Assertions.assertEquals(5, hospitals.size());
    }

}
