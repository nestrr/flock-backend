package com.nestrr.apps.flock.campus.repository;

import com.nestrr.apps.flock.campus.entity.Campus;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CampusRepository
    extends ListPagingAndSortingRepository<Campus, String>, ListCrudRepository<Campus, String> {
  Optional<Campus> findById(String id);
}
