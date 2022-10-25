module com.example.sos {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
    exports com.example.gamelogic;
}