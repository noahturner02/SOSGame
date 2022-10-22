package com.example.gamelogic;

public class Game {
    private String gameMode;
    private int gameSize;
    private boolean playerTurn; // 0 -> player 1's turn; 1 -> player 2's turn
    private boolean gameFinished; // 0 -> game in progress; 1 -> game finished
    private GameBoard board;
    Game(String gameMode, int gameSize) {
        this.gameMode = gameMode;
        this.gameSize = gameSize;
        playerTurn = false;
        gameFinished = false;
        board = new GameBoard(gameSize);
    }
}
