package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupMembership;
import com.nestrr.apps.flock.group.entity.GroupView;
import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupMembershipRepository
    extends ListPagingAndSortingRepository<GroupMembership, GroupMembershipId>,
        ListCrudRepository<GroupMembership, GroupMembershipId>,
        JpaRepository<GroupMembership, GroupMembershipId> {
  @Query(
      nativeQuery = true,
      value =
          "SELECT gv.* FROM group_membership gm JOIN group_view gv ON gm.group_id=gv.id WHERE gm.person_id=?1")
  List<GroupView> findGroupsByGroupId(String personId);

  List<GroupMembership> findByIdGroupId(String groupId);
}
