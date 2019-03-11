package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class AttackGameAction {

    @JsonProperty private Game game;
    @JsonProperty private int x;
    @JsonProperty private char y;
    @JsonProperty private boolean isSonar;
    @JsonProperty private boolean isDirNorth;
    @JsonProperty private boolean isDirEast;
    @JsonProperty private boolean isDirSouth;
    @JsonProperty private boolean isDirWest;
    @JsonProperty private char isDirection;


    public Game getGame() {
        return game;
    }

    public int getActionRow() {
        return x;
    }

    public char getActionColumn() {
        return y;
    }

    public boolean isSonar() {
        return isSonar;
    }

    public boolean isDirNorth() {return isDirNorth; }

    public boolean isDirEast() { return isDirEast; }

    public boolean isDirSouth() { return isDirSouth; }

    public boolean isDirWest() { return isDirWest; }

    public char getDirection() {return isDirection; }
}
