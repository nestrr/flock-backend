package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.Group;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupRepository
    extends ListPagingAndSortingRepository<Group, String>, ListCrudRepository<Group, String> {
  @Query(nativeQuery = true, value = "SELECT * FROM \"group\" g WHERE g.admin_id=?1")
  List<Group> findByAdminId(String adminId);
}
