package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Degree;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Optional;

public interface DegreeRepository
    extends ListPagingAndSortingRepository<Degree, String>, ListCrudRepository<Degree, String> {

  @Query(
      nativeQuery = true,
      value = "SELECT * FROM degree d WHERE degree_type_code=?1 AND program_code=?2")
  Optional<Degree> findByTypeAndProgramCodes(String degreeTypeCode, String programCode);
}
