package org.telatenko;

import org.telatenko.model.TeamStats;
import org.telatenko.service.CSVReaderService;
import org.telatenko.service.JsonWriterService;
import org.telatenko.service.StatisticsService;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvFile = "src/main/resources/goalscorers.csv";
        String jsonFile = "output.json";

        CSVReaderService csvReaderService = new CSVReaderService();
        StatisticsService statisticsService = new StatisticsService();
        JsonWriterService jsonWriterService = new JsonWriterService();

        List<String[]> records = csvReaderService.readCsv(csvFile);
        List<TeamStats> teamStatsList = statisticsService.processStatistics(records);
        jsonWriterService.writeJson(teamStatsList, jsonFile);
    }
}