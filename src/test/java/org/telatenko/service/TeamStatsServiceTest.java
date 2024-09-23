package org.telatenko.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telatenko.model.PlayerStats;
import org.telatenko.model.TeamStats;
import java.util.Map;

public class TeamStatsServiceTest {

    private TeamStatsService teamStatsService;
    private TeamStats teamStats;

    @BeforeEach
    public void setUp() {
        teamStatsService = new TeamStatsService();
        teamStats = new TeamStats("TeamA");
    }

    @Test
    public void testAddGoal_SingleGoal() {
        teamStatsService.addGoal(teamStats, "Player1", false);

        Map<String, PlayerStats> playerStatsMap = teamStats.getPlayerStatsMap();
        assertEquals(1, playerStatsMap.size());

        PlayerStats playerStats = playerStatsMap.get("Player1");
        assertNotNull(playerStats);
        assertEquals("Player1", playerStats.getName());
        assertEquals(1, playerStats.getGoalsScored());
    }

    @Test
    public void testAddGoal_MultipleGoals() {
        teamStatsService.addGoal(teamStats, "Player1", false);
        teamStatsService.addGoal(teamStats, "Player2", false);
        teamStatsService.addGoal(teamStats, "Player1", false);

        Map<String, PlayerStats> playerStatsMap = teamStats.getPlayerStatsMap();
        assertEquals(2, playerStatsMap.size());

        PlayerStats player1Stats = playerStatsMap.get("Player1");
        assertNotNull(player1Stats);
        assertEquals("Player1", player1Stats.getName());
        assertEquals(2, player1Stats.getGoalsScored());

        PlayerStats player2Stats = playerStatsMap.get("Player2");
        assertNotNull(player2Stats);
        assertEquals("Player2", player2Stats.getName());
        assertEquals(1, player2Stats.getGoalsScored());
    }

    @Test
    public void testGetTopScorer_SinglePlayer() {
        teamStatsService.addGoal(teamStats, "Player1", false);

        PlayerStats topScorer = teamStatsService.getTopScorer(teamStats);
        assertNotNull(topScorer);
        assertEquals("Player1", topScorer.getName());
        assertEquals(1, topScorer.getGoalsScored());
    }

    @Test
    public void testGetTopScorer_MultiplePlayers() {
        teamStatsService.addGoal(teamStats, "Player1", false);
        teamStatsService.addGoal(teamStats, "Player2", false);
        teamStatsService.addGoal(teamStats, "Player1", false);
        teamStatsService.addGoal(teamStats, "Player3", false);

        PlayerStats topScorer = teamStatsService.getTopScorer(teamStats);
        assertNotNull(topScorer);
        assertEquals("Player1", topScorer.getName());
        assertEquals(2, topScorer.getGoalsScored());
    }

    @Test
    public void testGetTopScorer_NoGoals() {
        PlayerStats topScorer = teamStatsService.getTopScorer(teamStats);
        assertNotNull(topScorer);
        assertEquals("", topScorer.getName());
        assertEquals(0, topScorer.getGoalsScored());
    }
}