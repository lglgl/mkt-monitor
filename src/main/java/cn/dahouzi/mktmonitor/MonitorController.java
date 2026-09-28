package cn.dahouzi.mktmonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class MonitorController {

    @GetMapping("/")
    public Map<String, Object> index() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("application", "mkt-monitor");
        response.put("status", "UP");
        response.put("timestamp", Instant.now().toString());

        return response;
    }

    @GetMapping("/api/monitor")
    public Map<String, Object> monitor() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("service", "market-monitor");
        response.put("message", "Market monitor service is running");
        response.put("timestamp", Instant.now().toString());

        return response;
    }
}