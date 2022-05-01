package com.example.javalearning.api.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import com.example.javalearning.db.domain.HospitalEntity;
import com.example.javalearning.db.repository.HospitalRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    /**
     * Get hospital info with nothing.
     */
    @Transactional
    public List<HospitalEntity> findHospitalNoFetch() {
        List<HospitalEntity> hospitals = hospitalRepository.findHospitalNoFetch();
        // when call getDoctors(), query executed to get doctor where hospital_id = ?
        hospitals.stream().forEach(h -> System.out.println("★" + h.getDoctors().size()));
        return hospitals;
    }

    @Transactional
    public List<HospitalEntity> findHospitalFetchDoctors() {
        List<HospitalEntity> hospitals = hospitalRepository.findHospitalFetchDoctors();
        // when call getDoctors(), query not executed because doctors already gotten by
        // findHospitalFetchDoctors()
        hospitals.stream().forEach(h -> System.out.println("★" + h.getDoctors().size()));
        return hospitals;
    }

}
