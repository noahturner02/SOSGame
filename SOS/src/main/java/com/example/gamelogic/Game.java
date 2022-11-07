package com.example.gamelogic;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
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
    public void setGameMode(GameMode g) {
        gameMode = g;
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
    public GameMode getGameMode() {
        return gameMode;
    }
    public List<Cell> checkForSOS(int row, int column) { // row and column of last placed item
        List<Cell> sosCells = new ArrayList<>();
        if (board.getCellByIndex(row, column).getStatus() == cellStatus.S) {
            // Find which directions that an SOS can extend from
            sosCells.add(board.getCellByIndex(row, column));
            if ((row + 2) <= board.getGameGrid().size() - 1) { // there are at least two spaces above the S
                if (board.getCellByIndex(row + 1, column).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row + 1, column));
                    if (board.getCellByIndex(row + 2, column).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row + 2, column));
                        System.out.println("SOS North of S");
                        return sosCells;
                    }
                }
            }
            if (((row + 2) <= board.getGameGrid().size() - 1) && ((column + 2) <= board.getGameGrid().size() - 1)) { // There are two spaces NE of the S
                if (board.getCellByIndex(row + 1, column + 1).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row + 1, column + 1));
                    if (board.getCellByIndex(row + 2, column + 2).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row + 2, column + 2));
                        System.out.println("SOS NE of S");
                        return sosCells;
                    }
                }
            }
            if ((column + 2) <= board.getGameGrid().size() - 1) { // There are two spaces E of the S
                if (board.getCellByIndex(row, column + 1).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row, column + 1));
                    if (board.getCellByIndex(row, column + 2).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row, column + 2));
                        System.out.println("SOS E of S");
                        return sosCells;
                    }
                }
            }
            if (((row - 2) >= 0) && ((column + 2) <= board.getGameGrid().size() - 1)) { // There are two spaces SE of the S
                if (board.getCellByIndex(row -1, column + 1).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row - 1, column + 1));
                    if (board.getCellByIndex(row -2, column + 2).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row -2, column + 2));
                        System.out.println("SOS SE of S");
                        return sosCells;
                    }
                }
            }
            if ((row - 2) >= 0) { // There are two spaces S of the S
                if (board.getCellByIndex(row -1, column).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row - 1, column));
                    if (board.getCellByIndex(row -2, column).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row -2, column));
                        System.out.println("SOS South of the S");
                        return sosCells;
                    }
                }
            }
            if (((row - 2) >= 0) && ((column - 2) >= 0)) {
                if (board.getCellByIndex(row -1, column -1).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row -1, column -1));
                    if (board.getCellByIndex(row-2, column -2).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row -2, column -2));
                        System.out.println("SOS SW of the S");
                        return sosCells;
                    }
                }
            }
            if ((column - 2) >= 0) {
                if (board.getCellByIndex(row, column -1).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row, column -1));
                    if (board.getCellByIndex(row, column-2).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row, column -2));
                        System.out.println("SOS W of the S");
                        return sosCells;
                    }
                }
            }
            if (((row + 2) <= board.getGameGrid().size() - 1) && ((column - 2) >= 0)) {
                if (board.getCellByIndex(row + 1, column - 1).getStatus() == cellStatus.O) {
                    sosCells.add(board.getCellByIndex(row + 1, column - 1));
                    if (board.getCellByIndex(row +2, column -2).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row + 2, column -2));
                        System.out.println("SOS NW of S");
                        return sosCells;
                    }
                }
            }
        } else if (board.getCellByIndex(row, column).getStatus() == cellStatus.O) {
            if ((row + 1) <= board.getGameGrid().size() - 1) {
                if ((row - 1) >= 0) { // There are squares above and below the O
                    if (board.getCellByIndex(row + 1, column).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row + 1, column));
                        if (board.getCellByIndex(row -1, column).getStatus() == cellStatus.S) {
                            sosCells.add(board.getCellByIndex(row - 1, column));
                            System.out.println("Vertical SOS with O");
                            return sosCells;
                        }
                    }
                }
            }
            if (((row + 1) <= board.getGameGrid().size() - 1) && ((column + 1) <= board.getGameGrid().size() - 1)) {
                if (((row - 1) >= 0) && ((column - 1) >= 0)) { // There are squares in the \ diagonal
                    if (board.getCellByIndex(row + 1, column + 1).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row + 1, column + 1));
                        if (board.getCellByIndex(row - 1, column - 1).getStatus() == cellStatus.S) {
                            sosCells.add(board.getCellByIndex(row - 1, column - 1));
                            System.out.println("\\ SOS with O");
                            return sosCells;
                        }
                    }
                }
            }
            if ((column + 1) <= board.getGameGrid().size() - 1) {
                if ((column - 1) >= 0) { // There are squares left and right of the O
                    if (board.getCellByIndex(row, column + 1).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row, column + 1));
                        if (board.getCellByIndex(row, column - 1).getStatus() == cellStatus.S) {
                            sosCells.add(board.getCellByIndex(row, column - 1));
                            System.out.println("Horizontal SOS with O");
                            return sosCells;
                        }
                    }
                }
            }
            if (((row + 1) <= board.getGameGrid().size() - 1) && ((column - 1) >= 0)) {
                if (((row - 1) >= 0) && ((column + 1) <= board.getGameGrid().size() - 1)) { // There are squares in the / diagonal
                    if (board.getCellByIndex(row + 1, column - 1).getStatus() == cellStatus.S) {
                        sosCells.add(board.getCellByIndex(row + 1, column -1));
                        if (board.getCellByIndex(row - 1, column + 1).getStatus() == cellStatus.S) {
                            sosCells.add(board.getCellByIndex(row - 1, column + 1));
                            System.out.println("/ SOS with O");
                            return sosCells;
                        }
                    }
                }
            }

        }

        return sosCells;
    }
}