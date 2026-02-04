package com.crisfloretino.arbitrage.model;

import java.util.List;

public record APIResponse(
        String nextCursor,
        String notice,
        boolean success,
        List<Event> data
) {
}
