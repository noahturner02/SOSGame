package com.example.gamelogic;

public class SimpleGame extends Game{

    public SimpleGame(int gameSize) {
        super(GameMode.SIMPLE, gameSize);
    }
    @Override
    public void checkForSOS(int row, int column) {
        super.checkForSOS(row, column);

    }
}
