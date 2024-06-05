package com.epds.javafx_login;

import com.epds.javafx_login.utilities.DatabaseHelper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scenes/login-register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setResizable(true);

        // Removes the default header design
        //stage.initStyle(StageStyle.UNDECORATED);

        DatabaseHelper.createUsersTable();
        DatabaseHelper.close();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static CompositeDisposable getCompositeDisposable() {
        return disposables;
    }

    @Override
    public void stop() throws Exception {
        // Dispose all subscriptions after program closes
        disposables.dispose();

        super.stop();
    }
}