package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Standing;
import java.util.Optional;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface StandingRepository extends ListPagingAndSortingRepository<Standing, String> {
  Optional<Standing> findById(String id);
}
