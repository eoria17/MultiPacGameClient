package clientMenu;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import clientConnection.Settings;
import game.Position;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import packets.AddConnectionPacket;
import packets.ReadyPacket;
import packets.StartingPositionPacket;

import java.util.HashMap;

public class JoinRoom extends Application {
    Client c;

    public void start(Stage stage) {
        c = new Client(Settings.host, Settings.port);
        c.connect();
        AddConnectionPacket packet = new AddConnectionPacket();
        c.sendObject(packet);

        VBox vBox = new VBox();
        HBox hBox = new HBox(10);
        HBox hBox1 = new HBox(20);

        Text t1 = new Text("Player Start Position");
        Button ready = new Button("Ready?");
        Button goback = new Button("Back");
        Font fontBold = Font.font("Times New Roman", FontWeight.BOLD, 20);
        t1.setFont(fontBold);

        vBox.getChildren().addAll(t1);
        vBox.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(ready, goback);
        hBox1.setAlignment(Pos.CENTER);


        ToggleGroup group = new ToggleGroup();


        RadioButton tl = new RadioButton("Top Left");
        RadioButton tr = new RadioButton("Top Right");
        RadioButton bl = new RadioButton("Bottom left");
        RadioButton br = new RadioButton("Bottom Right");

        tl.setToggleGroup(group);
        tr.setToggleGroup(group);
        bl.setToggleGroup(group);
        br.setToggleGroup(group);
        Position topLeft = new Position(0, 0);
        Position topRight = new Position(0, 10);
        Position botLeft = new Position(10, 0);
        Position botRight = new Position(10, 10);

        HashMap<Integer, Position> startingPositions = new HashMap<Integer, Position>();
        startingPositions.put(1, topLeft);
        startingPositions.put(2, topRight);
        startingPositions.put(3, botLeft);
        startingPositions.put(4, botRight);

        hBox.getChildren().addAll(tl, tr, bl, br);
        hBox.setAlignment(Pos.CENTER);


        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(tl)) {
                if (ConnectionHandler.allPlayersPosition.containsValue(startingPositions.get(1))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Starting position already taken");
                    alert.showAndWait();
                } else {
                    ConnectionHandler.allPlayersPosition.put(ConnectionHandler.id, startingPositions.get(1));
                    StartingPositionPacket sPacket = new StartingPositionPacket(startingPositions.get(1));
                    c.sendObject(sPacket);
                }
            } else if (newValue.equals(tr)) {
                if (ConnectionHandler.allPlayersPosition.containsValue(startingPositions.get(2))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Starting position already taken");
                    alert.showAndWait();
                } else {
                    ConnectionHandler.allPlayersPosition.put(ConnectionHandler.id, startingPositions.get(2));
                    StartingPositionPacket sPacket = new StartingPositionPacket(startingPositions.get(2));
                    c.sendObject(sPacket);
                }
            } else if (newValue.equals(bl)) {
                if (ConnectionHandler.allPlayersPosition.containsValue(startingPositions.get(3))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Starting position already taken");
                    alert.showAndWait();
                } else {
                    ConnectionHandler.allPlayersPosition.put(ConnectionHandler.id, startingPositions.get(3));
                    StartingPositionPacket sPacket = new StartingPositionPacket(startingPositions.get(3));
                    c.sendObject(sPacket);
                }
            } else if (newValue.equals(br)) {
                if (ConnectionHandler.allPlayersPosition.containsValue(startingPositions.get(4))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Starting position already taken");
                    alert.showAndWait();
                } else {
                    ConnectionHandler.allPlayersPosition.put(ConnectionHandler.id, startingPositions.get(4));
                    StartingPositionPacket sPacket = new StartingPositionPacket(startingPositions.get(4));
                    c.sendObject(sPacket);
                }
            }
        });

        ready.setOnAction(event -> {
            stage.close();
            try {
                Thread.sleep(3000);

                if (!c.getSocket().isClosed()) {
                    ConnectionHandler.allPlayersReadyStatus.put(ConnectionHandler.id, true);
                    ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id, true);
                    c.sendObject(rpacket);
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ready.setDisable(true);
        });


        goback.setOnAction(event -> {
            stage.close();
            OnlineMenu om = new OnlineMenu();
            Stage st = new Stage();
            om.start(st);
        });


        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setCenter(hBox);
        borderPane.setBottom(hBox1);

        Scene scene = new Scene(borderPane, 600, 400);
        stage.setTitle("Join Room");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
