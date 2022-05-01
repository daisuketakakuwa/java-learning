package com.example.javalearning.api.service;

import com.example.javalearning.db.domain.HospitalEntity;
import com.example.javalearning.db.repository.HospitalRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HospitalServiceMockTest {

    // HospitalRepository classをMock化してテストする

    // service class to test
    @InjectMocks
    private HospitalService hospitalService;

    // mock target
    @Mock
    private HospitalRepository hospitalRepository;

    @Test
    public void givenNothing_whenFindHospitalsNoFetch_thenSuccess() {

        // mock setting
        List<HospitalEntity> mockList = new ArrayList();
        HospitalEntity hospital = new HospitalEntity();
        hospital.setDoctors(new HashSet<>());
        mockList.add(hospital);
        mockList.add(hospital);
        mockList.add(hospital);
        when(hospitalRepository.findHospitalNoFetch()).thenReturn(mockList);

        List<HospitalEntity> actual = hospitalService.findHospitalNoFetch();

        Assertions.assertEquals(3, actual.size());
    }

}
