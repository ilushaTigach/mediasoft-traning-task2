package org.telatenko.service;

import org.telatenko.model.PlayerStats;
import org.telatenko.model.TeamStats;
import java.util.Comparator;
import java.util.Map;

public class TeamStatsService {

    public void addGoal(TeamStats teamStats, String scorer, boolean penalty) {
        Map<String, PlayerStats> playerStatsMap = teamStats.getPlayerStatsMap();
        PlayerStats playerStats = playerStatsMap.getOrDefault(scorer, new PlayerStats(scorer));
        playerStats.incrementGoalsScored();
        playerStatsMap.put(scorer, playerStats);
        teamStats.setPlayerStatsMap(playerStatsMap);
    }

    public PlayerStats getTopScorer(TeamStats teamStats) {
        return teamStats.getPlayerStatsMap().values().stream()
                .max(Comparator.comparingInt(PlayerStats::getGoalsScored))
                .orElse(new PlayerStats(""));
    }
}
