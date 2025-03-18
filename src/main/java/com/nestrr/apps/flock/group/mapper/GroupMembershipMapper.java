package com.nestrr.apps.flock.group.mapper;

import com.nestrr.apps.flock.group.dto.GroupMembershipDto;
import com.nestrr.apps.flock.group.entity.GroupMembershipView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMembershipMapper {
  @Mapping(source = "id.personId", target = "personId")
  @Mapping(source = "id.groupId", target = "groupId")
  GroupMembershipDto toGroupMembershipDto(GroupMembershipView groupMembershipView);
}
