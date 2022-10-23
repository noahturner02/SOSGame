package com.example.gamelogic;

public class Game {
    private GameMode gameMode;
    private int gameSize;
    private PlayerTurn playerTurn;
    private boolean gameFinished; // 0 -> game in progress; 1 -> game finished
    private SelectedPiece player1PieceSelected = SelectedPiece.S;
    private SelectedPiece player2PieceSelected = SelectedPiece.S;
    public GameBoard board;
    public Game(GameMode gameMode, int gameSize) {
        this.gameMode = gameMode;
        this.gameSize = gameSize;
        playerTurn = PlayerTurn.PLAYER1;
        gameFinished = false;
        board = new GameBoard(gameSize);
    }
    public void setPlayerTurn(PlayerTurn playerTurn) {
        this.playerTurn = playerTurn;
    }
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
    public void setPlayer1PieceSelected(SelectedPiece s) {
        player1PieceSelected = s;
    }
    public void setPlayer2PieceSelected(SelectedPiece s) {
        player2PieceSelected = s;
    }
    public SelectedPiece getPlayer1PieceSelected() {
        return player1PieceSelected;
    }
    public SelectedPiece getPlayer2PieceSelected() {
        return player2PieceSelected;
    }
    public PlayerTurn getPlayerTurn() {
        return playerTurn;
    }
}
