package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupMembershipView;
import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupMembershipViewRepository
    extends ListPagingAndSortingRepository<GroupMembershipView, GroupMembershipId>,
        ListCrudRepository<GroupMembershipView, GroupMembershipId>,
        JpaRepository<GroupMembershipView, GroupMembershipId> {
  List<GroupMembershipView> findByIdGroupId(String groupId);
}
