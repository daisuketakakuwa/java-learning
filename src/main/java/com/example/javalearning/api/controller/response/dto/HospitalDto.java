package com.example.javalearning.api.controller.response.dto;

import lombok.Data;

@Data
public class HospitalDto {
    private Integer id;
    private String name;
    private String address;
    private String zipcode;
}
