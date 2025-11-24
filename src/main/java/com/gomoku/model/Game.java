package com.gomoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a Go-Moku game.
 * 
 * @author Awadh Baodhah
 */
public class Game {
    private final Board board;
    private Player currentPlayer;
    private boolean gameOver;
    private Player winner;
    private final Random random;

    /**
     * Creates a new game with default 8x8 board.
     */
    public Game() {
        this(8);
    }

    /**
     * Creates a new game with specified board size.
     * 
     * @param size The size of the board (size x size).
     */
    public Game(int size) {
        this.board = new Board(size);
        this.currentPlayer = Player.PLAYER_X;  // Human starts first
        this.gameOver = false;
        this.winner = null;
        this.random = new Random();
    }

    /**
     * Makes a move for the current player.
     * 
     * @param row The row index (1-based).
     * @param col The column index (1-based).
     * @return true if the move was successful, false otherwise.
     */
    public boolean makeMove(int row, int col) {
        if (gameOver || !board.isValidMove(row - 1, col - 1)) {
            return false;
        }

        board.makeMove(row - 1, col - 1, currentPlayer);

        if (board.hasWon(currentPlayer)) {
            gameOver = true;
            winner = currentPlayer;
            return true;
        }

        if (board.isFull()) {
            gameOver = true;
            return true;
        }

        currentPlayer = currentPlayer.next();
        return true;
    }

    /**
     * Makes a random move for the AI player.
     */
    public void makeAIMove() {
        if (gameOver || currentPlayer != Player.PLAYER_O) {
            return;
        }

        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isValidMove(i, j)) {
                    availableMoves.add(new int[]{i + 1, j + 1});
                }
            }
        }

        if (!availableMoves.isEmpty()) {
            int[] move = availableMoves.get(random.nextInt(availableMoves.size()));
            makeMove(move[0], move[1]);
        }
    }

    /**
     * Gets the current state of the board.
     * 
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current player.
     * 
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the winner of the game.
     * 
     * @return The winner, or null if the game is a draw or not over.
     */
    public Player getWinner() {
        return winner;
    }
}
