package com.nestrr.apps.flock.profile.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nestrr.apps.flock.profile.dto.DegreeDto;
import com.nestrr.apps.flock.profile.mapper.DegreeViewMapper;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

@Component
public class DegreeDtoAttributeConverter implements AttributeConverter<DegreeDto, String> {
  // not using default mapper to avoid changing its naming settings
  private final ObjectMapper objectMapper =
      new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  private final DegreeViewMapper degreeViewMapper;

  public DegreeDtoAttributeConverter(DegreeViewMapper degreeViewMapper) {
    super();
    this.degreeViewMapper = degreeViewMapper;
  }

  @Override
  public String convertToDatabaseColumn(DegreeDto degreeDto) {
    try {
      return objectMapper.writeValueAsString(degreeViewMapper.viewFromDegreeDto(degreeDto));
    } catch (JsonProcessingException jpe) {
      System.out.println("Cannot convert DegreeDto into JSON");
      return null;
    }
  }

  @Override
  public DegreeDto convertToEntityAttribute(String value) {
    if (value == null) return null;
    try {
      return objectMapper.readValue(value, DegreeDto.class);
    } catch (JsonProcessingException e) {
      System.out.println("Cannot convert JSON into DegreeDto");
      return null;
    }
  }
}
