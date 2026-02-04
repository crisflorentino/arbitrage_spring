package com.crisfloretino.arbitrage.model;

public record Team(
        String teamID,
        Names names,
        Colors colors,
        String statEntityID
) {
    public String getMedium() {
        return names.medium();
    }
}
