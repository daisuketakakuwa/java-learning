package com.example.javalearning.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Sql(scripts = {
            "/api/service/clean_table.sql",
            "/api/service/insert_hospital.sql",
            "/api/service/insert_doctor_genre.sql",
            "/api/service/insert_doctor.sql",
            "/api/service/insert_hospital_category.sql",
            "/api/service/insert_hospital_hospital_category.sql",
    }, config = @SqlConfig(encoding = "utf-8"))
    @Test
    public void findHospitals() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/hospitals")
                // .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        System.out.println("★" + mvcResult.getResponse().getContentAsString());

    }

}
