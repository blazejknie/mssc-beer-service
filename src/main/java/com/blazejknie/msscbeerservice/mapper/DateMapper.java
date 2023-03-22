package com.blazejknie.msscbeerservice.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {
    OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            LocalDateTime dateTime = timestamp.toLocalDateTime();
            return OffsetDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                    dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    Timestamp toTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.toLocalDateTime());
        } else {
            return null;
        }
    }
}
