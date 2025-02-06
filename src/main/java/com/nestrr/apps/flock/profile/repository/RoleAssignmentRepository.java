package com.nestrr.apps.flock.profile.repository;

import com.nestrr.apps.flock.profile.entity.RoleAssignment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface RoleAssignmentRepository
    extends ListPagingAndSortingRepository<RoleAssignment, String> {
  @Query(
      nativeQuery = true,
      value =
          "SELECT r.name FROM role_assignment ra JOIN role r ON ra.role_id=r.id WHERE ra.person_id=?")
  Optional<List<String>> listRolesByPersonId(String subId);
}
