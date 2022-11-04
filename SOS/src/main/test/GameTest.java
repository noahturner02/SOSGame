import com.example.gamelogic.*;
import com.example.ui.CellController;
import com.example.ui.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GameTest {
    @Test
    @DisplayName("AC 1.1: Selecting Board Size For Blank Board")
    void AC1_1() {
        Game g = new SimpleGame(3);
        Assertions.assertEquals(3, g.board.getGameGrid().size()); // The number of rows should be initialized at 3
        g.board.resizeBoard(10);
        Assertions.assertEquals(10, g.board.getGameGrid().size()); // The number of rows should be 10 now.
    }
    @Test
    @DisplayName("AC 1.2: Selecting Board Size for Game In Progress")
    void AC1_2() {
        Game g = new SimpleGame(3);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S); // A piece is on the board. The game is now in progress
        g.board.resizeBoard(7);
        Assertions.assertEquals(7, g.board.getGameGrid().size());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Assertions.assertEquals(cellStatus.EMPTY, g.board.getCellByIndex(i, j).getStatus());
            }
        }
    }
    @Test
    @DisplayName("AC 1.3: Invalid Board Size")
    void AC1_3() { // Note that since the UI implementation uses a slider, that it is impossible to input a number other than {3, 4, 5, 6, 7, 8, 9, 10}
        // When a number larger than this is supplied directly to the resize method, though, an additional safeguard will prevent anything from happening
        Game g = new SimpleGame( 3);
        g.board.resizeBoard(12);
        Assertions.assertEquals(3, g.board.getGameGrid().size());
    }
    @Test
    @DisplayName("AC 2.1: Choose the game mode of the chosen game")
    void AC2_1() {
        Game g = new SimpleGame(3);
        Assertions.assertEquals(GameMode.SIMPLE, g.getGameMode());
        g.setGameMode(GameMode.GENERAL);
        Assertions.assertEquals(GameMode.GENERAL, g.getGameMode());
    }
    @Test
    @DisplayName("AC 3.1: Start a new game of the chosen board size and game mode")
    void AC3_1() {
        Game g = new SimpleGame(3);
        g = new GeneralGame(10); // To make a new game, simply construct a new game object supplied with the user's input.
        Assertions.assertEquals(10, g.board.getGameGrid().size());
        Assertions.assertEquals(GameMode.GENERAL, g.getGameMode());
        Assertions.assertEquals(PlayerTurn.PLAYER1, g.getPlayerTurn());
    }
    @Test
    @DisplayName("AC 4.1: Make a move in a simple game")
    void AC4_1() {
        Game g = new SimpleGame(3);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S); // Take the coordinates from the location in the GridPane
        Assertions.assertEquals(cellStatus.S, g.board.getCellByIndex(0, 0).getStatus());
    }
    @Test
    @DisplayName("AC 6.1: Make a move in a general game")
    void AC6_1() {
        Game g = new GeneralGame( 3);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.O);
        Assertions.assertEquals(cellStatus.O, g.board.getCellByIndex(0, 0).getStatus());
    }





}
