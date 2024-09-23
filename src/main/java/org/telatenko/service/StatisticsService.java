package org.telatenko.service;

import org.telatenko.model.TeamStats;
import java.util.*;

public class StatisticsService {

    private TeamStatsService teamStatsService = new TeamStatsService();
    public List<TeamStats> processStatistics(List<String[]> records) {
        Map<String, TeamStats> teamStatsMap = new HashMap<>();

        for (String[] line : records) {
            if (line.length < 8) {
                System.err.println("Invalid record: " + String.join(",", line));
                continue;
            }

            String date = line[0];
            String homeTeam = line[1];
            String awayTeam = line[2];
            String team = line[3];
            String scorer = line[4];
            String minute = line[5];
            boolean ownGoal = Boolean.parseBoolean(line[6]);
            boolean penalty = Boolean.parseBoolean(line[7]);

            TeamStats teamStats = teamStatsMap.getOrDefault(team, new TeamStats(team));

            if (ownGoal) {
                if (team.equals(homeTeam)) {
                    teamStats.setGoalsMissed(teamStats.getGoalsMissed() + 1);
                } else {
                    teamStats.setGoalsMissed(teamStats.getGoalsMissed() + 1);
                }
            } else {
                teamStats.setGoalsScored(teamStats.getGoalsScored() + 1);
                teamStatsService.addGoal(teamStats, scorer, penalty);
            }

            teamStatsMap.put(team, teamStats);
        }

        List<TeamStats> teamStatsList = new ArrayList<>(teamStatsMap.values());
        teamStatsList.sort((t1, t2) -> {
            int goalsCompare = Integer.compare(t2.getGoalsScored(), t1.getGoalsScored());
            if (goalsCompare != 0) {
                return goalsCompare;
            }
            return t1.getTeam().compareTo(t2.getTeam());
        });

        return teamStatsList;
    }
}