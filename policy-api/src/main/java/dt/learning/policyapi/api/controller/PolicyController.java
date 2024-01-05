package dt.learning.policyapi.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dt.learning.policyapi.api.controller.dto.PolicyDto;
import dt.learning.policyapi.api.service.PolicyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping
    public List<PolicyDto> findPolicies() {
        // TODO: 検索用のメソッドに切り替える
        return policyService.findAllPolicies();
    }

}
