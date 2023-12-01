package xxjavax.validator.javaxvalidator.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PolicySaveRequestParameter {

    @NotBlank
    @Size(max = 50)
    private String holderName;

    @NotBlank
    @Size(max = 100)
    private String insuranceType;

}
