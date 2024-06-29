package api.client.api_client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PolicyController {

    @GetMapping("/hello")
    public String hello() {
        return "HELLO";
    }

}
