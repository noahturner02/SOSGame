package com.example.ui;

import com.example.gamelogic.*;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class CellController {

    @FXML
    CheckBox recordGameBox;
    @FXML
    Button replayButton;
    @FXML
    Group gridpaneWrapper;
    @FXML
    RadioButton simpleGame;
    @FXML
    RadioButton generalGame;
    @FXML
    Button newGameButton;
    @FXML
    Slider sizeSlider;
    @FXML
    GridPane gameBoard;
    @FXML
    HBox bottomHBox;
    @FXML
    StackPane player1Pane;
    @FXML
    StackPane player2Pane;
    @FXML
    RadioButton player1S;
    @FXML
    RadioButton player1O;
    @FXML
    RadioButton player2S;
    @FXML
    RadioButton player2O;
    @FXML
    RadioButton player1HumanButton;
    @FXML
    RadioButton player1ComputerButton;
    @FXML
    RadioButton player2HumanButton;
    @FXML
    RadioButton player2ComputerButton;

    static Game game = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN, false);

    private void handleComputerMove() {
        Coordinate c;
        if (game.getPlayerTurn() == PlayerTurn.PLAYER1) {
            if (game.player1Type == PlayerType.COMPUTER) {
                c = game.computerMove();
                if (c.getX() != -1) {
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(.5));
                    pauseTransition.setOnFinished(event -> {
                        onClickUI(game.checkForSOS(c.getX(), c.getY()), getChildFromGridPaneByRowAndColumn(c.getX(), c.getY()));
                        game.setPlayerTurn(PlayerTurn.PLAYER2);
                        if ((game.player2Type == PlayerType.COMPUTER) && (!game.getGameFinished())) {
                            handleComputerMove();
                        } else if (game.getGameFinished()) {
                            winDisplay();
                        }
                        }
                    );
                    pauseTransition.play();
                }
                else {
                    game.setPlayerTurn(PlayerTurn.PLAYER2);
                    if ((game.player2Type == PlayerType.COMPUTER) && (!game.getGameFinished())) {
                        handleComputerMove();
                    } else if (game.getGameFinished()) {
                        winDisplay();
                    }
                }
            }
        }
        else {
            if (game.player2Type == PlayerType.COMPUTER) {
                c = game.computerMove();
                if (c.getX() != -1) {
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(.5));
                    pauseTransition.setOnFinished(event -> {
                        onClickUI(game.checkForSOS(c.getX(), c.getY()), getChildFromGridPaneByRowAndColumn(c.getX(), c.getY()));
                        game.setPlayerTurn(PlayerTurn.PLAYER1);
                        if ((game.player1Type == PlayerType.COMPUTER) && (!game.getGameFinished())) {
                            handleComputerMove();
                        } else if (game.getGameFinished()) {
                            winDisplay();
                        }
                        }
                    );
                    pauseTransition.play();
                }
                else {
                    game.setPlayerTurn(PlayerTurn.PLAYER1);
                    if ((game.player1Type == PlayerType.COMPUTER) && (!game.getGameFinished())) {
                        handleComputerMove();
                    } else if (game.getGameFinished()) {
                        winDisplay();
                    }
                }
            }
        }
    }

    private void hideAllPieces() {
        ObservableList<Node> nodeList = gameBoard.getChildren();
        for (Node n : nodeList) { // Remove S's and O's from the board
            if (n instanceof StackPane) {
                StackPane s = (StackPane) n;
                s.getChildren().get(0).setVisible(false);
                s.getChildren().get(1).setVisible(false);
            }
        }
    }

    private void removeLines() {
        // removes lines from display
        ObservableList<Node> lineList = gridpaneWrapper.getChildren();
        while (lineList.size() > 1) {
            lineList.remove(1);
        }
    }

    private void replayGameFromTextFile() {
        // Handles replaying the game from the text file
        Game replayGame;
        if (game.getGameMode() == GameMode.GENERAL) {
            replayGame = new GeneralGame(game.board.getGameGrid().size(), game.player1Type, game.player2Type, true);
        }
        else if (game.getGameMode() == GameMode.SIMPLE) {
            replayGame = new SimpleGame(game.board.getGameGrid().size(), game.player1Type, game.player2Type, true);
        }

        hideAllPieces();
        removeLines();

        // Parse the line for coordinates and piece
        int row = 0;
        int column = 0;
        String piece = "Q";
        cellStatus cs = cellStatus.EMPTY
        try {
            BufferedReader br = new BufferedReader(new FileReader("savedGame.txt"));
            String s = br.readLine();
            String[] paramList = s.split(", ");
            row = Integer.parseInt(paramList[0]);
            column = Integer.parseInt(paramList[1]);
            piece = paramList[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Result: " + row + ", " + column + ", " + piece);

        if (piece == "S") { // Convert string to cellStatus
            cs = cellStatus.S;
        }
        else if (piece == "O") {
            cs = cellStatus.O;
        }

        

    }

    private void replayButtonEnableCheck() {
        if (game.getGameFinished() && game.getRecordGame()) {
            replayButton.setDisable(false);
        } else {
            replayButton.setDisable(true);
        }
    }

    private void onClickUI(List<Coordinate> SOSList, StackPane cell) {
        ObservableList<Node> childrenList = cell.getChildren();
        if (SOSList.size() >= 3) {
            while (SOSList.size() >= 3) {
                StackPane startCell = getChildFromGridPaneByRowAndColumn(SOSList.get(0).getX(), SOSList.get(0).getY());
                StackPane endCell = getChildFromGridPaneByRowAndColumn(SOSList.get(2).getX(), SOSList.get(2).getY());
                double startX = startCell.getLayoutX() + (startCell.getWidth() / 2);
                double startY = startCell.getLayoutY() + (startCell.getHeight() / 2);
                double endX = endCell.getLayoutX() + (startCell.getWidth() / 2);
                double endY = endCell.getLayoutY() + (startCell.getHeight() / 2);
                Line line = new Line(startX, startY, endX, endY);
                line.setStrokeWidth(5);
                if (game.getPlayerTurn() == PlayerTurn.PLAYER2) {
                    line.setStyle("-fx-stroke: red");
                }
                else if (game.getPlayerTurn() == PlayerTurn.PLAYER1) {
                    line.setStyle("-fx-stroke: blue");
                }
                gridpaneWrapper.getChildren().add(line);
                SOSList.remove(0);
                SOSList.remove(0);
                SOSList.remove(0);
                System.out.println("Line created: " + startX + " " + startY + " " + endX + " " + endY);
            }
        }

        switch (game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).getStatus()) {
            case S:
                childrenList.get(0).setVisible(true);
                break;
            case O:
                childrenList.get(1).setVisible(true);
                break;
        }
        if (game.getPlayerTurn() == PlayerTurn.PLAYER1) {
            player2Pane.setStyle("-fx-background-color: #CC4B23");
            player1Pane.setStyle("-fx-background-color: #FFFFFF");
        } else if (game.getPlayerTurn() == PlayerTurn.PLAYER2) {
            player2Pane.setStyle("-fx-background-color: #FFFFFF");
            player1Pane.setStyle("-fx-background-color: #6D9DD5");
        }
        replayButtonEnableCheck();
    }

    private void onClickData(StackPane cell) {
        // Processing click in game logic
        List<Coordinate> SOSList = new ArrayList<>();
        Coordinate c;
        if (game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).getStatus() == cellStatus.EMPTY) {
            if (game.getPlayerTurn() == PlayerTurn.PLAYER1) {
                if (game.getPlayer1PieceSelected() == SelectedPiece.S) {
                    game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.S);
                    game.writeToFile(new Coordinate(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)), cellStatus.S);
                } else if (game.getPlayer1PieceSelected() == SelectedPiece.O) {
                    game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.O);
                    game.writeToFile(new Coordinate(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)), cellStatus.O);
                }
                onClickUI(game.checkForSOS(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)), cell);
                game.setPlayerTurn(PlayerTurn.PLAYER2);
            } else if (game.getPlayerTurn() == PlayerTurn.PLAYER2) {
                if (game.getPlayer2PieceSelected() == SelectedPiece.S) {
                    game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.S);
                    game.writeToFile(new Coordinate(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)), cellStatus.S);
                } else if (game.getPlayer2PieceSelected() == SelectedPiece.O) {
                    game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.O);
                    game.writeToFile(new Coordinate(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)), cellStatus.O);
                }

                onClickUI(game.checkForSOS(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)), cell);
                game.setPlayerTurn(PlayerTurn.PLAYER1);
            }
        }
        if (!game.getGameFinished()) {
            handleComputerMove();
        }
        else if (game.getGameFinished()) {
            winDisplay();
        }
    }

    private void winDisplay() {
        ObservableList<Node> childrenList = gameBoard.getChildren();
        for (Node node : childrenList) {
            node.setOnMouseClicked(null);
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CellController.class.getResource("win-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 350, 500);
            Stage stage = new Stage();
            stage.setTitle("Game Over");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private StackPane getChildFromGridPaneByRowAndColumn(int row, int column) {
        ObservableList<Node> childrenList = gameBoard.getChildren();
        for (Node node : childrenList) {
            if ((gameBoard.getRowIndex(node) == row) && (gameBoard.getColumnIndex(node) == column)) {
                return (StackPane) node;
            }
        }
        return null;
    }

    private int getSliderSize() {
        return  (int) sizeSlider.getValue();
    }

    private void setNewGameButtonHandler() {
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // Handles newGameButton Clicks
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("new game'd");
                PlayerType player1Type = null;
                PlayerType player2Type = null;
                boolean recordGame = false;
                if (player1HumanButton.isSelected()) {
                    player1Type = PlayerType.HUMAN;
                }
                else if (player1ComputerButton.isSelected()) {
                    player1Type = PlayerType.COMPUTER;
                }
                if (player2HumanButton.isSelected()) {
                    player2Type = PlayerType.HUMAN;
                }
                else if (player2ComputerButton.isSelected()) {
                    player2Type = PlayerType.COMPUTER;
                }
                if (recordGameBox.isSelected()) {
                    recordGame = true;
                }
                else {
                    recordGame = false;
                }

                if (simpleGame.isSelected()) {
                    game = new SimpleGame(getSliderSize(), player1Type, player2Type, recordGame);
                }
                else {
                    game = new GeneralGame(getSliderSize(), player1Type, player2Type, recordGame);
                }
                resizeBoard(getSliderSize());
                game.setPlayerTurn(PlayerTurn.PLAYER1);
                player1S.setSelected(true);
                player2S.setSelected(true);
                game.setPlayer1PieceSelected(SelectedPiece.S);
                game.setPlayer2PieceSelected(SelectedPiece.S);
                player1Pane.setStyle("-fx-background-color: #6D9DD5");
                player2Pane.setStyle("-fx-background-color: #FFFFFF");
                replayButtonEnableCheck();
                clearLogFile();

                if (player1Type == PlayerType.COMPUTER) {
                    handleComputerMove();
                }
            }
        });
    }

    private void clearLogFile() {
        try { // Basically clears the contents of the file
            PrintWriter writer = new PrintWriter("savedGame.txt");
            writer.print("");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resizeBoard(int size) {
        game.board.resizeBoard(size);
        Node n = null;
        ObservableList<Node> childrenList = gridpaneWrapper.getChildren();

        gameBoard.setMinWidth(500);
        gameBoard.setMaxWidth(500);
        gameBoard.setMinHeight(500);
        gameBoard.setMaxHeight(500);
        gameBoard.getChildren().clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StackPane sp = new StackPane();
                sp.setLayoutX(10);
                sp.setLayoutY(10);
                sp.setMinWidth(gameBoard.getMinWidth() / size);
                sp.setMaxWidth(gameBoard.getMaxWidth() / size);
                sp.setMinHeight(gameBoard.getMinHeight() / size);
                sp.setMaxHeight(gameBoard.getMaxHeight() / size);
                sp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                Label s = new Label("S");
                s.setFont(new Font("System Bold", 26));
                Label o = new Label("O");
                o.setFont(new Font("System Bold", 26));
                sp.getChildren().addAll(s, o);

                switch (game.board.getCellByIndex(i, j).getStatus()) {
                    case EMPTY:
                        s.setVisible(false);
                        o.setVisible(false);
                        break;
                    case O:
                        s.setVisible(false);
                        o.setVisible(true);
                        break;
                    case S:
                        s.setVisible(true);
                        o.setVisible(false);
                        break;
                }
                gameBoard.add(sp, i, j);
                setClickEventHandler(sp);
            }
        }
        while (childrenList.size() > 1) {
            childrenList.remove(1);
        }
    }

    private void setClickEventHandler(StackPane cell) {
        cell.setOnMouseClicked(event -> { // Handles mouse click event
            onClickData(cell);
        });

    }


    @FXML
    public void initialize() {
        HBox.setHgrow(player1Pane, Priority.ALWAYS);
        HBox.setHgrow(player2Pane, Priority.ALWAYS);

        setNewGameButtonHandler();

        player1S.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Player 1 Using S's");
                game.setPlayer1PieceSelected(SelectedPiece.S);
            }
        });

        player1O.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Player 1 Using O's");
                game.setPlayer1PieceSelected(SelectedPiece.O);
            }
        });

        player2S.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Player 2 Using S's");
                game.setPlayer2PieceSelected(SelectedPiece.S);
            }
        });

        player2O.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Player 2 Using O's");
                game.setPlayer2PieceSelected(SelectedPiece.O);
            }
        });

        replayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Got my iPod stuck on replay");
                replayGameFromTextFile();
            }
        });
        replayButtonEnableCheck();
        clearLogFile();

        System.out.println("Initialized");
        player1Pane.setStyle("-fx-background-color: #6D9DD5");
        resizeBoard(3);
    }
}
