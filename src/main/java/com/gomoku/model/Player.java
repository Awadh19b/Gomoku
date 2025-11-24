package com.gomoku.model;

/**
 * Represents the players in the Go-Moku game.
 * 
 * @author Awadh Baodhah
 */
public enum Player {
    /**
     * The first player, represented by 'X'.
     */
    PLAYER_X('X'),
    
    /**
     * The second player (AI in this case), represented by 'O'.
     */
    PLAYER_O('O'),
    
    /**
     * Represents an empty cell on the board.
     */
    NONE('.');
    
    private final char symbol;
    
    /**
     * Constructor for Player enum.
     *
     * @param symbol The character symbol representing the player on the board.
     */
    Player(char symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Gets the symbol of the player.
     *
     * @return The character symbol of the player.
     */
    public char getSymbol() {
        return symbol;
    }
    
    /**
     * Gets the next player in turn.
     *
     * @return The next player (switches between PLAYER_X and PLAYER_O).
     */
    public Player next() {
        return this == PLAYER_X ? PLAYER_O : PLAYER_X;
    }
}
