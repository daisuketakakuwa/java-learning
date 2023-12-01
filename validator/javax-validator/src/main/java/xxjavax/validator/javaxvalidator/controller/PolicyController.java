package xxjavax.validator.javaxvalidator.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import xxjavax.validator.javaxvalidator.controller.request.PolicySaveRequestParameter;
import xxjavax.validator.javaxvalidator.controller.request.PolicySearchRequestParameter;

@RestController
@Validated
public class PolicyController {

    // 1: GET pathパラメータ @PathVariable

    @GetMapping("/policies-pathparam/{policyNo}")
    public String findPolicy(@PathVariable String policyNo) {
        return "Received policyNo: " + policyNo;
    }

    @GetMapping("/policies-pathparam-valid/{policyNo}")
    public String findPolicyWithValidation(
            @PathVariable @Size(min = 9, max = 9, message = "Value must be 9.") String policyNo) {
        return "Received policyNo: " + policyNo;
    }

    // 2: GET queryパラメータ @RequestParam

    @GetMapping("/policies-queryparam")
    public String searchPolicies(@RequestParam(required = false) String policyNo) {
        return "Received policyNo: " + policyNo;
    }

    @GetMapping("/policies-queryparam-valid")
    public String searchPoliciesWithValidation(
            @RequestParam(required = false) @Size(min = 9, max = 9, message = "Value must be 9.") String policyNo) {
        // @Sizeに反していたら ConstraintViolationException を投げる。
        return "Received policyNo: " + policyNo;
    }

    // 3. GET queryパラメータ DTOクラス

    @GetMapping("/policies-queryparam-dto")
    public String searchPoliciesDto(PolicySearchRequestParameter request) {
        return "Received policyNo: " + request.getPolicyNo();
    }

    @GetMapping("/policies-queryparam-dto-valid")
    public String searchPoliciesDtoWithValidation(@Valid PolicySearchRequestParameter request) {
        return "Received policyNo: " + request.getPolicyNo();
    }

    // 4. POST RequestBody @RequestBody

    // 動作確認用curlコマンド
    // curl -X POST -H "Content-Type: application/json" -d "{\"holderName\":
    // \"daisuke takakuwa\", \"insuranceType\": \"がん保険\"}"
    // http://localhost:8080/policies

    @PostMapping("/policies")
    public String savePolicy(@RequestBody PolicySaveRequestParameter request) {
        return "Received request: " + request;
    }

    @PostMapping("/policies-valid")
    public String savePolicyWithValidation(@Valid @RequestBody PolicySaveRequestParameter request) {
        return "Received request: " + request;
    }

}
