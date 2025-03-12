package com.nestrr.apps.flock.group.service;

import static com.nestrr.apps.flock.util.BeanCopyUtils.copyNonNullProperties;

import com.nestrr.apps.flock.group.constants.GroupStatuses;
import com.nestrr.apps.flock.group.dto.NewGroupRequest;
import com.nestrr.apps.flock.group.dto.UpdateGroupRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupStatus;
import com.nestrr.apps.flock.group.repository.GroupRepository;
import com.nestrr.apps.flock.group.repository.GroupStatusRepository;
import com.nestrr.apps.flock.messaging.dto.AdminChangeContext;
import com.nestrr.apps.flock.messaging.service.MessagingService;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;
  private final GroupStatusRepository groupStatusRepository;
  private final MessagingService messagingService;
  private final PersonRepository personRepository;

  public GroupServiceImpl(
      GroupRepository groupRepository,
      GroupStatusRepository groupStatusRepository,
      MessagingService messagingService,
      PersonRepository personRepository) {
    this.groupRepository = groupRepository;
    this.groupStatusRepository = groupStatusRepository;
    this.messagingService = messagingService;
    this.personRepository = personRepository;
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
    groupRepository.save(group);

    if (groupFromUpdate.getAdminId() != null
        && !groupFromUpdate.getAdminId().equals(group.getAdminId())) {
      Person oldAdmin = personRepository.findById(group.getAdminId()).orElseThrow();
      Person newAdmin = personRepository.findById(groupFromUpdate.getAdminId()).orElseThrow();
      AdminChangeContext context =
          AdminChangeContext.builder()
              .group(group)
              .oldAdmin(oldAdmin)
              .newAdmin(newAdmin)
              .build(); // Use old details of group in change context, so that emails refer to the
      // well-known details of the group
      messagingService.sendAdminChangeNotification(context);
    }
    return groupFromUpdate;
  }

  @Override
  public Boolean isGroupOwner(String groupId, String personId) {
    Group group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new NoSuchElementException("Group does not exist."));
    return group.getAdminId().equals(personId);
  }
}
