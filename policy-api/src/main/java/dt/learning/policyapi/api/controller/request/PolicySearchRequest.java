package dt.learning.policyapi.api.controller.request;

import lombok.Data;

@Data
public class PolicySearchRequest {

    private String policyNo;

    private String policyHolderName;

    private String policyHolderGender;

}
