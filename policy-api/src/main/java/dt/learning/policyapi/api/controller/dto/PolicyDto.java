package dt.learning.policyapi.api.controller.dto;

import lombok.Data;

@Data
public class PolicyDto {
    private String policyNo;
    private String policyHolderName;
    private String policyHolderGender;
    private String policyType;
}
