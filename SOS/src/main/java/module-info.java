module com.example.sos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sos to javafx.fxml;
    exports com.example.sos;
}