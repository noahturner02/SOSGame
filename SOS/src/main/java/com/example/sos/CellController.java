package com.example.sos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class CellController {
    @FXML
    StackPane cell00;
    @FXML
    StackPane cell01;
    @FXML
    StackPane cell02;
    @FXML
    StackPane cell10;
    @FXML
    StackPane cell11;
    @FXML
    StackPane cell12;
    @FXML
    StackPane cell20;
    @FXML
    StackPane cell21;
    @FXML
    StackPane cell22;
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
        setClickEventHandler(cell00);
        setClickEventHandler(cell01);
        setClickEventHandler(cell02);
        setClickEventHandler(cell10);
        setClickEventHandler(cell11);
        setClickEventHandler(cell12);
        setClickEventHandler(cell20);
        setClickEventHandler(cell21);
        setClickEventHandler(cell22);
    }



    @FXML
    public void initialize() {
        prepAllGrids();
        HBox.setHgrow(player1Pane, Priority.ALWAYS);
        HBox.setHgrow(player2Pane, Priority.ALWAYS);
        System.out.println("Initialized");
        player1Pane.setStyle("-fx-background-color: #6D9DD5");
    }
}
