package com.nestrr.apps.flock.group.mapper;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.dto.GroupStatusDto;
import com.nestrr.apps.flock.group.entity.GroupView;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {
  default GroupDto toGroupDto(GroupView groupView) {
    return GroupDto.builder()
        .id(groupView.getId())
        .image(groupView.getImage())
        .name(groupView.getName())
        .description(groupView.getDescription())
        .status(
            GroupStatusDto.builder()
                .id(groupView.getStatusId())
                .name(groupView.getStatusName())
                .build())
        .adminId(groupView.getAdminId())
        .build();
  }
}
