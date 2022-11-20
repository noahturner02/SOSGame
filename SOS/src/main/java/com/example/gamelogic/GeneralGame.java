package com.example.gamelogic;

import java.util.ArrayList;
import java.util.List;

public class GeneralGame extends Game{
    public int player1Points = 0;
    public int player2Points = 0;
    public GeneralGame(int gameSize, PlayerType player1Type, PlayerType player2Type) {
        super(GameMode.GENERAL, gameSize, player1Type, player2Type);
    }
    @Override
    public List<Coordinate> checkForSOS(int row, int column) {
        List<Coordinate> sosList = super.checkForSOS(row, column);
        if (!sosList.isEmpty()) {
            if (getPlayerTurn() == PlayerTurn.PLAYER1) {
                player2Points += sosList.size() / 3;
            } else if (getPlayerTurn() == PlayerTurn.PLAYER2) {
                player1Points += sosList.size() / 3;
            }
            System.out.println("Player 1: " + player1Points + "\nPlayer 2: " + player2Points);
        }
        if (super.board.isFull()) {
            super.setGameFinished(true);
            if (player1Points > player2Points) {
                winner = Winner.PLAYER1;
            }
            else if (player2Points > player1Points) {
                winner = Winner.PLAYER2;
            }
            else {
                winner = Winner.DRAW;
            }
        }
        else {
            super.setGameFinished(false);
        }
        return sosList;
    }
    @Override
    public Coordinate computerMove() throws NullPointerException{
        return new Coordinate(-1, -1);
    }
}
