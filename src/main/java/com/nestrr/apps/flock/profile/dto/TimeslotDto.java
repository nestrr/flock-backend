package com.nestrr.apps.flock.profile.dto;

import java.time.LocalTime;

public record TimeslotDto(
    Integer day, LocalTime from, LocalTime to, Integer reliability, Integer flexibility) {}
