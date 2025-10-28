package com.example.Minigames;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;



public class MiniGameShapes extends Application {

    private Text messageText;
    private Stage miniGameShapesStage;
    private boolean isPaused = false;

    public void start(Stage MiniGameShapes) {
        this.miniGameShapesStage = MiniGameShapes;
        MiniGameShapes.setTitle("MinGameShapes");
        Group root = new Group();
        Scene shapeGameScene = new Scene(root, 400.0, 400.0);
        shapeGameScene.setFill(Color.LIGHTGRAY);

        final Rectangle target = new Rectangle(170.0, 50.0, 50.0, 50.0);
        target.setFill(Color.BLUEVIOLET);
        target.setArcWidth(10);
        target.setArcHeight(10);

        final Circle circle = createDraggableCircle(60, 200);
        final Polygon polygon = createDraggablePolygon(190, 200);
        final Rectangle rectangle = createDraggableRectangle(320, 200);

        target.setOnDragOver(event -> {
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        target.setOnDragEntered(event -> {
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                target.setFill(Color.RED);
            }
            event.consume();
        });

        target.setOnDragExited(event -> {
            target.setFill(Color.BLUEVIOLET);
            event.consume();
        });

        target.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString() && db.getString().equals("3")) {
                success = true;
                target.setFill(Color.GREEN);
                setMessage("Μπράβο, πολύ σωστά");
                showCorrectAnswerWindow();
            } else {
                target.setFill(Color.RED);
                setMessage("Προσπάθησε Ξανά");
            }
            event.setDropCompleted(success);
            event.consume();
        });

        HBox messageBox = new HBox();
        messageBox.setTranslateX(10);
        messageBox.setTranslateY(10);
        messageBox.setAlignment(Pos.TOP_RIGHT);

        messageText = new Text("Βρες το σωστό σχήμα");
        messageText.setFont(Font.font(16));
        messageBox.getChildren().add(messageText);

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> togglePause());
        pauseButton.setLayoutX(350);
        pauseButton.setLayoutY(10);

        root.getChildren().addAll(target, circle, polygon, rectangle, messageBox, pauseButton);
        MiniGameShapes.setScene(shapeGameScene);
        MiniGameShapes.show();
    }

    private Circle createDraggableCircle(double x, double y) {
        Circle draggableCircle = new Circle(x, y, 30);
        draggableCircle.setFill(Color.YELLOW);
        draggableCircle.setOnDragDetected(event -> {
            Dragboard db = draggableCircle.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("1");
            db.setContent(content);
            event.consume();
        });
        return draggableCircle;
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            showPauseWindow();
        } else {
            messageText.setText("Βρες το σωστό σχήμα");
        }
    }

    private Polygon createDraggablePolygon(double x, double y) {
        Polygon draggablePolygon = new Polygon ();
        draggablePolygon.getPoints().addAll(
                x, y - 30,
                x + 30, y + 30,
                x - 30, y + 30
        );
        draggablePolygon.setFill(Color.ORANGE);
        draggablePolygon.setOnDragDetected(event -> {
            Dragboard db = draggablePolygon.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("2");
            db.setContent(content);
            event.consume();
        });
        return draggablePolygon;
    }

    private Rectangle createDraggableRectangle(double x, double y) {
        Rectangle draggableRectangle = new Rectangle(x - 30, y - 30, 60, 60);
        draggableRectangle.setFill(Color.GREEN);
        draggableRectangle.setOnDragDetected(event -> {
            Dragboard db = draggableRectangle.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("3");
            db.setContent(content);
            event.consume();
        });
        return draggableRectangle;
    }

    private void showCorrectAnswerWindow() {

        Stage correctAnswerStage = new Stage();
        correctAnswerStage.initModality(Modality.APPLICATION_MODAL);
        correctAnswerStage.setTitle("Correct Answer");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Text message = new Text("Συνχαρητήρια βρήκες το σωστό");
        message.setFont(Font.font(20));

        Button nextLevelButton = new Button(" Next Level");
        nextLevelButton.setOnAction(e -> {
            correctAnswerStage.close();
            MiniGameMathematics math = new MiniGameMathematics();
            math.start(new Stage());
            miniGameShapesStage.close();
        });

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> {
            correctAnswerStage.close();
            MainMenu main = new MainMenu();
            main.start(new Stage());
            miniGameShapesStage.close();
        });


        vbox.getChildren().addAll(message, nextLevelButton, mainMenuButton);

        Scene scene = new Scene(vbox, 300, 200);
        correctAnswerStage.setScene(scene);
        correctAnswerStage.showAndWait();
    }

    private void showPauseWindow() {
        Stage pauseStage = new Stage();
        pauseStage.initModality(Modality.APPLICATION_MODAL);

        pauseStage.setTitle("Pause Menu");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Text message = new Text("Game Paused");
        message.setFont(Font.font(20));

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            pauseStage.close();
            togglePause();
        });
        Button MainButton = new Button("Main Menu");
        MainButton.setOnAction(e ->{
            pauseStage.close();
                miniGameShapesStage.close();
            MainMenu mainmenu = new MainMenu();
            mainmenu.start(new Stage());

        });

        MainButton.setLayoutX(50);
        MainButton.setLayoutY(120);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            pauseStage.close();
            miniGameShapesStage.close();
        });

        vbox.getChildren().addAll(message, resumeButton, MainButton, quitButton);

        Scene scene = new Scene(vbox, 300, 200);
        pauseStage.setScene(scene);
        pauseStage.showAndWait();
    }

        private void setMessage (String message){
            messageText.setText(message);
        }

        public static void main (String[]args){
            Application.launch(args);
        }
    }

