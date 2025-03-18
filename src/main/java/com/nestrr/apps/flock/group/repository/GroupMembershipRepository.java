package com.nestrr.apps.flock.group.repository;

import com.nestrr.apps.flock.group.entity.GroupMembership;
import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface GroupMembershipRepository
    extends ListPagingAndSortingRepository<GroupMembership, GroupMembershipId>,
        ListCrudRepository<GroupMembership, GroupMembershipId>,
        JpaRepository<GroupMembership, GroupMembershipId> {

  List<GroupMembership> findByIdGroupId(String groupId);
}
