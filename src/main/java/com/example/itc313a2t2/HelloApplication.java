package com.example.itc313a2t2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {
    static List<Float> xList = new ArrayList<>();
    static List<Float> yList = new ArrayList<>();
    static double RADIUS = 5;

    public static void xAndYCoordinates(List<String> rawList) {
        char equals = '=';
        for (String e : rawList) {
            int xIndex = e.indexOf(equals) + 1;
            int indexOfSemiCol = e.indexOf(";");
            int yIndex = e.lastIndexOf(equals) + 1;
            xList.add(Float.parseFloat(e.substring(xIndex, indexOfSemiCol)));
            yList.add(Float.parseFloat(e.substring(yIndex)));
        }
    }

    public static void dragAndDrop() {

    }

    @Override
    public void start(Stage stage) throws IOException {
        List<Circle> circleList = new ArrayList<>();
        TextField coordinatesText = new TextField();
        coordinatesText.setDisable(true);
        coordinatesText.setAlignment(Pos.valueOf("BOTTOM_CENTER"));
        coordinatesText.setStyle("-fx-font-family: arial;\" + \"-fx-font-size: 12pt;");

        for (float i : xList) {
            int index = xList.indexOf(i);
            circleList.add(new Circle(xList.get(index), yList.get(index), RADIUS, Color.BLUE));
        }

        Pane pane = new Pane();
        for (Circle c : circleList) {
            c.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    coordinatesText.setText("x=" + c.getCenterX() + " y=" + c.getCenterY());
                }
            });
            pane.getChildren().add(c);
        }

        for (Circle c : circleList) {
            c.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    c.setCenterX(event.getX());
                    c.setCenterY(event.getY());
                }
            });
        }

        pane.getChildren().add(coordinatesText);
        Scene scene = new Scene(pane, 600, 600);
        stage.setTitle("Show Points");
        stage.setScene(scene);
        stage.show();
        FileWriter fileWriter = new FileWriter("src/main/java/com/example/itc313a2t2/points.txt");
        stage.setOnCloseRequest(value -> {
            System.out.println("Stage is closing");

            try {
                for (Circle c : circleList) {
                    fileWriter.write("x=" + c.getCenterX() + "; y=" + c.getCenterY());
                    fileWriter.write(System.lineSeparator());
                }
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        List<String> rawList = new ArrayList<>();
        File file = new File("src/main/java/com/example/itc313a2t2/points.txt");
        Scanner scan;

        {
            try {
                scan = new Scanner(file);
                while (scan.hasNextLine()) {
                    rawList.add(scan.nextLine());
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        xAndYCoordinates(rawList);
        launch();

    }
}