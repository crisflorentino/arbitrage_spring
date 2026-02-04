package com.crisfloretino.arbitrage.model;

import java.util.Map;

public record Odd(
        String oddID,
        String opposingOddID,
        String marketName,
        String statID,
        String statEntityID,
        String periodID,
        String betTypeID,
        String sideID,
        boolean started,
        boolean ended,
        boolean cancelled,
        boolean bookOddsAvailable,
        boolean fairOddsAvailable,
        String fairOdds,
        String bookOdds,
        String openFairOdds,
        String openBookOdds,
        boolean scoringSupported,
        Map<String, BookmakerOdd> byBookmaker
) {
}
