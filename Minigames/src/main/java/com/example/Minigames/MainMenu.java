package com.example.Minigames;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;



public class MainMenu extends Application {

    private Stage MainMenu;

    public void start(Stage stage) {
        this.MainMenu = stage;
        stage.setTitle("Mini Games Menu");
        Group root = new Group();
        Scene scene = new Scene(root, 380.0, 380.0);
        scene.setFill(Color.LIGHTGRAY);

        VBox menuBox = createMenu();

        root.getChildren().addAll(menuBox);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createMenu() {
        VBox menuBox = new VBox(80);
        menuBox.setAlignment(Pos.CENTER);

        Text titleText = new Text("Mini Game Menu");
        titleText.setFont(Font.font(50));

        Button shapesButton = new Button("Play Mini Game with Shapes");
        shapesButton.setOnAction(e -> startShapesMiniGame());

        Button mathButton = new Button("Play Mini Game with Mathematics");
        mathButton.setOnAction(e -> startMathMiniGame());

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> quitGame());

        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(shapesButton, mathButton, quitButton);

        buttonContainer.setStyle("-fx-border-color: black; -fx-border-width: 1.5px; -fx-padding: 10px 30px;");

        menuBox.getChildren().addAll(titleText, buttonContainer);
        return menuBox;
    }


    private void startShapesMiniGame() {
        MainMenu.close();
        MiniGameShapes shapesMiniGame = new MiniGameShapes();
        shapesMiniGame.start(new Stage());
    }

        private void startMathMiniGame() {
            MainMenu.close();
            MiniGameMathematics miniGameMathematics =new MiniGameMathematics();
            miniGameMathematics.start(new Stage());
        }
    private void quitGame() {
        MainMenu.close();
    }

        public static void main(String[] args) {
            launch(args);
        }
    }