package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupViewRepository
    extends ListPagingAndSortingRepository<GroupView, String>,
        ListCrudRepository<GroupView, String>,
        JpaRepository<GroupView, String> {
  @Query(
      nativeQuery = true,
      value =
          "SELECT gv.* FROM group_membership gm JOIN group_view gv ON gm.group_id=gv.id WHERE gm.person_id=?1")
  List<GroupView> findGroupsByPersonId(String personId);

  @Query(
      nativeQuery = true,
      value =
          "SELECT gv.* FROM group_membership gm JOIN group_view gv ON gm.group_id=gv.id WHERE gm.person_id=?1 AND gv.status_name=?2")
  List<GroupView> findGroupsByPersonAndStatusName(String personId, String statusName);
}
