package com.example.projektzaliczeniowy;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RankingController implements Initializable {
    @FXML
    private Button powrot;
    @FXML
    private TableView<RankingModel> rankingTable;
    @FXML
    private TableColumn<RankingModel, Integer> idCol;
    @FXML
    private TableColumn<RankingModel, String> nickCol;
    @FXML
    private TableColumn<RankingModel, Integer> poziomCol;
    private ObservableList<RankingModel> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = FXCollections.observableArrayList();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nickCol.setCellValueFactory(new PropertyValueFactory<>("nick"));
        poziomCol.setCellValueFactory(new PropertyValueFactory<>("poziom"));
        try {
            data = DataBase.getData();
            rankingTable.setItems(data);
            rankingTable.getSortOrder().setAll(Arrays.asList(poziomCol));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
}
