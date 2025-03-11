package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.constants.GroupInviteConstants;
import com.nestrr.apps.flock.group.constants.GroupInviteStatuses;
import com.nestrr.apps.flock.group.dto.NewGroupInviteRequest;
import com.nestrr.apps.flock.group.entity.Group;
import com.nestrr.apps.flock.group.entity.GroupInvite;
import com.nestrr.apps.flock.group.entity.GroupInviteStatus;
import com.nestrr.apps.flock.group.entity.id.GroupInviteId;
import com.nestrr.apps.flock.group.repository.GroupInviteRepository;
import com.nestrr.apps.flock.group.repository.GroupInviteStatusRepository;
import com.nestrr.apps.flock.messaging.dto.GroupInviteContext;
import com.nestrr.apps.flock.messaging.service.MessagingService;
import com.nestrr.apps.flock.profile.entity.Person;
import com.nestrr.apps.flock.profile.repository.PersonRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupInviteServiceImpl implements GroupInviteService {
  private final GroupInviteRepository groupInviteRepository;
  private final GroupInviteStatusRepository groupInviteStatusRepository;
  private final PersonRepository personRepository;
  private final MessagingService messagingService;

  public GroupInviteServiceImpl(
      GroupInviteRepository groupInviteRepository,
      GroupInviteStatusRepository groupInviteStatusRepository,
      MessagingService messagingService,
      PersonRepository personRepository) {
    this.groupInviteRepository = groupInviteRepository;
    this.groupInviteStatusRepository = groupInviteStatusRepository;
    this.messagingService = messagingService;
    this.personRepository = personRepository;
  }

  @Transactional
  @Override
  public void createInvites(NewGroupInviteRequest request) {
    this.storeInvite(request.groupId(), request.recipientId(), getPendingStatusId());
  }

  @Transactional
  @Override
  public void createInvites(Group group, List<String> recipientIds) {
    recipientIds.forEach(
        recipientId -> storeInvite(group.getId(), recipientId, getPendingStatusId()));
    sendInvites(group, recipientIds);
  }

  private void storeInvite(String groupId, String recipientId, String statusId) {
    groupInviteRepository.save(
        GroupInvite.builder()
            .id(new GroupInviteId(groupId, recipientId))
            .statusId(statusId)
            .expiresOn(LocalDateTime.now().plusDays(GroupInviteConstants.INVITE_WAIT_PERIOD_DAYS))
            .build());
  }

  private String getPendingStatusId() {
    String pending = GroupInviteStatuses.RESPONSE_PENDING.value();

    return groupInviteStatusRepository
        .findByName(pending)
        .map(GroupInviteStatus::getId)
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    String.format(
                        "No appropriate group invite status ID found for status: %s.", pending)));
  }

  private void sendInvites(Group group, List<String> recipientIds) {
    Person admin =
        personRepository
            .findById(group.getAdminId())
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format(
                            "No Person object found for admin ID %s (group ID %s)",
                            group.getAdminId(), group.getId())));

    GroupInviteContext context = GroupInviteContext.builder().group(group).admin(admin).build();
    messagingService.sendInviteNotification(context, recipientIds);
  }
}
