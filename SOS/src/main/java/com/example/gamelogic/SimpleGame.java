package com.example.gamelogic;

import java.util.List;

public class SimpleGame extends Game{

    public SimpleGame(int gameSize) {
        super(GameMode.SIMPLE, gameSize);
    }
    @Override
    public List<Cell> checkForSOS(int row, int column) {
        List<Cell> sosList = super.checkForSOS(row, column);
        if (sosList.isEmpty()) {
            super.setGameFinished(false);
        }
        else {
            super.setGameFinished(true);
        }
        return sosList;
    }
}
