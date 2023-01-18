package com.example.tresenratlla;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {
    //Aquí abrimos la aplicación principal de javaFX con ayuda de FXMLLoader
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tic Tac Tarik");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Ponemos en marcha la APP y a jugar!
        launch(args);
    }
}