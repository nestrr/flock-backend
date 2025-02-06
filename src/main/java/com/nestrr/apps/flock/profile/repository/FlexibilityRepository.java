package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Flexibility;
import java.util.Optional;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface FlexibilityRepository extends ListPagingAndSortingRepository<Flexibility, String> {
  Optional<Flexibility> findByCode(Integer code);
}
