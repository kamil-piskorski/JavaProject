package com.example.projektzaliczeniowy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;

public class DataBase {
    public static Connection connectDB() throws ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/projektjava","root","");
          //  System.out.println("nawiazano polaczenie");
            return conn;
            } catch (SQLException e){
            e.getErrorCode();
            return null;
        }
    }

    public static ObservableList<RankingModel> getData() throws ClassNotFoundException, SQLException {
        Connection conn = connectDB();
        ObservableList<RankingModel> lista = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from ranking");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                lista.add(new RankingModel((lista.size()+1), rs.getString("nick"), Integer.parseInt(rs.getString("poziom"))));
            }
        } catch (Exception e){ }
        return lista;
    }

    public static void add_update(String nick, int poziom) throws SQLException, ClassNotFoundException {
        Connection conn = connectDB();
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from ranking where nick = ?");
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();

            if(rs.isBeforeFirst()) {
                psUpdate = conn.prepareStatement("update ranking set poziom = ? where nick = ? and poziom < ?");
                psUpdate.setInt(1, poziom);
                psUpdate.setString(2, nick);
                psUpdate.setInt(3, poziom);
                psUpdate.execute();
            } else {
                psInsert = conn.prepareStatement("INSERT INTO RANKING (nick, poziom) VALUES (?, ?)");
                psInsert.setString(1, nick);
                psInsert.setInt(2, poziom);
                psInsert.execute();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}