package org.telatenko.model;

public class PlayerStats {
    private String name;
    private int goalsScored;

    public PlayerStats(String name) {
        this.name = name;
        this.goalsScored = 0;
    }

    public void incrementGoalsScored() {
        this.goalsScored++;
    }

    public String getName() {
        return name;
    }

    public int getGoalsScored() {
        return goalsScored;
    }
}
