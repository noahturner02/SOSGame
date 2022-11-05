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
    public void checkForSOS(int row, int column) { // row and column of last placed item
        if (getCellByIndex(row, column).getStatus() == cellStatus.S) {
            // Find which directions that an SOS can extend from
            if ((row + 2) <= gameGrid.size()) { // there are at least two spaces above the S
                if (getCellByIndex(row + 1, column).getStatus() == cellStatus.O) {
                    if (getCellByIndex((row + 2), column).getStatus() == cellStatus.S) {
                        System.out.println("SOS North of S");
                    }
                }
            }
            if (((row + 2) <= gameGrid.size()) && ((column + 2) <= gameGrid.size())) { // There are two spaces NE of the S
                if (getCellByIndex(row + 1, column + 1).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row + 2, column + 2).getStatus() == cellStatus.S) {
                        System.out.println("SOS NE of S");
                    }
                }
            }
            if ((column + 2) <= gameGrid.size()) { // There are two spaces E of the S
                if (getCellByIndex(row, column + 1).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row, column + 2).getStatus() == cellStatus.S) {
                        System.out.println("SOS E of S");
                    }
                }
            }
            if (((row - 2) >= 0) && ((column + 2) <= gameGrid.size())) { // There are two spaces SE of the S
                if (getCellByIndex(row -1, column + 1).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row -2, column + 2).getStatus() == cellStatus.S) {
                        System.out.println("SOS SE of S");
                    }
                }
            }
            if ((row - 2) >= 0) { // There are two spaces S of the S
                if (getCellByIndex(row -1, column).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row -2, column).getStatus() == cellStatus.S) {
                        System.out.println("SOS South of the S");
                    }
                }
            }
            if (((row - 2) >= 0) && ((column - 2) >= 0)) {
                if (getCellByIndex(row -1, column -1).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row-2, column -2).getStatus() == cellStatus.S) {
                        System.out.println("SOS SW of the S");
                    }
                }
            }
            if ((column - 2) >= 0) {
                if (getCellByIndex(row, column -1).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row, column-2).getStatus() == cellStatus.S) {
                        System.out.println("SOS W of the S");
                    }
                }
            }
            if (((row + 2) <= gameGrid.size()) && ((column - 2) >= 0)) {
                if (getCellByIndex(row + 1, column - 1).getStatus() == cellStatus.O) {
                    if (getCellByIndex(row +2, column -2).getStatus() == cellStatus.S) {
                        System.out.println("SOS NW of S");
                    }
                }
            }
        }
    }
}