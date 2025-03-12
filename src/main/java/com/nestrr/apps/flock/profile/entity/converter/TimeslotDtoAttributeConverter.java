package com.nestrr.apps.flock.profile.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nestrr.apps.flock.profile.dto.TimeslotDto;
import com.nestrr.apps.flock.profile.entity.TimeslotView;
import com.nestrr.apps.flock.profile.mapper.TimeslotMapper;
import jakarta.persistence.AttributeConverter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TimeslotDtoAttributeConverter
    implements AttributeConverter<List<TimeslotDto>, String> {
  // not using default mapper to avoid changing its naming settings
  private final ObjectMapper objectMapper =
      new ObjectMapper()
          .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
          .registerModule(new JavaTimeModule());
  private final TimeslotMapper timeslotMapper;
  private final Logger log;

  public TimeslotDtoAttributeConverter(TimeslotMapper timeslotMapper) {
    super();
    this.timeslotMapper = timeslotMapper;
    this.log = LoggerFactory.getLogger(TimeslotDtoAttributeConverter.class);
  }

  @Override
  public String convertToDatabaseColumn(List<TimeslotDto> timeslotDtos) {
    try {
      return objectMapper.writeValueAsString(
          timeslotDtos.stream().map(timeslotMapper::viewFromTimeslotDto).toList());
    } catch (JsonProcessingException jpe) {
      log.error("Cannot convert TimeslotDto list into JSON");
      return null;
    }
  }

  @Override
  public List<TimeslotDto> convertToEntityAttribute(String value) {
    if (value == null) return null;
    try {
      List<TimeslotView> timeslotViewObjects =
          objectMapper.readValue(value, new TypeReference<List<TimeslotView>>() {});
      return timeslotViewObjects.stream().map(timeslotMapper::toTimeslotDto).toList();
    } catch (JsonProcessingException e) {
      log.error("Cannot convert JSON into TimeslotDto list");
      return null;
    }
  }
}
