package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Reliability;
import java.util.Optional;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ReliabilityRepository extends ListPagingAndSortingRepository<Reliability, String> {
  Optional<Reliability> findByCode(Integer code);
}
