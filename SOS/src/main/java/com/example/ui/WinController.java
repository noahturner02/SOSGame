package com.example.ui;

import com.example.gamelogic.Cell;
import com.example.gamelogic.Winner;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinController {
    @FXML
    Label winText;

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
    }
}
