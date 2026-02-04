package com.crisfloretino.arbitrage.model;

import java.util.Map;

public record Event(
        String eventID,
        String sportID,
        String leagueID,
        String type,
        Teams teams,
        Status status,
        Info info,
        Links links,
        Map<String, Odd> odds,
        Map<String, Object> results,
        Map<String, Player> players
) {
    public Odd getHomeOdds() {
        return odds.get("points-home-game-ml-home");
    }

    public Odd getAwayOdds() {
        return odds.get("points-away-game-ml-away");
    }

    public Team getHomeTeam() {
        return teams.home();
    }

    public Team getAwayTeam() {
        return teams.away();
    }

}
