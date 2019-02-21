package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void testGetResult() {
        Square testSquare = new Square(1, 'A');
        Result testResult = new Result(testSquare);
        AtackStatus testAtackStatus = testResult.getResult();
        assertEquals(testAtackStatus, AtackStatus.MISS);
    }

    @Test
    public void testSetResult() {
        Square testSquare = new Square(1, 'A');
        Result testResult = new Result(testSquare);
        testResult.setResult(AtackStatus.HIT);
        assertEquals(testResult, AtackStatus.HIT);
    }

    @Test
    public void testGetShip() {
        Square testSquare = new Square(1, 'A');
        Result testResult = new Result(testSquare);
        Ship testShip = new Ship("Minesweeper");
        testResult.setShip(testShip);
        Ship resultShip = testResult.getShip();
        assertEquals(testShip, resultShip);
    }

    @Test
    public void testSetShip() {
        Square testSquare = new Square(1, 'A');
        Result testResult = new Result(testSquare);
        Ship testShip = new Ship("Minesweeper");
        testResult.setShip(testShip);
        Ship resultShip = testResult.getShip();
        assertEquals(testShip, resultShip);
    }

    @Test
    public void testGetLocation() {
        Square testSquare = new Square(1, 'A');
        Result testResult = new Result(testSquare);
        Square testLocation = testResult.getLocation();
        assertEquals(testSquare, testLocation);
    }



}
