package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.Major;
import java.util.Optional;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface MajorRepository extends ListPagingAndSortingRepository<Major, String> {
  Optional<Major> findById(String id);
}
