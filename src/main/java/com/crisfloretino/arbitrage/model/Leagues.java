package com.crisfloretino.arbitrage.model;

import lombok.Getter;

public enum Leagues {
    NBA("NBA", 1),
    NFL("NFL", 2),
    NHL("NHL", 3),
    MLB("MLB", 4);

    @Getter
    private final String name;
    @Getter
    private final int id;

    Leagues(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Leagues fromId(int id) {
        for (Leagues league : values()) {
            if (league.id == id) { return league; }
        }

        throw new IllegalArgumentException("ID does not exist.");
    }
}
