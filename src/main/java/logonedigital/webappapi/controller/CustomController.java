package logonedigital.webappapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

    @GetMapping(path = "/api/custom")
    public String custom() {
        return "custom";
    }
}