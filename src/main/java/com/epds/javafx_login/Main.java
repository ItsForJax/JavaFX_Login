package com.epds.javafx_login;

import com.epds.javafx_login.utilities.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scenes/login-register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setResizable(false);
//  Removes the default header design
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        DatabaseHelper.createUsersTable();
        DatabaseHelper.close();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}