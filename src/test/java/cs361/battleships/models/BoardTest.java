package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInvalidPlacement() {
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true, false));
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true, false));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true, false);
        Result result = board.attack(2, 'E');
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true, false);
        minesweeper = board.getShips().get(0);
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.SURRENDER, result.getResult());
        assertEquals(minesweeper, result.getShip());
    }

    @Test
    public void testAttackSameSquareMultipleTimes() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true, false);
        board.attack(1, 'A');
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.INVALID, result.getResult());
    }

    @Test
    public void testAttackSameEmptySquareMultipleTimes() {
        Result initialResult = board.attack(1, 'A');
        assertEquals(AtackStatus.MISS, initialResult.getResult());
        Result result = board.attack(1, 'A');
        assertEquals(AtackStatus.MISS, result.getResult());
    }

    @Test
    public void testSurrender() {
        board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true, false);
        board.attack(2, 'A');
        var result = board.attack(1, 'A');
        assertEquals(AtackStatus.SURRENDER, result.getResult());
    }

    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true, false));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 5, 'D', true, false));

    }

    @Test
    public void testCantPlaceMoreThan4Ships() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true, false));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 3, 'A', true, false));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 1, 'B', false, false));
        assertTrue(board.placeShip(new Ship("SUBMARINE"), 8, 'D', false, false));
        assertFalse(board.placeShip(new Ship(""), 8, 'A', false, false));

    }
}
