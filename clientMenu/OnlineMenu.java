package clientMenu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;



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

        cr.setOnAction(event -> {
            stage.close();
            CreateRoom cro = new CreateRoom();
            Stage st = new Stage();
            cro.start(st);
        });

        jr.setOnAction(event -> {

        });







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
