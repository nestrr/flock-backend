package com.nestrr.apps.flock.profile.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalTime;

@Embeddable
public record TimeslotViewId(Integer day, LocalTime startTime, LocalTime endTime)
    implements Serializable {}
