package com.nestrr.apps.flock.degreetype.controller;

import com.nestrr.apps.flock.degreetype.dto.DegreeTypeDto;
import com.nestrr.apps.flock.degreetype.service.DegreeTypeService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/degree-type")
public class DegreeTypeController {
  private final DegreeTypeService degreeTypeService;

  public DegreeTypeController(DegreeTypeService degreeTypeService) {
    this.degreeTypeService = degreeTypeService;
  }

  @GetMapping
  public ResponseEntity<List<DegreeTypeDto>> getDegreeTypes() {
    List<DegreeTypeDto> degreeTypes = degreeTypeService.getAllDegreeTypes();
    return ResponseEntity.ok(degreeTypes);
  }
}
