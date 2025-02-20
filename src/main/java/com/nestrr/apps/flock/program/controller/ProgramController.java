package com.nestrr.apps.flock.program.controller;

import com.nestrr.apps.flock.program.dto.ProgramDto;
import com.nestrr.apps.flock.program.service.ProgramService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/program")
public class ProgramController {
  private final ProgramService programService;

  public ProgramController(ProgramService programService) {
    this.programService = programService;
  }

  @GetMapping
  public ResponseEntity<List<ProgramDto>> getPrograms(
      @RequestParam(name = "degreeType", required = false) String degreeType) {
    List<ProgramDto> programs =
        StringUtils.isBlank(degreeType)
            ? programService.getAllPrograms()
            : programService.getProgramsByDegreeTypeCode(degreeType);
    return ResponseEntity.ok(programs);
  }
}
