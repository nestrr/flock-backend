package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.BeanCopyUtils.copyNonNullProperties;

import com.nestrr.apps.flock.group.constants.GroupStatuses;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupStatus;
import com.nestrr.apps.flock.group.repository.GroupRepository;
import com.nestrr.apps.flock.group.repository.GroupStatusRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
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
  public Optional<Group> findGroupById(String groupId) {
    return groupRepository.findById(groupId);
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

  @Override
  public Group updateGroup(String groupId, UpdateGroupRequest updateGroupRequest) {
    Group groupFromUpdate =
        Group.builder()
            .id(groupId)
            .name(updateGroupRequest.name())
            .image(updateGroupRequest.image())
            .description(updateGroupRequest.description())
            .adminId(updateGroupRequest.adminId())
            .build();
    Group group = groupRepository.findById(groupId).orElseThrow();
    copyNonNullProperties(group, groupFromUpdate);
    return groupRepository.save(group);
  }

  @Override
  public Boolean isGroupAdmin(String groupId, String personId) {
    Group group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new NoSuchElementException("Group does not exist."));
    return group.getAdminId().equals(personId);
  }

  @Override
  public void deleteGroup(String groupId) {
    groupRepository.deleteById(groupId);
  }

  @Override
  public Group getGroup(String groupId) throws NoSuchElementException {
    return groupRepository
        .findById(groupId)
        .orElseThrow(
            () -> new NoSuchElementException(String.format("No group with group ID %s", groupId)));
  }
}
