package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Optional;

public interface GroupStatusRepository
    extends ListPagingAndSortingRepository<GroupStatus, String>,
        ListCrudRepository<GroupStatus, String> {

  @Query(nativeQuery = true, value = "SELECT * FROM group_status gs WHERE gs.name=?1")
  Optional<GroupStatus> findByName(String name);
}
