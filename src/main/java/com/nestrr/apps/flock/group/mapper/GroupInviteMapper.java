package com.nestrr.apps.flock.group.mapper;

import com.nestrr.apps.flock.group.dto.GroupInviteDto;
import com.nestrr.apps.flock.group.entity.GroupInviteView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupInviteMapper {
  @Mapping(source = "id.personId", target = "personId")
  @Mapping(source = "id.groupId", target = "groupId")
  GroupInviteDto toGroupInviteDto(GroupInviteView groupInviteView);
}
