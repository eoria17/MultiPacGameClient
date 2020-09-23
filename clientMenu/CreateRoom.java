package clientMenu;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import clientConnection.Settings;
import game.Position;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import packets.AddConnectionPacket;
import packets.ReadyPacket;
import packets.SettingPacket;
import packets.StartingPositionPacket;

import java.util.HashMap;


public class CreateRoom extends Application {
    public int limit;
    Client c;

    public void start(Stage stage) {
        c = new Client(Settings.host, Settings.port);
        c.connect();
        VBox vBox = new VBox(70);
        HBox hBox = new HBox(20);


        VBox vBox1 = new VBox(70);
        HBox hBox1 = new HBox(20);

        TextField textField = new TextField();


        Text t1 = new Text("Player Number");
        Text t2 = new Text("Player Start Position");
        Button ready = new Button("Ready?");
        Button goback = new Button("Back");
        Font fontBold = Font.font("Times New Roman", FontWeight.BOLD, 20);
        t1.setFont(fontBold);
        t2.setFont(fontBold);


        /**
         * player number
         */
        final String[] a = new String[]{"1", "2", "3", "4"};
        ChoiceBox<String> cb = new ChoiceBox<String>(
                FXCollections.observableArrayList("1", "2", "3", "4")
        );
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                textField.setText(a[newValue.intValue()]);
                limit = Integer.valueOf(textField.getText());
            }
        });


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

        hBox1.getChildren().addAll(tl, tr, bl, br);

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
            }else if(newValue.equals(tr)){
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
            }else if(newValue.equals(bl)){
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
            }else if(newValue.equals(br)){
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


        vBox.getChildren().addAll(t1, t2);
        vBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(ready, goback);
        hBox.setAlignment(Pos.CENTER);

        cb.setTooltip(new Tooltip("Select player number"));

        vBox1.getChildren().addAll(cb, hBox1);
        vBox1.setAlignment(Pos.CENTER);

        ready.setOnAction(event -> {


            try {
                Thread.sleep(1000);

                if (!c.getSocket().isClosed()) {
                    // (Theo) This will set the limit of how many clients are able to connect to the
                    // server.
                    SettingPacket settingPacket = new SettingPacket(limit);
                    c.sendObject(settingPacket);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                Thread.sleep(2000);

                if (!c.getSocket().isClosed()) {
                    // (Theo) This add connection packet will register the connection to the server,
                    // registering into the server's connected clients list.
                    AddConnectionPacket packet = new AddConnectionPacket();
                    c.sendObject(packet);

                    ConnectionHandler.allPlayersReadyStatus.put(ConnectionHandler.id, true);
                    ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id, true);
                    c.sendObject(rpacket);


                    // select starting position
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });


        goback.setOnAction(event -> {
            stage.close();
            SelectModeMenu smm = new SelectModeMenu();
            Stage st = new Stage();
            smm.start(st);
        });


        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setCenter(vBox1);
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane, 600, 400);
        stage.setTitle("Room");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}


