package com.example.ui;

import com.example.gamelogic.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CellController {

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

    static Game game = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);

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
                if (simpleGame.isSelected()) {
                    game = new SimpleGame(getSliderSize(), player1Type, player2Type);
                }
                else {
                    game = new GeneralGame(getSliderSize(), player1Type, player2Type);
                }
                resizeBoard(getSliderSize());
                game.setPlayerTurn(PlayerTurn.PLAYER1);
                player1S.setSelected(true);
                player2S.setSelected(true);
                game.setPlayer1PieceSelected(SelectedPiece.S);
                game.setPlayer2PieceSelected(SelectedPiece.S);
                player1Pane.setStyle("-fx-background-color: #6D9DD5");
                player2Pane.setStyle("-fx-background-color: #FFFFFF");
            }
        });
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

        ObservableList<Node> childrenList = cell.getChildren();
        cell.setOnMouseClicked(event -> { // Handles mouse click event

            // Processing click in game logic
            List<Coordinate> SOSList = new ArrayList<>();
            Coordinate c;
            if (game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).getStatus() == cellStatus.EMPTY) {
                if (game.getPlayerTurn() == PlayerTurn.PLAYER1) {
                    if (game.getPlayer1PieceSelected() == SelectedPiece.S) {
                        game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.S);
                    } else if (game.getPlayer1PieceSelected() == SelectedPiece.O) {
                        game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.O);
                    }
                    game.setPlayerTurn(PlayerTurn.PLAYER2);
                    if (game.player2Type == PlayerType.COMPUTER) {
                        c = game.computerMove();
                        switch (game.board.getCellByIndex(c.getX(), c.getY()).getStatus()) {
                            case S:
                                childrenList.get(0).setVisible(true);
                                break;
                            case O:
                                childrenList.get(1).setVisible(true);
                                break;
                        }
                    }
                } else if (game.getPlayerTurn() == PlayerTurn.PLAYER2) {
                    if (game.getPlayer2PieceSelected() == SelectedPiece.S) {
                        game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.S);
                    } else if (game.getPlayer2PieceSelected() == SelectedPiece.O) {
                        game.board.getCellByIndex(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)).setStatus(cellStatus.O);
                    }
                    game.setPlayerTurn(PlayerTurn.PLAYER1);
                    if (game.player1Type == PlayerType.COMPUTER) {
                        c = game.computerMove();
                        switch (game.board.getCellByIndex(c.getX(), c.getY()).getStatus()) {
                            case S:
                                childrenList.get(0).setVisible(true);
                                break;
                            case O:
                                childrenList.get(1).setVisible(true);
                                break;
                        }
                    }
                }
                SOSList = game.checkForSOS(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell));
            }
            if (game.getGameFinished()) {
                winDisplay();
            }

            // UI control

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
                    if (game.getPlayerTurn() == PlayerTurn.PLAYER1) {
                        line.setStyle("-fx-stroke: red");
                    }
                    else if (game.getPlayerTurn() == PlayerTurn.PLAYER2) {
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
                player2Pane.setStyle("-fx-background-color: #FFFFFF");
                player1Pane.setStyle("-fx-background-color: #6D9DD5");
            } else if (game.getPlayerTurn() == PlayerTurn.PLAYER2) {
                player2Pane.setStyle("-fx-background-color: #CC4B23");
                player1Pane.setStyle("-fx-background-color: #FFFFFF");
            }
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

        System.out.println("Initialized");
        player1Pane.setStyle("-fx-background-color: #6D9DD5");
        resizeBoard(3);
    }
}
