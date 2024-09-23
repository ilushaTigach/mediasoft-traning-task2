package org.telatenko.model;

import java.util.HashMap;
import java.util.Map;

public class TeamStats {
    private String team;
    private int goalsScored;
    private int goalsMissed;
    private Map<String, PlayerStats> playerStatsMap;

    public TeamStats(String team) {
        this.team = team;
        this.goalsScored = 0;
        this.goalsMissed = 0;
        this.playerStatsMap = new HashMap<>();
    }

    public String getTeam() {
        return team;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsMissed() {
        return goalsMissed;
    }

    public void setGoalsMissed(int goalsMissed) {
        this.goalsMissed = goalsMissed;
    }

    public Map<String, PlayerStats> getPlayerStatsMap() {
        return playerStatsMap;
    }

    public void setPlayerStatsMap(Map<String, PlayerStats> playerStatsMap) {
        this.playerStatsMap = playerStatsMap;
    }
}