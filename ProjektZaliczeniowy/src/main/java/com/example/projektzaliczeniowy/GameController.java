package com.example.projektzaliczeniowy;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameController implements Initializable {
    ArrayList<String> possibleButtons = new ArrayList<>(Arrays.asList("button0", "button1", "button2", "button3", "button4",
            "button5", "button6", "button7", "button8"));
    ArrayList<Button> buttons = new ArrayList<>();
    ArrayList<String> pattern = new ArrayList<>();
    int patternOrder = 0;
    Random random = new Random();
    int counter = 0;
    int turn = 1;

    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button buttonSt;
    @FXML
    private Button endButton;
    @FXML
    private Label text;
    @FXML
    private TextField nick;
    @FXML
    private Label user;
    @FXML
    private Label przegrana;
    @FXML
    private VBox vbox;
    @FXML
    private Button endButton2;
    @FXML
    private Button endButton3;
    @FXML
    private AnchorPane scena;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons.addAll(Arrays.asList(button0, button1, button2, button3, button4,
                button5, button6, button7, button8));
    }
    @FXML
    void buttonClicked(ActionEvent event) {

        if(((Control)event.getSource()).getId().equals(pattern.get(counter))){
            Button button = buttons.get(getIndexOfButton(event));
            changeButtonColor(button, "-fx-base: lightgreen", "-fx-border-color: black");
            counter++;
        } else {
            Button button = buttons.get(getIndexOfButton(event));
            changeButtonColor(button, "-fx-base: red", "-fx-border-color: black");
            vbox.setVisible(false);
            przegrana.setVisible(true);
            buttonSt.setVisible(false);
            text.setVisible(false);
            przegrana.setText("Przegrałeś! Twój wynik to: " + (pattern.size()-1));
            endButton.setVisible(true);
            endButton2.setVisible(true);
            scena.setCursor(Cursor.DEFAULT);
            return;
        }

        if(counter == turn){
            text.setText("Poziom: " + (pattern.size()));
            nextTurn();
            scena.setCursor(Cursor.NONE);
        }
    }
    @FXML
    void start(ActionEvent event) {
        if (nick.getText().isEmpty()) {
            nick.setStyle("-fx-border-color: red");
            nick.setPromptText("Wpisz swój nick");
        } else {
            pattern.clear();
            nick.setVisible(false);
            user.setText(nick.getText());
            user.setVisible(true);
            buttonSt.setVisible(false);
            scena.setCursor(Cursor.NONE);

            pattern.add(possibleButtons.get(random.nextInt(possibleButtons.size())));
            showPattern();
           // System.out.println(pattern);

            counter = 0;
            turn = 1;
        }
    }
    @FXML
    public void zakoncz(ActionEvent event) throws SQLException, ClassNotFoundException {

        przegrana.setText("Twój wynik został zapisany!");
        DataBase.add_update(nick.getText(), (pattern.size() - 1));
        endButton.setVisible(false);
        endButton3.setVisible(true);
    }

    private void nextTurn(){
        counter = 0;
        turn++;

        pattern.add(possibleButtons.get(random.nextInt(possibleButtons.size())));
        showPattern();
      //  System.out.println(pattern);
    }

    private int getIndexOfButton(ActionEvent event){
        String buttonId = ((Control)event.getSource()).getId();
        return Integer.parseInt(buttonId.substring(buttonId.length() -1));
    }
    private int getIndexOfButton2(String button){
        return Integer.parseInt(button.substring(button.length() -1));
    }

    private void showPattern(){
        PauseTransition pause = new PauseTransition(
                Duration.seconds(0.2));
        pause.setOnFinished(e -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), event -> {
                showNext();
            }));
            timeline.setCycleCount(pattern.size());
            timeline.play();
        });
        pause.play();

    }
    private void showNext(){
        Button button = buttons.get(getIndexOfButton2(pattern.get(patternOrder)));
        changeButtonColor(button, "-fx-base: gray", "-fx-border-color: black");
        patternOrder++;

        if(patternOrder == turn){
            patternOrder = 0;
            scena.setCursor(Cursor.DEFAULT);
        }
    }
    private void changeButtonColor(Button button, String color, String border){
        button.setStyle(color);
        PauseTransition pause = new PauseTransition(
                Duration.seconds(0.2));
        pause.setOnFinished(e -> {
            button.setStyle("-fx-border-color: black");
        });
        pause.play();
    }

    public void switchScenes(ActionEvent event) throws IOException {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("kolory.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zakonczGre(ActionEvent event){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
    }
}

