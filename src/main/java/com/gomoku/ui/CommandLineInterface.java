package com.gomoku.ui;

import com.gomoku.model.Board;
import com.gomoku.model.Game;
import com.gomoku.model.Player;

import java.util.Scanner;

/**
 * Command-line interface for the Go-Moku game.
 * 
 * @author Awadh Baodhah
 */
public class CommandLineInterface {
    private final Game game;
    private final Scanner scanner;

    public CommandLineInterface() {
        this.game = new Game(8);  // 8x8 board
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the game.
     */
    public void start() {
        System.out.println("Welcome to Go-Moku!");
        System.out.println("You are X, the AI is O");
        System.out.println("Enter moves as row,col (e.g., 4,5)");
        System.out.println("Type 'exit' to quit the game");
        
        while (!game.isGameOver()) {
            printBoard();
            
            if (game.getCurrentPlayer() == Player.PLAYER_X) {
                // Human's turn
                System.out.print("Your move (row,col): ");
                String input = "";
                try {
                    input = scanner.nextLine().trim().toLowerCase();
                    
                    if (input.equals("exit")) {
                        System.out.println("Game ended by user.");
                        return;
                    }
                    
                    if (!input.matches("\\d+,\\d+")) {
                        System.out.println("Invalid input. Please enter row,col (e.g., 4,5) or 'exit' to quit");
                        continue;
                    }
                    
                    String[] parts = input.split(",");
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    
                    if (!game.makeMove(row, col)) {
                        System.out.println("Invalid move. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Please try again.");
                    continue;
                }
            } else {
                // AI's turn
                System.out.println("AI is thinking...");
                try {
                    Thread.sleep(1000); // Add a small delay for better UX
                    game.makeAIMove();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Game was interrupted.");
                    return;
                }
            }
        }
        
        // Game over
        printBoard();
        Player winner = game.getWinner();
        if (winner == Player.PLAYER_X) {
            System.out.println("Congratulations! You won!");
        } else if (winner == Player.PLAYER_O) {
            System.out.println("AI wins! Better luck next time!");
        } else {
            System.out.println("It's a draw!");
        }
        
        scanner.close();
    }

    private void printBoard() {
        System.out.println();
        Board board = game.getBoard();
        int size = board.getSize();
        
        // Print column numbers
        System.out.print("  ");
        for (int col = 1; col <= size; col++) {
            System.out.print(" " + col);
        }
        System.out.println();
        
        // Print board with row numbers
        for (int row = 0; row < size; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < size; col++) {
                System.out.print(" " + board.getCell(row, col).getSymbol());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new CommandLineInterface().start();
    }
}
