package com.crisfloretino.arbitrage.model;

public record BookmakerOdd(
        String odds,
        String lastUpdatedAt,
        boolean available,
        String deeplink
) {
}
