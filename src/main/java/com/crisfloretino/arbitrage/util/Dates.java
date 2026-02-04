package com.crisfloretino.arbitrage.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public final class Dates {
    private static final ZoneId ZONE = ZoneId.of("UTC");

    private Dates() {}

    public static String todayStart() {
        return LocalDate.now(ZONE)
                .atStartOfDay(ZONE)
                .format(DateTimeFormatter.ISO_INSTANT);
    }

    public static String todayEnd() {
        return LocalDate.now(ZONE)
                .plusDays(1)
                .atStartOfDay(ZONE)
                .minusSeconds(1)
                .format(DateTimeFormatter.ISO_INSTANT);
    }
}