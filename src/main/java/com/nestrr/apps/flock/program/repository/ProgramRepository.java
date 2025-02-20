package com.nestrr.apps.flock.program.repository;

import com.nestrr.apps.flock.program.entity.Program;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ProgramRepository
    extends ListPagingAndSortingRepository<Program, String>, ListCrudRepository<Program, String> {

  @Query(nativeQuery = true, value = "SELECT * FROM program WHERE code=?1")
  Optional<Program> findByCode(String code);

  @Query(
      nativeQuery = true,
      value =
          "SELECT p.* FROM program p JOIN degree d ON d.program_code = p.code WHERE d.degree_type_code=?1")
  Optional<List<Program>> findByDegreeType(String degreeTypeCode);
}
