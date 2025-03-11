package com.nestrr.apps.flock.group.mapper;

import com.nestrr.apps.flock.group.dto.GroupDto;
import com.nestrr.apps.flock.group.entity.GroupView;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {
  GroupDto toGroupDto(GroupView groupView);
}
