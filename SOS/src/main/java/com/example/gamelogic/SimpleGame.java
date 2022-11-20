package com.example.gamelogic;

import java.util.ArrayList;
import java.util.List;

public class SimpleGame extends Game{

    public SimpleGame(int gameSize, PlayerType player1Type, PlayerType player2Type) {
        super(GameMode.SIMPLE, gameSize, player1Type, player2Type);
    }
    @Override
    public List<Coordinate> checkForSOS(int row, int column) {
        List<Coordinate> sosList = super.checkForSOS(row, column);

        if (!sosList.isEmpty()) {
            super.setGameFinished(true);
            if (getPlayerTurn() == PlayerTurn.PLAYER1) {
                winner = Winner.PLAYER1;
            }
            else if (getPlayerTurn() == PlayerTurn.PLAYER2) {
                winner = Winner.PLAYER2;
            }
        }
        if (board.isFull()) {
            setGameFinished(true);
            winner = Winner.DRAW;
        }
        return sosList;
    }
    @Override
    public Coordinate computerMove() {
        Coordinate c = null;
        Game testGame = (Game) this;
        System.out.println("Computer makin' a move!");
        for (int i = 0; i < board.getGameGrid().size(); i++) {
            for (int j = 0; j < board.getGameGrid().get(0).size(); j++) {
                if (board.getCellByIndex(i, j).getStatus() == cellStatus.EMPTY) {
                    testGame.board.getCellByIndex(i, j).setStatus(cellStatus.S);
                    if (!testGame.checkForSOS(i, j).isEmpty()) {
                        System.out.println("Computer has spotted SOS");
                        board.getCellByIndex(i, j).setStatus(cellStatus.S);
                        return new Coordinate(i, j);
                    }
                    testGame.board.getCellByIndex(i, j).setStatus(cellStatus.O);
                    if (!testGame.checkForSOS(i, j).isEmpty()) {
                        System.out.println("Computer has spotted SOS");
                        board.getCellByIndex(i, j).setStatus(cellStatus.O);
                        return new Coordinate(i, j);
                    }
                    testGame.board.getCellByIndex(i, j).setStatus(cellStatus.EMPTY);
                }
            }
        }
        if (c == null) {
            for (int i = 0; i < board.getGameGrid().size(); i++) {
                for (int j = 0; j < board.getGameGrid().size(); j++) {
                    if (board.getCellByIndex(i, j).getStatus() == cellStatus.EMPTY) {
                        if ((i + 2) <= board.getGameGrid().size() - 1) {
                            if (board.getCellByIndex(i + 1, j).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i + 2, j).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }

                        if (((i + 2) <= board.getGameGrid().size() - 1) && ((j + 2) <= board.getGameGrid().size() - 1)) {
                            if (board.getCellByIndex(i + 1, j + 1).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i + 2, j + 2).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }

                        if ((j + 2) <= board.getGameGrid().size() - 1) {
                            if (board.getCellByIndex(i, j + 1).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i, j + 2).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }

                        if (((i - 2) >= 0) && ((j + 2) <= board.getGameGrid().size() - 1)) {
                           if (board.getCellByIndex(i - 1, j + 1).getStatus() == cellStatus.O) {
                               if (board.getCellByIndex(i - 2, j + 2).getStatus() == cellStatus.EMPTY) {
                                   continue;
                               }
                           }
                        }

                        if ((i - 2) >= 0) {
                            if (board.getCellByIndex(i - 1, j).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i - 2, j).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }

                        if (((i - 2) >= 0) && ((j - 2) >= 0)) {
                            if (board.getCellByIndex(i - 1, j - 1).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i - 2, j - 2).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }

                        if ((j - 2) >= 0) {
                            if (board.getCellByIndex(i, j - 1).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i, j - 2).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }
                        if (((i + 2) <= board.getGameGrid().size() - 1) && ((j - 2) >= 0)) {
                            if (board.getCellByIndex(i + 1, j - 1).getStatus() == cellStatus.O) {
                                if (board.getCellByIndex(i + 2, j - 2).getStatus() == cellStatus.EMPTY) {
                                    continue;
                                }
                            }
                        }
                        board.getCellByIndex(i, j).setStatus(cellStatus.S);
                        return new Coordinate(i, j);
                    }
                }
            }
        }

        if (c == null) {
            for (int i = 0; i < board.getGameGrid().size(); i++) {
                for (int j = 0; j < board.getGameGrid().size(); j++) {
                    if (board.getCellByIndex(i, j).getStatus() == cellStatus.EMPTY) {
                        if ((i + 1) <= board.getGameGrid().size() - 1) {
                            if ((i - 1) >= 0) {
                                if (board.getCellByIndex(i + 1, j).getStatus() == cellStatus.S) {
                                    if (board.getCellByIndex(i - 1, j).getStatus() == cellStatus.S) {
                                        continue;
                                    }
                                }
                            }
                        }

                        if (((i + 1) <= board.getGameGrid().size() - 1) && ((j + 1) <= board.getGameGrid().size() - 1)) {
                            if (((i - 1) >= 0) && ((j - 1) >= 0)) {
                                if (board.getCellByIndex(i + 1, j + 1).getStatus() == cellStatus.S) {
                                    if (board.getCellByIndex(i - 1, j - 1).getStatus() == cellStatus.S) {
                                        continue;
                                    }
                                }
                            }
                        }

                        if ((j + 1) <= board.getGameGrid().size() - 1) {
                            if ((j - 1) >= 0) {
                                if (board.getCellByIndex(i, j + 1).getStatus() == cellStatus.S) {
                                    if (board.getCellByIndex(i, j - 1).getStatus() == cellStatus.S) {
                                        continue;
                                    }
                                }
                            }
                        }

                        if (((i + 1) <= board.getGameGrid().size() - 1) && ((j - 1) >= 0)) {
                            if (((i - 1) >= 0) && ((j + 1) <= board.getGameGrid().size() - 1)) {
                                if (board.getCellByIndex(i + 1, j - 1).getStatus() == cellStatus.S) {
                                    if (board.getCellByIndex(i - 1, j + 1).getStatus() == cellStatus.S) {
                                        continue;
                                    }
                                }
                            }
                        }
                        board.getCellByIndex(i, j).setStatus(cellStatus.O);
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        if (c == null) {
            for (int i = 0; i < board.getGameGrid().size(); i++) {
                for (int j = 0; j < board.getGameGrid().get(0).size(); j++) {
                    if (board.getCellByIndex(i, j).getStatus() == cellStatus.EMPTY) {
                        board.getCellByIndex(i, j).setStatus(cellStatus.S);
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        if (c != null) {
            board.getCellByIndex(c.getX(), c.getY()).setStatus(cellStatus.S);
        }
        else {
            return new Coordinate(-1, -1);
        }
        return c;
    }
}
