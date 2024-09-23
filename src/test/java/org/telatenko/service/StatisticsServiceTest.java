package org.telatenko.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telatenko.model.PlayerStats;
import org.telatenko.model.TeamStats;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StatisticsServiceTest {

    private StatisticsService statisticsService;

    @BeforeEach
    public void setUp() {
        statisticsService = new StatisticsService();
    }

    @Test
    public void testProcessStatistics_SingleTeamSingleGoal() {
        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamA", "Player1", "10", "false", "false"});

        List<TeamStats> teamStatsList = statisticsService.processStatistics(records);

        assertEquals(1, teamStatsList.size());
        TeamStats teamStats = teamStatsList.get(0);
        assertEquals("TeamA", teamStats.getTeam());
        assertEquals(1, teamStats.getGoalsScored());
        assertEquals(0, teamStats.getGoalsMissed());

        PlayerStats topScorer = new TeamStatsService().getTopScorer(teamStats);
        assertEquals("Player1", topScorer.getName());
        assertEquals(1, topScorer.getGoalsScored());
    }

    @Test
    public void testProcessStatistics_MultipleTeamsMultipleGoals() {
        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamA", "Player1", "10", "false", "false"});
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamB", "Player2", "20", "false", "false"});
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamA", "Player1", "30", "false", "false"});
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamB", "Player3", "40", "false", "false"});

        List<TeamStats> teamStatsList = statisticsService.processStatistics(records);

        assertEquals(2, teamStatsList.size());

        TeamStats teamAStats = teamStatsList.stream().filter(ts -> ts.getTeam().equals("TeamA")).findFirst().orElse(null);
        assertNotNull(teamAStats);
        assertEquals(2, teamAStats.getGoalsScored());

        PlayerStats topScorerTeamA = new TeamStatsService().getTopScorer(teamAStats);
        assertEquals("Player1", topScorerTeamA.getName());
        assertEquals(2, topScorerTeamA.getGoalsScored());

        TeamStats teamBStats = teamStatsList.stream().filter(ts -> ts.getTeam().equals("TeamB")).findFirst().orElse(null);
        assertNotNull(teamBStats);
        assertEquals(2, teamBStats.getGoalsScored());

        PlayerStats topScorerTeamB = new TeamStatsService().getTopScorer(teamBStats);
        assertEquals("Player2", topScorerTeamB.getName());
        assertEquals(1, topScorerTeamB.getGoalsScored());
    }

    @Test
    public void testProcessStatistics_OwnGoal() {
        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamA", "Player1", "10", "true", "false"});

        List<TeamStats> teamStatsList = statisticsService.processStatistics(records);

        assertEquals(1, teamStatsList.size());
        TeamStats teamStats = teamStatsList.get(0);
        assertEquals("TeamA", teamStats.getTeam());
        assertEquals(0, teamStats.getGoalsScored());
        assertEquals(1, teamStats.getGoalsMissed());

        PlayerStats topScorer = new TeamStatsService().getTopScorer(teamStats);
        assertEquals("", topScorer.getName());
        assertEquals(0, topScorer.getGoalsScored());
    }

    @Test
    public void testProcessStatistics_InvalidRecord() {
        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamA", "Player1", "10", "false"});
        records.add(new String[]{"2023-01-01", "TeamA", "TeamB", "TeamA", "Player1", "20", "false", "false"});

        List<TeamStats> teamStatsList = statisticsService.processStatistics(records);

        assertEquals(1, teamStatsList.size());
        TeamStats teamStats = teamStatsList.get(0);
        assertEquals("TeamA", teamStats.getTeam());
        assertEquals(1, teamStats.getGoalsScored());
        assertEquals(0, teamStats.getGoalsMissed());

        PlayerStats topScorer = new TeamStatsService().getTopScorer(teamStats);
        assertEquals("Player1", topScorer.getName());
        assertEquals(1, topScorer.getGoalsScored());
    }
}