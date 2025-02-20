package com.nestrr.apps.flock.campus.controller;

import com.nestrr.apps.flock.campus.dto.CampusDto;
import com.nestrr.apps.flock.campus.service.CampusService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campus")
public class CampusController {
  private final CampusService campusService;

  public CampusController(CampusService campusService) {
    this.campusService = campusService;
  }

  @GetMapping
  public ResponseEntity<List<CampusDto>> getAllCampuses() {
    return ResponseEntity.ok(campusService.getAllCampuses());
  }
}
