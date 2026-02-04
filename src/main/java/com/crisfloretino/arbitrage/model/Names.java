package com.crisfloretino.arbitrage.model;

import com.google.gson.annotations.SerializedName;

public record Names(
        @SerializedName("long") String longName,
        String medium,
        @SerializedName("short") String shortName,
        String location
) {
}
