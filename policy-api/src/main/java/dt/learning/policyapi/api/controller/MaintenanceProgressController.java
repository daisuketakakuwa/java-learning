package dt.learning.policyapi.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dt.learning.policyapi.api.controller.dto.MaintenanceProgressDto;
import dt.learning.policyapi.api.service.MaintenanceProgressService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/progresses")
@RequiredArgsConstructor
public class MaintenanceProgressController {

    private final MaintenanceProgressService maintenanceProgressService;

    @GetMapping
    public List<MaintenanceProgressDto> findProgresses() {
        // TODO: 検索用のメソッドに切り替える
        return maintenanceProgressService.findAllProgresses();
    }

}
