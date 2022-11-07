package com.example.gamelogic;

import java.util.List;

public class GeneralGame extends Game{
    public int player1Points = 0;
    public int player2Points = 0;
    public GeneralGame(int gameSize) {
        super(GameMode.GENERAL, gameSize);
    }
    @Override
    public List<Coordinate> checkForSOS(int row, int column) {
        List<Coordinate> sosList = super.checkForSOS(row, column);
        if (!sosList.isEmpty()) {
            if (getPlayerTurn() == PlayerTurn.PLAYER1) {
                player1Points += sosList.size() / 3;
            }
            else if (getPlayerTurn() == PlayerTurn.PLAYER2) {
                player2Points += sosList.size() / 3;
            }
            System.out.println("Player 1: " + player1Points + "\nPlayer 2: " + player2Points);
        }
        return sosList;
    }
}
