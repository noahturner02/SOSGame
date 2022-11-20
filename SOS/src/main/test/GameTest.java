import com.example.gamelogic.*;
import com.example.ui.CellController;
import com.example.ui.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class GameTest {
    @Test
    @DisplayName("AC 1.1: Selecting Board Size For Blank Board")
    void AC1_1() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        Assertions.assertEquals(3, g.board.getGameGrid().size()); // The number of rows should be initialized at 3
        g.board.resizeBoard(10);
        Assertions.assertEquals(10, g.board.getGameGrid().size()); // The number of rows should be 10 now.
    }
    @Test
    @DisplayName("AC 1.2: Selecting Board Size for Game In Progress")
    void AC1_2() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
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
        Game g = new SimpleGame( 3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.resizeBoard(12);
        Assertions.assertEquals(3, g.board.getGameGrid().size());
    }
    @Test
    @DisplayName("AC 2.1: Choose the game mode of the chosen game")
    void AC2_1() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        Assertions.assertEquals(GameMode.SIMPLE, g.getGameMode());
        g.setGameMode(GameMode.GENERAL);
        Assertions.assertEquals(GameMode.GENERAL, g.getGameMode());
    }
    @Test
    @DisplayName("AC 3.1: Start a new game of the chosen board size and game mode")
    void AC3_1() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g = new GeneralGame(10, PlayerType.HUMAN, PlayerType.HUMAN); // To make a new game, simply construct a new game object supplied with the user's input.
        Assertions.assertEquals(10, g.board.getGameGrid().size());
        Assertions.assertEquals(GameMode.GENERAL, g.getGameMode());
        Assertions.assertEquals(PlayerTurn.PLAYER1, g.getPlayerTurn());
    }
    @Test
    @DisplayName("AC 4.1: Make a move in a simple game")
    void AC4_1() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S); // Take the coordinates from the location in the GridPane
        Assertions.assertEquals(cellStatus.S, g.board.getCellByIndex(0, 0).getStatus());
    }
    @Test
    @DisplayName("AC 6.1: Make a move in a general game")
    void AC6_1() {
        Game g = new GeneralGame( 3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.O);
        Assertions.assertEquals(cellStatus.O, g.board.getCellByIndex(0, 0).getStatus());
    }

    @Test
    @DisplayName("Register an SOS on simple")
    void SOS_simple() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.O);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        List<Coordinate> SOS = g.checkForSOS(0, 2);
        Assertions.assertEquals(g.board.getCellByIndex(SOS.get(0).getX(), SOS.get(0).getY()).getStatus(), cellStatus.S);
        Assertions.assertEquals(g.board.getCellByIndex(SOS.get(1).getX(), SOS.get(1).getY()).getStatus(), cellStatus.O);
        Assertions.assertEquals(g.board.getCellByIndex(SOS.get(2).getX(), SOS.get(2).getY()).getStatus(), cellStatus.S);
    }

    @Test
    @DisplayName("Register an SOS on General")
    void SOS_General() {
        Game g = new GeneralGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.O);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        List<Coordinate> SOS = g.checkForSOS(0, 1);
        Assertions.assertEquals(g.board.getCellByIndex(SOS.get(0).getX(), SOS.get(0).getY()).getStatus(), cellStatus.S);
        Assertions.assertEquals(g.board.getCellByIndex(SOS.get(1).getX(), SOS.get(1).getY()).getStatus(), cellStatus.O);
        Assertions.assertEquals(g.board.getCellByIndex(SOS.get(2).getX(), SOS.get(2).getY()).getStatus(), cellStatus.S);
    }

    @Test
    @DisplayName("Multiple SOS' with one move")
    void mult_SOS() {
        GeneralGame g = new GeneralGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 1).setStatus(cellStatus.O);
        List<Coordinate> SOS = g.checkForSOS(1, 1);
        Assertions.assertEquals(g.player1Points, 4);
    }

    @Test
    @DisplayName("A Simple Game ends in a win")
    void simpleWin() {
        SimpleGame g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.O);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        g.checkForSOS(0, 0);
        Assertions.assertEquals(g.getGameFinished(), true);
        Assertions.assertEquals(g.winner, Winner.PLAYER1);
    }

    @Test
    @DisplayName("A Simple Game ends in a draw")
    void simpleDraw() {
        SimpleGame g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 2).setStatus(cellStatus.S);
        g.checkForSOS(0, 0);
        Assertions.assertEquals(g.getGameFinished(), true);
        Assertions.assertEquals(g.winner, Winner.DRAW);
    }

    @Test
    @DisplayName("A General Game ends in a win")
    void generalWin() {
        GeneralGame g = new GeneralGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 0).setStatus(cellStatus.O);
        g.checkForSOS(1, 0);
        Assertions.assertEquals(g.getGameFinished(), false);
        g.board.getCellByIndex(1, 1).setStatus(cellStatus.O);
        g.checkForSOS(1, 1);
        Assertions.assertEquals(g.getGameFinished(), false);
        g.board.getCellByIndex(1, 2).setStatus(cellStatus.O);
        g.checkForSOS(1, 2);
        Assertions.assertEquals(g.getGameFinished(), true);
        Assertions.assertEquals(g.winner, Winner.PLAYER1);
    }

    @Test
    @DisplayName("A General Game ends in a draw")
    void generalDraw() {
        GeneralGame g = new GeneralGame(3, PlayerType.HUMAN, PlayerType.HUMAN);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(1, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 1).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 2).setStatus(cellStatus.S);
        g.checkForSOS(0, 0);
        Assertions.assertEquals(g.getGameFinished(), true);
        Assertions.assertEquals(g.winner, Winner.DRAW);
    }

    @Test
    @DisplayName("AC 8.3")
    void AC8_3() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.COMPUTER);
        g.board.getCellByIndex(0, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.O);
        Coordinate c = g.computerMove();
        Assertions.assertEquals(c.getX(), 0);
        Assertions.assertEquals(c.getY(), 2);
    }

    @Test
    @DisplayName("AC 8.4")
    void AC8_4() {
        Game g = new SimpleGame(3, PlayerType.HUMAN, PlayerType.COMPUTER);
        g.board.getCellByIndex(0, 1).setStatus(cellStatus.O);
        Coordinate c = g.computerMove();
        Assertions.assertEquals(c.getX(), 1);
        Assertions.assertEquals(c.getY(), 0);
    }

    @Test
    @DisplayName("AC 8.5")
    void AC8_5() {
        Game g = new GeneralGame(5, PlayerType.HUMAN, PlayerType.COMPUTER);
        g.board.getCellByIndex(1, 0).setStatus(cellStatus.O);
        g.board.getCellByIndex(2, 0).setStatus(cellStatus.S);
        g.board.getCellByIndex(4, 4).setStatus(cellStatus.S);
        g.board.getCellByIndex(3, 4).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 4).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 3).setStatus(cellStatus.S);
        g.board.getCellByIndex(2, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(3, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(4, 2).setStatus(cellStatus.S);
        g.board.getCellByIndex(4, 3).setStatus(cellStatus.S);
        Coordinate c = g.computerMove();
        Assertions.assertEquals(c.getY(), 3);
        Assertions.assertEquals(c.getX(), 3);
    }




}
