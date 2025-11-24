package com.gomoku.model;

import java.util.Arrays;

/**
 * Represents the game board for Go-Moku.
 * 
 * @author Awadh Baodhah
 */
public class Board {
    private static final int DEFAULT_SIZE = 8;
    private final int size;
    private final Player[][] grid;
    
    /**
     * Creates a new board with the default size (8x8).
     */
    public Board() {
        this(DEFAULT_SIZE);
    }
    
    /**
     * Creates a new board with the specified size.
     *
     * @param size The size of the board (size x size).
     * @throws IllegalArgumentException if size is less than 5.
     */
    public Board(int size) {
        if (size < 5) {
            throw new IllegalArgumentException("Board size must be at least 5x5");
        }
        this.size = size;
        this.grid = new Player[size][size];
        initializeBoard();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], Player.NONE);
        }
    }
    
    /**
     * Gets the size of the board.
     *
     * @return The size of the board (number of rows/columns).
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets the player at the specified position.
     *
     * @param row The row index (0-based).
     * @param col The column index (0-based).
     * @return The player at the specified position, or Player.NONE if empty.
     * @throws IndexOutOfBoundsException if row or col is out of bounds.
     */
    public Player getCell(int row, int col) {
        checkBounds(row, col);
        return grid[row][col];
    }
    
    /**
     * Places a player's piece on the board.
     *
     * @param row The row index (0-based).
     * @param col The column index (0-based).
     * @param player The player making the move.
     * @return true if the move was successful, false if the cell is already occupied.
     * @throws IndexOutOfBoundsException if row or col is out of bounds.
     * @throws IllegalArgumentException if player is null or Player.NONE.
     */
    public boolean makeMove(int row, int col, Player player) {
        checkBounds(row, col);
        if (player == null || player == Player.NONE) {
            throw new IllegalArgumentException("Invalid player");
        }
        if (grid[row][col] != Player.NONE) {
            return false;
        }
        grid[row][col] = player;
        return true;
    }
    
    /**
     * Checks if the specified move is valid.
     *
     * @param row The row index (0-based).
     * @param col The column index (0-based).
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int row, int col) {
        try {
            return grid[row][col] == Player.NONE;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    /**
     * Checks if the board is full.
     *
     * @return true if the board is full, false otherwise.
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == Player.NONE) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Checks if the specified player has won the game.
     *
     * @param player The player to check for a win.
     * @return true if the player has won, false otherwise.
     */
    public boolean hasWon(Player player) {
        // Check all possible directions for 5 in a row
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == player) {
                    if (checkDirection(i, j, 1, 0, player) ||  // Horizontal
                        checkDirection(i, j, 0, 1, player) ||  // Vertical
                        checkDirection(i, j, 1, 1, player) ||  // Diagonal \
                        checkDirection(i, j, 1, -1, player)) { // Diagonal /
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean checkDirection(int row, int col, int rowDir, int colDir, Player player) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r < 0 || r >= size || c < 0 || c >= size || grid[r][c] != player) {
                return false;
            }
            count++;
            if (count == 5) {
                return true;
            }
        }
        return false;
    }
    
    private void checkBounds(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Position (" + row + ", " + col + ") is out of bounds");
        }
    }
}
