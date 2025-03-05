package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.DegreeView;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.Repository;

public interface DegreeViewRepository
    extends ListPagingAndSortingRepository<DegreeView, String>, Repository<DegreeView, String> {

  @Query(
      nativeQuery = true,
      value = "SELECT * FROM degree_view d WHERE degree_type_code=?1 AND program_code=?2")
  Optional<DegreeView> findByTypeAndProgramCodes(String degreeTypeCode, String programCode);

  Optional<DegreeView> findById(String degreeId);
}
