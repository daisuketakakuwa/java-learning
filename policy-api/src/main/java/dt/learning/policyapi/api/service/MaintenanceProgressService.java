package dt.learning.policyapi.api.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dt.learning.policyapi.api.controller.dto.MaintenanceProgressDto;
import dt.learning.policyapi.db.domain.MaintenanceProgressEntity;
import dt.learning.policyapi.db.repository.MaintenanceProgressRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaintenanceProgressService {

    private final MaintenanceProgressRepository maintenanceProgressRepository;
    private final ModelMapper modelMapper;

    public List<MaintenanceProgressDto> findAllProgresses() {
        List<MaintenanceProgressEntity> progressEntites = StreamSupport
                .stream(maintenanceProgressRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return progressEntites.stream().map(e -> modelMapper.map(e, MaintenanceProgressDto.class))
                .collect(Collectors.toList());
    }

}
