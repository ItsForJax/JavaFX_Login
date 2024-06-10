package com.epds.javafx_login;

import com.epds.javafx_login.database.DatabaseManager;
import com.epds.javafx_login.database.chat.ChatMessageRepository;
import com.epds.javafx_login.entities.ChatMessage;
import com.epds.javafx_login.utilities.DatabaseHelper;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private static final CompositeDisposable disposables = new CompositeDisposable();
    private DatabaseManager db;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("scenes/login-register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setResizable(true);

        DatabaseHelper.createUsersTable();
        DatabaseHelper.close();

        // Disable Logging
        LoggerFactory.setLogBackendFactory(LogBackendType.NULL);

        // Create a table of ChatMessages
        try {
            db = new DatabaseManager();
            db.createTablesIfNotExists(ChatMessage.class);

            ChatMessageRepository.initialize(db.getConnectionSource());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        // Close the database manager
        if (db != null)
            db.close();

        super.stop();
    }
}