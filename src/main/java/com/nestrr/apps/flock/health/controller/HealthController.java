package com.nestrr.apps.flock.health.controller;

import com.nestrr.apps.flock.health.dto.HealthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

  public HealthController() {}

  @GetMapping
  public ResponseEntity<HealthDto> healthCheck() {
    return ResponseEntity.ok(HealthDto.builder().health(true).build());
  }
}
