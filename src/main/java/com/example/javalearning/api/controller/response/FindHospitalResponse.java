package com.example.javalearning.api.controller.response;

import java.util.List;

import com.example.javalearning.api.controller.response.dto.HospitalDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FindHospitalResponse {
    private List<HospitalDto> hospitals;
}
