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
    public Winner winner = null;
    public PlayerType player1Type;
    public PlayerType player2Type;
    public cellStatus computerSelectedPiece;
    public boolean recordGame = false;
    private List<MoveRecord> moveRecordList = new ArrayList<>();

    public Game(GameMode gameMode, int gameSize, PlayerType player1Type, PlayerType player2Type, boolean recordGame) {
        this.gameMode = gameMode;
        this.gameSize = gameSize;
        this.player1Type = player1Type;
        this.player2Type = player2Type;
        this.recordGame = recordGame;
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
    public boolean getGameFinished() { return gameFinished; }
    public boolean getRecordGame() { return recordGame; }
    public void setRecordGame(boolean recordGame) { this.recordGame = recordGame; }
    public void setMoveRecordList(List<MoveRecord> moveRecordList) {
        this.moveRecordList = moveRecordList;
    }
    public List<MoveRecord> getMoveRecordList() { return moveRecordList; }
    public void addMoveRecord(MoveRecord moveRecord) {
        moveRecordList.add(moveRecord);
    }
    abstract public Coordinate computerMove();
    public List<Coordinate> checkForSOS(int row, int column) { // row and column of last placed item
        List<Coordinate> sosCells = new ArrayList<>();
        if (board.getCellByIndex(row, column).getStatus() == cellStatus.S) {
            // Find which directions that an SOS can extend from
            if ((row + 2) <= board.getGameGrid().size() - 1) { // there are at least two spaces above the S
                if (board.getCellByIndex(row + 1, column).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row + 2, column).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row + 1, column));
                        sosCells.add(new Coordinate(row + 2, column));
                        System.out.println("SOS North of S");
                    }
                }
            }
            if (((row + 2) <= board.getGameGrid().size() - 1) && ((column + 2) <= board.getGameGrid().size() - 1)) { // There are two spaces NE of the S
                if (board.getCellByIndex(row + 1, column + 1).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row + 2, column + 2).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row + 1, column + 1));
                        sosCells.add(new Coordinate(row + 2, column + 2));
                        System.out.println("SOS NE of S");
                    }
                }
            }
            if ((column + 2) <= board.getGameGrid().size() - 1) { // There are two spaces E of the S
                if (board.getCellByIndex(row, column + 1).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row, column + 2).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row, column + 1));
                        sosCells.add(new Coordinate(row, column + 2));
                        System.out.println("SOS E of S");
                    }
                }
            }
            if (((row - 2) >= 0) && ((column + 2) <= board.getGameGrid().size() - 1)) { // There are two spaces SE of the S
                if (board.getCellByIndex(row -1, column + 1).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row -2, column + 2).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row - 1, column + 1));
                        sosCells.add(new Coordinate(row - 2, column + 2));
                        System.out.println("SOS SE of S");
                    }
                }
            }
            if ((row - 2) >= 0) { // There are two spaces S of the S
                if (board.getCellByIndex(row -1, column).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row -2, column).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row - 1, column));
                        sosCells.add(new Coordinate(row - 2, column));
                        System.out.println("SOS South of the S");
                    }
                }
            }
            if (((row - 2) >= 0) && ((column - 2) >= 0)) {
                if (board.getCellByIndex(row -1, column -1).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row-2, column -2).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row - 1, column - 1));
                        sosCells.add(new Coordinate(row - 2, column - 2));
                        System.out.println("SOS SW of the S");
                    }
                }
            }
            if ((column - 2) >= 0) {
                if (board.getCellByIndex(row, column -1).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row, column-2).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row, column - 1));
                        sosCells.add(new Coordinate(row, column - 2));
                        System.out.println("SOS W of the S");
                    }
                }
            }
            if (((row + 2) <= board.getGameGrid().size() - 1) && ((column - 2) >= 0)) {
                if (board.getCellByIndex(row + 1, column - 1).getStatus() == cellStatus.O) {
                    if (board.getCellByIndex(row +2, column -2).getStatus() == cellStatus.S) {
                        sosCells.add(new Coordinate(row, column));
                        sosCells.add(new Coordinate(row + 1, column - 1));
                        sosCells.add(new Coordinate(row + 2, column - 2));
                        System.out.println("SOS NW of S");
                    }
                }
            }
        } else if (board.getCellByIndex(row, column).getStatus() == cellStatus.O) {
            if ((row + 1) <= board.getGameGrid().size() - 1) {
                if ((row - 1) >= 0) { // There are squares above and below the O
                    if (board.getCellByIndex(row + 1, column).getStatus() == cellStatus.S) {
                        if (board.getCellByIndex(row -1, column).getStatus() == cellStatus.S) {
                            sosCells.add(new Coordinate(row + 1, column));
                            sosCells.add(new Coordinate(row, column));
                            sosCells.add(new Coordinate(row - 1, column));
                            System.out.println("Vertical SOS with O");
                        }
                    }
                }
            }
            if (((row + 1) <= board.getGameGrid().size() - 1) && ((column + 1) <= board.getGameGrid().size() - 1)) {
                if (((row - 1) >= 0) && ((column - 1) >= 0)) { // There are squares in the \ diagonal
                    if (board.getCellByIndex(row + 1, column + 1).getStatus() == cellStatus.S) {
                        if (board.getCellByIndex(row - 1, column - 1).getStatus() == cellStatus.S) {
                            sosCells.add(new Coordinate(row + 1, column + 1));
                            sosCells.add(new Coordinate(row, column));
                            sosCells.add(new Coordinate(row - 1, column - 1));
                            System.out.println("\\ SOS with O");
                        }
                    }
                }
            }
            if ((column + 1) <= board.getGameGrid().size() - 1) {
                if ((column - 1) >= 0) { // There are squares left and right of the O
                    if (board.getCellByIndex(row, column + 1).getStatus() == cellStatus.S) {
                        if (board.getCellByIndex(row, column - 1).getStatus() == cellStatus.S) {
                            sosCells.add(new Coordinate(row, column + 1));
                            sosCells.add(new Coordinate(row, column));
                            sosCells.add(new Coordinate(row, column - 1));
                            System.out.println("Horizontal SOS with O");
                        }
                    }
                }
            }
            if (((row + 1) <= board.getGameGrid().size() - 1) && ((column - 1) >= 0)) {
                if (((row - 1) >= 0) && ((column + 1) <= board.getGameGrid().size() - 1)) { // There are squares in the / diagonal
                    if (board.getCellByIndex(row + 1, column - 1).getStatus() == cellStatus.S) {
                        if (board.getCellByIndex(row - 1, column + 1).getStatus() == cellStatus.S) {
                            sosCells.add(new Coordinate(row + 1, column - 1));
                            sosCells.add(new Coordinate(row, column));
                            sosCells.add(new Coordinate(row - 1, column + 1));
                            System.out.println("/ SOS with O");
                        }
                    }
                }
            }

        }

        return sosCells;
    }
}