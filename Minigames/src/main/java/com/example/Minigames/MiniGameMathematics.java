package com.example.Minigames;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.paint.Color;

public class MiniGameMathematics extends Application {
    private Stage miniGameMath;

    private int score = 0;
    private int maxHearts = 10;
    private int hearts = 3;
    private Label scoreLabel;
    private Label heartsLabel;
    private int correctAnswer;
    private TextField answerField;
    private Stage lostStage;
    private Stage pauseStage;
    private BorderPane calculationPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage miniGameMath) {
        this.miniGameMath = miniGameMath;
        this.miniGameMath.setTitle("Math Game");

        BorderPane mainPane = new BorderPane();

        GridPane grid = createGridPane();
        mainPane.setCenter(grid);


        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGRAY, null, null);
        Background background = new Background(backgroundFill);
        mainPane.setBackground(background);

        calculationPane = new BorderPane();
        calculationPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        calculationPane.setCenter(new Label());
        calculationPane.setPrefWidth(400);
        calculationPane.setPrefHeight(100);

        Label calculationLabel = (Label) calculationPane.getCenter();
        calculationLabel.setFont(new Font(36));

        mainPane.setTop(createPauseButton());

        Scene scene = new Scene(mainPane, 800, 400);
        miniGameMath.setScene(scene);
        miniGameMath.show();

        lostStage = new Stage();
        lostStage.setTitle("You Lost");
        lostStage.setScene(createLostScene());
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(20);

        Label calculationLabel = new Label();
        calculationLabel.setFont(new Font(36));

        GridPane.setColumnSpan(calculationLabel, 2);
        GridPane.setRowIndex(calculationLabel, 0);
        grid.getChildren().add(calculationLabel);

        scoreLabel = new Label("Score: " + score);
        scoreLabel.setFont(new Font(36));
        GridPane.setColumnSpan(scoreLabel, 2);
        GridPane.setRowIndex(scoreLabel, 1);
        grid.getChildren().add(scoreLabel);

        heartsLabel = new Label();
        updateHeartsLabel();
        GridPane.setColumnSpan(heartsLabel, 2);
        GridPane.setRowIndex(heartsLabel, 2);
        grid.getChildren().add(heartsLabel);

        answerField = new TextField();
        answerField.setPromptText("Enter your answer");
        GridPane.setColumnSpan(answerField, 2);
        GridPane.setRowIndex(answerField, 3);
        grid.getChildren().add(answerField);

        Button submitButton = new Button("Submit");
        GridPane.setColumnSpan(submitButton, 2);
        GridPane.setRowIndex(submitButton, 4);
        grid.getChildren().add(submitButton);

        submitButton.setOnAction(event -> checkAnswer(calculationLabel));

        generateProblem(calculationLabel);

        return grid;
    }

    private HBox createPauseButton() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_RIGHT);

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> togglePause());

        hbox.getChildren().add(pauseButton);

        return hbox;
    }

    private Scene createLostScene() {
        VBox lostBox = new VBox(20);
        lostBox.setAlignment(Pos.CENTER);

        Label lostLabel = new Label("You lost. Try again.");
        lostLabel.setFont(new Font(24));

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event -> {
            hearts = 3;
            score = 0;
            updateHeartsLabel();
            scoreLabel.setText("Score: " + score);
            lostStage.close();
            generateProblem((Label) calculationPane.getCenter());
        });

        lostBox.getChildren().addAll(lostLabel, restartButton);

        return new Scene(lostBox, 300, 150);
    }

    private void generateProblem(Label calculationLabel) {
        Random random = new Random();
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        char operator = "+-*/".charAt(random.nextInt(4));

        switch (operator) {
            case '+':
                correctAnswer = num1 + num2;
                break;
            case '-':
                correctAnswer = num1 - num2;
                break;
            case '*':
                correctAnswer = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    correctAnswer = num1 / num2;
                } else {
                    generateProblem(calculationLabel);
                    return;
                }
                break;
        }

        calculationLabel.setText(num1 + " " + operator + " " + num2 + " = ?");
        answerField.clear();
    }

    private void checkAnswer(Label calculationLabel) {
        try {
            int guess = Integer.parseInt(answerField.getText());
            if (guess == correctAnswer) {
                score++;
                if (score == 5 && hearts < maxHearts) {
                    hearts++;
                    score = 0;
                    updateHeartsLabel();
                }
                showAlert("Well done");
            } else {
                hearts--;
                if (hearts == 0) {
                    lostStage.show();
                }
                updateHeartsLabel();
                showAlert("Wrong");
            }
            scoreLabel.setText("Score: " + score);
            generateProblem(calculationLabel);
        } catch (NumberFormatException e) {

        }
    }

    private void updateHeartsLabel() {
        StringBuilder heartsText = new StringBuilder("Hearts: ");
        for (int i = 0; i < hearts; i++) {
            heartsText.append("❤");
        }
        heartsLabel.setText(heartsText.toString());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void togglePause() {
        if (pauseStage == null) {
            pauseStage = createPauseStage();
        }
        pauseStage.show();
    }
    private Stage createPauseStage() {
        Stage pauseStage = new Stage();
        this.pauseStage = pauseStage;
        pauseStage.setTitle("Pause Menu");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Text message = new Text("Game Paused");
        message.setFont(new Font(20));

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            pauseStage.close();
        });

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            pauseStage.close();
            miniGameMath.close();

            MainMenu mainMenu = new MainMenu();
            Stage mainMenuStage = new Stage();
            mainMenu.start(mainMenuStage);
        });

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            pauseStage.close();
            System.exit(0);
        });

        vbox.getChildren().addAll(message, resumeButton, backButton, quitButton);

        Scene scene = new Scene(vbox, 300, 200);
        pauseStage.setScene(scene);
        return pauseStage;
    }

}
