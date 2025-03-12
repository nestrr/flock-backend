package com.nestrr.apps.flock.profile.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestrr.apps.flock.profile.dto.CampusDto;
import jakarta.persistence.AttributeConverter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampusDtoAttributeConverter implements AttributeConverter<List<CampusDto>, String> {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final Logger log = LoggerFactory.getLogger(CampusDtoAttributeConverter.class);

  @Override
  public String convertToDatabaseColumn(List<CampusDto> campusDtos) {
    try {
      return objectMapper.writeValueAsString(campusDtos);
    } catch (JsonProcessingException jpe) {
      log.error("Cannot convert CampusDto list into JSON");
      return null;
    }
  }

  @Override
  public List<CampusDto> convertToEntityAttribute(String value) {
    if (value == null) return null;
    try {
      return objectMapper.readValue(value, new TypeReference<List<CampusDto>>() {});
    } catch (JsonProcessingException e) {
      log.error("Cannot convert JSON into CampusDto list");
      return null;
    }
  }
}
