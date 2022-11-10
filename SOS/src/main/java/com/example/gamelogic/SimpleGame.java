package com.example.gamelogic;

import java.util.List;

public class SimpleGame extends Game{

    public SimpleGame(int gameSize, PlayerType player1Type, PlayerType player2Type) {
        super(GameMode.SIMPLE, gameSize, player1Type, player2Type);
    }
    @Override
    public List<Coordinate> checkForSOS(int row, int column) {
        List<Coordinate> sosList = super.checkForSOS(row, column);
        if (sosList.isEmpty()) {
            super.setGameFinished(false);
        }
        else {
            super.setGameFinished(true);
            if (getPlayerTurn() == PlayerTurn.PLAYER1) {
                winner = Winner.PLAYER2;
            }
            else if (getPlayerTurn() == PlayerTurn.PLAYER2) {
                winner = Winner.PLAYER1;
            }
        }
        if (board.isFull()) {
            setGameFinished(true);
            winner = Winner.DRAW;
        }
        return sosList;
    }
    @Override
    public void computerMove() {

    }
}
