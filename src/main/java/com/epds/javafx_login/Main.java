package com.epds.javafx_login;

import com.epds.javafx_login.utilities.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        DatabaseHelper.createUsersTable();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}