package dt.learning.policyapi.api.controller.dto;

import lombok.Data;

@Data
public class MaintenanceProgressDto {
    private String id;
    private String status;
    private String createdAt;
    private String policyNo;
}
