package dt.learning.policyapi.api.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import dt.learning.policyapi.api.controller.dto.PolicyDto;
import dt.learning.policyapi.db.domain.PolicyEntity;
import dt.learning.policyapi.db.repository.PolicyRepository;
import dt.learning.policyapi.db.specification.PolicySpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicySpecification policySpecification;
    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;

    public List<PolicyDto> searchPolicies(Map<String, Object> conditionMap) {

        // 検索条件
        Specification<PolicyEntity> specs = policySpecification.buildSpecifications(conditionMap);

        // JpaSpecificationExecutorが提供するfindAllメソッドをコール
        List<PolicyEntity> policyEntites = policyRepository.findAll(specs);

        return policyEntites.stream().map(e -> modelMapper.map(e, PolicyDto.class))
                .collect(Collectors.toList());
    }
}
