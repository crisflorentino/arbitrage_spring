package com.crisfloretino.arbitrage.model;

import java.util.List;

public record Status(
        boolean started,
        boolean completed,
        boolean cancelled,
        boolean ended,
        boolean live,
        boolean delayed,
        String currentPeriodID,
        String previousPeriodID,
        String displayShort,
        String displayLong,
        boolean inBreak,
        String startsAt,
        List<String> previousStartsAt,
        boolean hardStart,
        Periods periods,
        boolean oddsPresent,
        boolean oddsAvailable,
        boolean finalized
) {
}
