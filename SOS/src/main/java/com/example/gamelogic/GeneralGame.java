package com.example.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                player1Points += sosList.size() / 3;
            } else if (getPlayerTurn() == PlayerTurn.PLAYER2) {
                player2Points += sosList.size() / 3;
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
    public Coordinate computerMove() {
        Random rand = new Random();
        Coordinate c = null;
        List<Coordinate> potentialSMoves = new ArrayList<>();
        List<Coordinate> potentialOMoves = new ArrayList<>();
        List<Coordinate> selectedList = new ArrayList<>();
        int maxSOSSize = 0;
        System.out.println("Computer makin' a move!");
        for (int i = 0; i < board.getGameGrid().size(); i++) { // Look for the most SOS's at once
            for (int j = 0; j < board.getGameGrid().get(0).size(); j++) {
                if (board.getCellByIndex(i, j).getStatus() == cellStatus.EMPTY) {
                    board.getCellByIndex(i, j).setStatus(cellStatus.S);
                    if (super.checkForSOS(i, j).size() > maxSOSSize) {
                        System.out.println("Computer has spotted SOS");
                        c = new Coordinate(i, j);
                        maxSOSSize = super.checkForSOS(i, j).size();
                        computerSelectedPiece = cellStatus.S;
                    }
                    board.getCellByIndex(i, j).setStatus(cellStatus.O);
                    if (super.checkForSOS(i, j).size() > maxSOSSize) {
                        System.out.println("Computer has spotted SOS");
                        c = new Coordinate(i, j);
                        maxSOSSize = super.checkForSOS(i, j).size();
                        computerSelectedPiece = cellStatus.O;
                    }
                    board.getCellByIndex(i, j).setStatus(cellStatus.EMPTY);
                }
            }
        }
        if (c == null) { // Avoid setting up SOS by S
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
                        potentialSMoves.add(new Coordinate(i, j));
                    }
                }
            }
        }

        if (c == null) { // Avoid setting up an SOS by O
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
                        potentialOMoves.add(new Coordinate(i, j));
                    }
                }
            }
        }
        if ((c == null) && (potentialOMoves.isEmpty()) && (potentialSMoves.isEmpty())) { // Suicide. Select the first open square
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
            board.getCellByIndex(c.getX(), c.getY()).setStatus(computerSelectedPiece);
            if (computerSelectedPiece == cellStatus.S) {
                computerSelectedPiece = cellStatus.O;
            } else {
                computerSelectedPiece = cellStatus.S;
            }
        }
        else if (!potentialSMoves.isEmpty() && !potentialOMoves.isEmpty()) { // Possible S and O moves. Choose one
            cellStatus piece = cellStatus.S;
            if (rand.nextInt(2) == 0) {
                selectedList = potentialSMoves;
                piece = cellStatus.S;
            }
            else {
                selectedList = potentialOMoves;
                piece = cellStatus.O;
            }
            int index = rand.nextInt(selectedList.size());
            Coordinate selectedMove = selectedList.get(index);
            board.getCellByIndex(selectedMove.getX(), selectedMove.getY()).setStatus(piece);
            return selectedMove;
        }
        else if (!potentialSMoves.isEmpty() && potentialOMoves.isEmpty()) { // Only S moves are possible. Choose one
            int index = rand.nextInt(potentialSMoves.size());
            Coordinate selectedMove = potentialSMoves.get(index);
            board.getCellByIndex(selectedMove.getX(), selectedMove.getY()).setStatus(cellStatus.S);
            return selectedMove;
        }
        else if (!potentialOMoves.isEmpty() && potentialSMoves.isEmpty()) { // Only O moves are possible. Choose one
            int index = rand.nextInt(potentialOMoves.size());
            Coordinate selectedMove = potentialOMoves.get(index);
            board.getCellByIndex(selectedMove.getX(), selectedMove.getY()).setStatus(cellStatus.O);
            return selectedMove;
        }
        else {
            setGameFinished(true);
            if (player1Points > player2Points) {
                winner = Winner.PLAYER1;
            } else if (player2Points > player1Points) {
                winner = Winner.PLAYER2;
            }
            else {
                winner = Winner.DRAW;
            }
            return new Coordinate(-1, -1);
        }
        return c;
    }
}
