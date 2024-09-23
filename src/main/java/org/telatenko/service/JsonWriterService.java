package org.telatenko.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.telatenko.model.PlayerStats;
import org.telatenko.model.TeamStats;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonWriterService {

    public void writeJson(List<TeamStats> teamStatsList, String jsonFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode rootNode = objectMapper.createArrayNode();

        teamStatsList.stream().forEach(teamStats -> {
            ObjectNode teamNode = objectMapper.createObjectNode();
            teamNode.put("team", teamStats.getTeam());
            teamNode.put("goals_scored", teamStats.getGoalsScored());
            teamNode.put("goals_missed", teamStats.getGoalsMissed());

            PlayerStats topScorer = new TeamStatsService().getTopScorer(teamStats);
            ObjectNode topScorerNode = objectMapper.createObjectNode();
            topScorerNode.put("name", topScorer.getName());
            topScorerNode.put("goals_scored", topScorer.getGoalsScored());

            teamNode.set("top_scorer", topScorerNode);
            rootNode.add(teamNode);
        });

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFile), rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
