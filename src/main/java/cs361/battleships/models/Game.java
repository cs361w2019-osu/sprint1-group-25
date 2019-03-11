package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    public boolean placeShip(Ship ship, int x, char y, boolean isVertical, boolean isSubmerged) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical, isSubmerged);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical(), randSubmerged());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    public boolean moveFleet(char direction){
        if(direction=='n'){

        }
        else if( direction=='e'){

        }
        else if ( direction=='s'){

        }
        else if( direction == 'w'){

        }

    }

    public boolean attack(int x, char  y, boolean isSonar) {
        if (isSonar && opponentsBoard.getSonars() > 0) {
            opponentsBoard.sonar(x, y);
        } else if (isSonar && opponentsBoard.getSonars() < 0) {
            return false;
        } else {
            Result playerAttack = opponentsBoard.attack(x, y);
            if (playerAttack.getResult() == INVALID) {
                return false;
            }
        }

        Result opponentAttackResult;
        do {
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    private char randCol() {
        int random = new Random().nextInt(10);
        return (char) ('A' + random);
    }

    private int randRow() {
        return  new Random().nextInt(10) + 1;
    }

    private boolean randVertical() {
        return new Random().nextBoolean();
    }

    private boolean randSubmerged() {

        // Disable RNG until ships are denied underwater placement
        return false;

        //return new Random().nextBoolean();
    }
}
