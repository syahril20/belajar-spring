package testing.belajar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testing.belajar.services.DatabaseHealthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class DatabaseHealthController {

    private final DatabaseHealthService dbService;

    public DatabaseHealthController(DatabaseHealthService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/health/db")
    public Map<String, Object> checkDatabase() {
        Map<String, Object> response = new HashMap<>();
        if (dbService.isDatabaseUp()) {
            response.put("status", "UP");
            response.put("message", "MySQL database is connected ✅");
        } else {
            response.put("status", "DOWN");
            response.put("message", "MySQL database connection failed ❌");
        }
        return response;
    }
}
