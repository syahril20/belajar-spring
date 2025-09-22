package testing.belajar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map<String, Object> sayHello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hellos Watahels!");
        response.put("status", "success");
        response.put("code", 200);
        return response;
    }

}