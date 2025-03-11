package com.nestrr.apps.flock.group.service;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.entity.GroupMembership;
import com.nestrr.apps.flock.group.entity.id.GroupMembershipId;
import com.nestrr.apps.flock.group.mapper.GroupMapper;
import com.nestrr.apps.flock.group.repository.GroupMembershipRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {
  private final GroupMembershipRepository groupMembershipRepository;
  private final GroupMapper groupMapper;

  public GroupMembershipServiceImpl(
      GroupMembershipRepository groupMembershipRepository, GroupMapper groupMapper) {
    this.groupMembershipRepository = groupMembershipRepository;
    this.groupMapper = groupMapper;
  }

  @Override
  @Transactional
  public void addMember(String groupId, String personId) {
    groupMembershipRepository.save(
        GroupMembership.builder().id(new GroupMembershipId(groupId, personId)).build());
  }

  @Override
  @Transactional
  public void removeMember(String groupId, String personId) {
    groupMembershipRepository.deleteById(new GroupMembershipId(groupId, personId));
  }

  @Override
  public List<GroupDto> getGroupsByPersonId(String personId) {
    return groupMembershipRepository.findGroupsByGroupId(personId).stream()
        .map(groupMapper::toGroupDto)
        .toList();
  }
}
