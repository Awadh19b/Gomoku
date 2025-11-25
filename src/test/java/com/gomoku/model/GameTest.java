package com.gomoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static final int BOARD_SIZE = 5; // Smaller board for simpler testing
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(BOARD_SIZE);
    }

    @Test
    void testInitialGameState() {
        assertEquals(Player.PLAYER_X, game.getCurrentPlayer(), "Player X should start first");
        assertFalse(game.isGameOver(), "Game should not be over initially");
        assertNull(game.getWinner(), "There should be no winner initially");
    }

    @Test
    void testPlayerMoves() {
        // Player X moves
        assertTrue(game.makeMove(1, 1), "Valid move should succeed");
        assertEquals(Player.PLAYER_O, game.getCurrentPlayer(), "Should switch to Player O");
        
        // Player O moves
        assertTrue(game.makeMove(1, 2), "Valid move should succeed");
        assertEquals(Player.PLAYER_X, game.getCurrentPlayer(), "Should switch back to Player X");
        
        // Invalid move on occupied cell
        assertFalse(game.makeMove(1, 1), "Should not allow move on occupied cell");
    }

    @Test
    void testWinCondition() {
        // Player X wins horizontally
        for (int i = 1; i <= 5; i++) {
            game.makeMove(1, i);
            if (i < 5) {
                game.makeMove(2, i); // Player O's moves (won't win)
            }
        }
        
        assertTrue(game.isGameOver(), "Game should be over after a win");
        assertEquals(Player.PLAYER_X, game.getWinner(), "Player X should be the winner");
        
        // No more moves allowed after game over
        assertFalse(game.makeMove(3, 1), "Should not allow moves after game over");
    }

    @Test
    void testAIMoves() {
        // Player X makes first move
        assertTrue(game.makeMove(1, 1), "Player X's move should be valid");
        
        // AI (Player O) makes a move
        game.makeAIMove();
        
        // Verify AI made a valid move
        boolean aiMoved = false;
        Board board = game.getBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getCell(i, j) == Player.PLAYER_O) {
                    aiMoved = true;
                    break;
                }
            }
            if (aiMoved) break;
        }
        
        assertTrue(aiMoved, "AI should have made a move");
        assertEquals(Player.PLAYER_X, game.getCurrentPlayer(), "Should be Player X's turn after AI move");
    }

    @Test
    void testGameDraw() {
        // Fill the board without any player winning
        for (int i = 1; i <= BOARD_SIZE; i++) {
            for (int j = 1; j <= BOARD_SIZE; j++) {
                // Alternate between X and O without creating a win
                if ((i + j) % 2 == 0) {
                    game.makeMove(i, j);
                } else {
                    game.makeMove(i, j);
                    game.makeMove((i % BOARD_SIZE) + 1, (j % BOARD_SIZE) + 1);
                }
            }
        }
        
        assertTrue(game.isGameOver(), "Game should be over when board is full");
        assertNull(game.getWinner(), "Should be a draw with no winner");
    }
}
