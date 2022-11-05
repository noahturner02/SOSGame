package com.example.gamelogic;


import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private List<List<Cell>> gameGrid = new ArrayList<>();
    GameBoard(int size) {
        if ((size >= 3) && (size <= 10)) {
            gameGrid = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                List<Cell> innerList = new ArrayList();
                for (int j = 0; j < size; j++) {
                    innerList.add(new Cell(cellStatus.EMPTY));
                }
                gameGrid.add(innerList);
            }
        }
    }

    public void resizeBoard(int size) {
        if ((size >= 3) && (size <= 10)) {
            gameGrid = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                List<Cell> innerList = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    innerList.add(new Cell(cellStatus.EMPTY));
                }
                gameGrid.add(innerList);
            }
        }
    }

    public List<List<Cell>> getGameGrid() {
        return gameGrid;
    }
    public Cell getCellByIndex(int row, int column) {
        return gameGrid.get(row).get(column);
    }
    public void clickCell(int row, int column) {
        // fill in later
    }
}