package com.example.gamelogic;

public class Game {
    private GameMode gameMode;
    private int gameSize;
    private boolean playerTurn; // 0 -> player 1's turn; 1 -> player 2's turn
    private boolean gameFinished; // 0 -> game in progress; 1 -> game finished
    public GameBoard board;
    public Game(GameMode gameMode, int gameSize) {
        this.gameMode = gameMode;
        this.gameSize = gameSize;
        playerTurn = false;
        gameFinished = false;
        board = new GameBoard(gameSize);
    }
}
