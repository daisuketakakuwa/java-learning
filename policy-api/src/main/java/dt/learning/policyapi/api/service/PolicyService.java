package dt.learning.policyapi.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dt.learning.policyapi.api.controller.dto.PolicyDto;
import dt.learning.policyapi.db.domain.PolicyEntity;
import dt.learning.policyapi.db.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;

    public List<PolicyDto> findAllPolicies() {
        List<PolicyEntity> policyEntites = StreamSupport.stream(policyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return policyEntites.stream().map(e -> modelMapper.map(e, PolicyDto.class))
                .collect(Collectors.toList());
    }
}
