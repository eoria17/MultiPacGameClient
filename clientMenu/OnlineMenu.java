package clientMenu;

import clientConnection.Client;
import clientConnection.Settings;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import packets.AddConnectionPacket;


public class OnlineMenu extends Application {
    public void start(Stage stage){
        VBox vBox = new VBox(50);
        Button cr = new Button("Create Room");
        Button jr = new Button("Join Roon");
        Button back = new Button("Go Back");
        vBox.getChildren().addAll(cr,jr,back);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(11,12,13,14));
        BorderPane pane = new BorderPane();
        pane.setCenter(vBox);
        
        /**
        *Link to create room function
        */

        cr.setOnAction(event -> {
            stage.close();
            CreateRoom cro = new CreateRoom();
            Stage st = new Stage();
            cro.start(st);
        });
        
        /**
        *Link to join room function
        */

        jr.setOnAction(event -> {
            stage.close();
            JoinRoom joinRoom = new JoinRoom();
            Stage st = new Stage();
            joinRoom.start(st);

        });
        /**
        *Link to go back function
        */

        back.setOnAction(event -> {
            stage.close();
            SelectModeMenu smm = new SelectModeMenu();
            Stage st = new Stage();
            smm.start(st);
        });

        Scene scene = new Scene(pane,400,400);
        stage.setTitle("Choose play mode");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
