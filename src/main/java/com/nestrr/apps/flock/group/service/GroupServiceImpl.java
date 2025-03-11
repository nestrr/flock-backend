package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupStatuses;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupStatus;
import com.nestrr.apps.flock.group.repository.GroupRepository;
import com.nestrr.apps.flock.group.repository.GroupStatusRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;
  private final GroupStatusRepository groupStatusRepository;

  public GroupServiceImpl(
      GroupRepository groupRepository, GroupStatusRepository groupStatusRepository) {
    this.groupRepository = groupRepository;
    this.groupStatusRepository = groupStatusRepository;
  }

  @Override
  @Transactional
  public Group createGroup(String creatorId, NewGroupRequest newGroupRequest) {
    String pending = GroupStatuses.PENDING.value();

    String pendingStatusId =
        groupStatusRepository
            .findByName(pending)
            .map(GroupStatus::getId)
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "No appropriate group status ID found for status: pending."));
    return groupRepository.save(
        Group.builder()
            .name(newGroupRequest.name())
            .description(newGroupRequest.description())
            .image(newGroupRequest.image())
            .adminId(creatorId)
            .statusId(pendingStatusId)
            .build());
  }
}
