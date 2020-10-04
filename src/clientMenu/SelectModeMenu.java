package clientMenu;


import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import singlePlayerGame.Game;

import javax.swing.*;



public class SelectModeMenu extends Application {

    public void start(Stage stage) {
        VBox vBox = new VBox(50);
        Text text1 = new Text("Please select play mode");
        Font fontBold = Font.font("Times New Roman", FontWeight.BOLD,20);
        text1.setFont(fontBold);
        Button single = new Button("Single Player");
        Button multi = new Button("Play Online");
        vBox.getChildren().addAll(text1,single,multi);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(11,12,13,14));
        BorderPane pane = new BorderPane();
        pane.setCenter(vBox);

        single.setOnAction(event -> {
            Task<Game> task = new Task<Game>() {
                @Override
                protected Game call() throws Exception {
                    try{
                        Game game = new Game();
                        game.setTitle("Monster Game");
                        game.setSize(700,700);
                        game.setLocationRelativeTo(null);  // center the frame
                        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        game.setVisible(true);
                        game.play();
                    }catch (Exception ae){
                        ae.printStackTrace();
                    }
                    return null;
                }
            };

            new Thread(task).start();
        });


        multi.setOnAction(event -> {
            stage.close();
            OnlineMenu om = new OnlineMenu();
            Stage st = new Stage();
            om.start(st);
        });


        Scene scene = new Scene(pane,400,400);
        stage.setTitle("Choose play mode");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)  {
        launch(args);
    }


}

