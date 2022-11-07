package com.example.gamelogic;

import java.util.List;

public class SimpleGame extends Game{

    public SimpleGame(int gameSize) {
        super(GameMode.SIMPLE, gameSize);
    }
    @Override
    public List<Coordinate> checkForSOS(int row, int column) {
        List<Coordinate> sosList = super.checkForSOS(row, column);
        if (sosList.isEmpty()) {
            super.setGameFinished(false);
        }
        else {
            super.setGameFinished(true);
        }
        return sosList;
    }
}
