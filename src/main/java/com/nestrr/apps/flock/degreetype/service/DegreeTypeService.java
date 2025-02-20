package com.nestrr.apps.flock.degreetype.service;

import com.nestrr.apps.flock.degreetype.dto.DegreeTypeDto;
import java.util.List;

public interface DegreeTypeService {

  List<DegreeTypeDto> getAllDegreeTypes();

  DegreeTypeDto getDegreeTypeByCode(String code);
}
