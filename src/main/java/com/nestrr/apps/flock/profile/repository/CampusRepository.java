package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Campus;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface CampusRepository
    extends ListPagingAndSortingRepository<Campus, String>, CrudRepository<Campus, String> {
  Optional<Campus> findById(String id);
}
