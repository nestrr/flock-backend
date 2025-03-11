package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Optional;

public interface GroupInviteStatusRepository
    extends ListPagingAndSortingRepository<GroupInviteStatus, String>,
        ListCrudRepository<GroupInviteStatus, String> {
  @Query(nativeQuery = true, value = "SELECT * FROM group_invite_status gs WHERE gs.name=?1")
  Optional<GroupInviteStatus> findByName(String name);
}
