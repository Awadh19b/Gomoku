package com.gomoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static final int BOARD_SIZE = 5; // Smaller board for simpler testing
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BOARD_SIZE);
    }

    @Test
    void testBoardInitialization() {
        assertEquals(BOARD_SIZE, board.getSize(), "Board should be created with correct size");
        assertFalse(board.isFull(), "New board should not be full");
    }

    @Test
    void testMakeAndValidateMoves() {
        // Test valid move
        assertTrue(board.makeMove(0, 0, Player.PLAYER_X), "Valid move should succeed");
        assertEquals(Player.PLAYER_X, board.getCell(0, 0), "Cell should contain player's mark");
        
        // Test invalid move on occupied cell
        assertFalse(board.makeMove(0, 0, Player.PLAYER_O), "Should not allow move on occupied cell");
        
        // Test invalid move out of bounds
        assertThrows(IndexOutOfBoundsException.class, 
            () -> board.makeMove(-1, 0, Player.PLAYER_X),
            "Should throw for invalid row");
    }

    @Test
    void testWinConditions() {
        // Test horizontal win
        for (int i = 0; i < 5; i++) {
            board.makeMove(0, i, Player.PLAYER_X);
        }
        assertTrue(board.hasWon(Player.PLAYER_X), "Player X should win horizontally");
        
        // Reset for next test
        board = new Board(BOARD_SIZE);
        
        // Test vertical win
        for (int i = 0; i < 5; i++) {
            board.makeMove(i, 0, Player.PLAYER_O);
        }
        assertTrue(board.hasWon(Player.PLAYER_O), "Player O should win vertically");
    }

    @Test
    void testBoardFull() {
        // Fill the board without creating a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Player player = (i + j) % 2 == 0 ? Player.PLAYER_X : Player.PLAYER_O;
                board.makeMove(i, j, player);
            }
        }
        assertTrue(board.isFull(), "Board should be full when all cells are occupied");
        assertFalse(board.hasWon(Player.PLAYER_X), "No one should win in a draw");
    }
}
