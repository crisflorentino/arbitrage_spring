package com.crisfloretino.arbitrage.model;

import java.util.List;

public record Periods(
        List<String> started,
        List<String> ended
) {
}
