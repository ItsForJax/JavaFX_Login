package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.utilities.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.security.PrivateKey;

public class LoginRegisterController {

    String DEFAULT_USERNAME = "admin";
    String DEFAULT_PASSWORD = "admin";


    @FXML
    private TextField login_username;
    @FXML
    private PasswordField login_password;
    @FXML
    private TextField register_username;
    @FXML
    private PasswordField register_pass;
    @FXML
    private PasswordField register_confirm_pass;
    @FXML
    private GridPane grid_pane_register;
    @FXML
    private GridPane grid_pane_login;
    @FXML
    private Pane login_logo_pane, register_logo_pane;

    SceneController sceneController;
    private Parent root;

    @FXML
    protected void gotoRegister() {
        grid_pane_login.setVisible(false);
        login_logo_pane.setVisible(false);
        grid_pane_register.setVisible(true);
        register_logo_pane.setVisible(true);
    }

    @FXML
    protected void gotoLogin() {
        grid_pane_register.setVisible(false);
        register_logo_pane.setVisible(false);
        login_logo_pane.setVisible(true);
        grid_pane_login.setVisible(true);
    }

    @FXML
    protected void login(ActionEvent event) throws IOException {
        String username = login_username.getText();
        String password = login_password.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/epds/javafx_login/scenes/main.fxml"));
        root = loader.load();
        sceneController = loader.getController();


        Alert alert;
        if (DatabaseHelper.loginUser(username, password)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome!");
            sceneController.MainApplication(event,username);

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password.");
        }
        alert.showAndWait();
    }

    @FXML
    protected void register() {
        String username = register_username.getText();
        String password = register_pass.getText();
        String confirmpass = register_confirm_pass.getText();

        Alert alert;
        if (!username.isBlank() && !password.isBlank() && !confirmpass.isEmpty()) {
            if (password.equals(confirmpass)){
                if (DatabaseHelper.registerUser(username, password)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("User registered successfully.");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("User " + username + " already exist!");
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setHeaderText(null);
                alert.setContentText("Password doesn't match!");
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all items!");
        }

        alert.showAndWait();
    }
}