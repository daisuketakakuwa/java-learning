package api.client.api_client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import api.client.api_client.repository.remote.fuga.response.FetchFugaDataResponse;
import api.client.api_client.service.GeneralService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GeneralController {

    private final GeneralService generalService;

    @GetMapping("/events")
    public FetchFugaDataResponse fetchFuga() {
        return generalService.fetchFuga();
    }

}
