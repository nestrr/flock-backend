package com.nestrr.apps.flock.degreetype.repository;

import com.nestrr.apps.flock.degreetype.entity.DegreeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface DegreeTypeRepository
    extends ListPagingAndSortingRepository<DegreeType, String>,
        ListCrudRepository<DegreeType, String> {

  @Query(nativeQuery = true, value = "SELECT * FROM degree_type WHERE code=?1")
  Optional<DegreeType> findByCode(String code);
}
