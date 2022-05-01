package com.example.javalearning.api.controller;

import java.util.List;

import com.example.javalearning.api.controller.response.FindHospitalResponse;
import com.example.javalearning.api.controller.response.dto.HospitalDto;
import com.example.javalearning.api.service.HospitalService;
import com.example.javalearning.db.domain.HospitalEntity;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<FindHospitalResponse> findHospitals() {
        List<HospitalEntity> hospitalEntities = hospitalService.findHospitalFetchDoctors();
        List<HospitalDto> hospitalDtos = modelMapper.map(hospitalEntities, new TypeToken<List<HospitalDto>>() {
        }.getType());
        return ResponseEntity.ok(new FindHospitalResponse(hospitalDtos));
    }

}
