package xxjavax.validator.javaxvalidator.controller.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PolicySearchRequestParameter {
    @Size(min = 9, max = 9, message = "Value must be 9.")
    private String policyNo;
}
