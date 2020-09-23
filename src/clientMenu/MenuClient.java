package clientMenu;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class MenuClient extends Application {
    Boolean start = true;
    public void start(Stage Pstage){
        BorderPane borderPane = new BorderPane();
        Image im = new Image("file:PacMan-Start.png");
        VBox vBox = new VBox(20);
        Text text1 = new Text("Welcome to Pacman Game!");
        Font fontBold = Font.font("Times New Roman",FontWeight.BOLD,40);
        text1.setFont(fontBold);
        ImageView iv = new ImageView(im);
        Text text2 = new Text(200,200,"Please press Enter to start");
        text2.setFont(fontBold);
        vBox.getChildren().addAll(text1,iv,text2);
        vBox.setPadding(new Insets(11,12,13,14));
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);

        text2.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ENTER:
                    Pstage.close();
                    SelectModeMenu smm = new SelectModeMenu();
                    Stage st = new Stage();
                    smm.start(st);
                    break;
            }
        });



        Scene scene = new Scene(borderPane, 800, 800);
        Pstage.setTitle("Welcome");
        Pstage.setScene(scene);
        Pstage.show();
        text2.requestFocus();




        /*Pstage.close();
        SelectModeMenu smm = new SelectModeMenu();
        Stage st = new Stage();
        smm.start(st);*/

    }

    public static void main(String[] args) {
        launch(args);
    }

}

