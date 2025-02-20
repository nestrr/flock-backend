package com.nestrr.apps.flock.standing.controller;

import com.nestrr.apps.flock.standing.dto.StandingDto;
import com.nestrr.apps.flock.standing.service.StandingService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/standing")
public class StandingController {
  private final StandingService standingService;

  public StandingController(StandingService standingService) {
    this.standingService = standingService;
  }

  @GetMapping
  public ResponseEntity<List<StandingDto>> getProfile() {
    List<StandingDto> standings = standingService.getAllStandings();
    return ResponseEntity.ok(standings);
  }
}
