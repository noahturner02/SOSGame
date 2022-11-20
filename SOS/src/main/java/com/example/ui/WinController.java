package com.example.ui;

import com.example.gamelogic.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinController {

    @FXML
    Label scoreLabel;
    @FXML
    Label winText;

    GeneralGame g;

    @FXML
    public void initialize() {
        if (CellController.game.winner == Winner.DRAW) {
            winText.setText("The game has ended in a draw.");
        }
        else if (CellController.game.winner == Winner.PLAYER1) {
            winText.setText("Player 1 wins!");
        }
        else if (CellController.game.winner == Winner.PLAYER2) {
            winText.setText("Player 2 wins!");
        }
        if (CellController.game.getGameMode() == GameMode.GENERAL) {
            g = (GeneralGame) CellController.game;
            scoreLabel.setText("Score: " + g.player1Points + " - " + g.player2Points);
        }
        else {
            scoreLabel.setText("");
        }
    }
}
