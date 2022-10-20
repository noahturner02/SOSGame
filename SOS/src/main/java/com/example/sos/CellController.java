package com.example.sos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CellController {
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
    private boolean playerTurn = false; // 1 -> player 2's turn; 0 -> player 1's turn

    private void resizeBoard(int size) {
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
                s.setVisible(false);
                s.setFont(new Font("System Bold", 26));
                Label o = new Label("O");
                o.setVisible(true);
                o.setFont(new Font("System Bold", 26));
                sp.getChildren().addAll(s, o);
                gameBoard.add(sp, i, j);
            }
        }
    }

    private void setClickEventHandler(StackPane cell) {
        ObservableList<Node> childrenList = cell.getChildren();
        cell.setOnMouseClicked(event -> { // Handles mouse click event
            if ((childrenList.get(0).isVisible() == false) && (childrenList.get(1).isVisible() == false)) { // if square is empty
                if (playerTurn) { // Retrieve the radio button value and place the piece in the square
                    if (player2S.isSelected()) {
                        childrenList.get(0).setVisible(true);
                    } else if (player2O.isSelected()) {
                        childrenList.get(1).setVisible(true);
                    }
                } else {
                    if (player1S.isSelected()) {
                        childrenList.get(0).setVisible(true);
                    } else if (player1O.isSelected()) {
                        childrenList.get(1).setVisible(true);
                    }
                }
                System.out.println("Bruh");
                playerTurn = !playerTurn; // Change the turn
                if (playerTurn) { // Set the color of the active player to blue
                    player2Pane.setStyle("-fx-background-color: #6D9DD5");
                    player1Pane.setStyle("-fx-background-color: #FFFFFF");
                } else {
                    player2Pane.setStyle("-fx-background-color: #FFFFFF");
                    player1Pane.setStyle("-fx-background-color: #6D9DD5");
                }
            }
        });

    }
    private void prepAllGrids() {
    }



    @FXML
    public void initialize() {
        prepAllGrids();
        HBox.setHgrow(player1Pane, Priority.ALWAYS);
        HBox.setHgrow(player2Pane, Priority.ALWAYS);
        System.out.println("Initialized");
        player1Pane.setStyle("-fx-background-color: #6D9DD5");
        resizeBoard(4);
    }
}
