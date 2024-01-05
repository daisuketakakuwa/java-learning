package dt.learning.policyapi.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dt.learning.policyapi.api.controller.dto.PolicyDto;
import dt.learning.policyapi.api.controller.request.PolicySearchRequest;
import dt.learning.policyapi.api.service.PolicyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping
    public List<PolicyDto> searchPolicies(PolicySearchRequest request) {
        return policyService.searchPolicies(buildConditionMap(request));
    }

    private Map<String, Object> buildConditionMap(PolicySearchRequest request) {
        Map<String, Object> conditionMap = new HashMap<>();
        if (StringUtils.isNotBlank(request.getPolicyNo())) {
            conditionMap.put("policyNo", request.getPolicyNo());
        }
        if (StringUtils.isNotBlank(request.getPolicyHolderName())) {
            conditionMap.put("policyHolderName", request.getPolicyHolderName());
        }
        if (StringUtils.isNotBlank(request.getPolicyHolderGender())) {
            conditionMap.put("policyHolderGender", request.getPolicyHolderGender());
        }
        return conditionMap;
    }

}
