module com.example.Minigames {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.Minigames to javafx.fxml;
    exports com.example.Minigames;
}