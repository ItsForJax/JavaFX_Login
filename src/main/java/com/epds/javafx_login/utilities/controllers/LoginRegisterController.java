package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.Main;
import com.epds.javafx_login.utilities.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class LoginRegisterController {

    @FXML
    public ImageView loginPasswordToggle;
    @FXML
    public ImageView registerPasswordToggle;
    @FXML
    public ImageView registerPasswordConfirmToggle;
    @FXML
    private TextField login_username;
    @FXML
    private PasswordField login_password;
    @FXML
    private TextField login_password_visible;
    @FXML
    private TextField register_username;
    @FXML
    private PasswordField register_pass;
    @FXML
    private TextField register_pass_visible;
    @FXML
    private PasswordField register_confirm_pass;
    @FXML
    private TextField register_confirm_pass_visible;
    @FXML
    private GridPane grid_pane_register;
    @FXML
    private GridPane grid_pane_login;
    @FXML
    private Pane login_logo_pane, register_logo_pane;
    @FXML
    private Button login_btn, go_to_register_btn,
                    register_btn, go_to_login_btn;
    @FXML
    private boolean isLoginPasswordVisible = false;
    private boolean isRegisterPasswordVisible = false;
    private boolean isRegisterConfirmPasswordVisible = false;

    public MainController mainController;
    private Parent root;

    @FXML
    protected void initialize() throws IOException {
        login_btn.setOnAction(event -> login(event));
        go_to_register_btn.setOnAction(event -> gotoRegister());
        register_btn.setOnAction(event -> register());
        go_to_login_btn.setOnAction(event -> gotoLogin());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/epds/javafx_login/scenes/main.fxml"));
        root = loader.load();
        mainController = loader.getController();

        loginPasswordToggle.setOnMouseClicked(event -> {
            isLoginPasswordVisible = togglePasswordVisibility(login_password, login_password_visible, loginPasswordToggle, isLoginPasswordVisible);
        });
        registerPasswordToggle.setOnMouseClicked(event -> {
            isRegisterPasswordVisible = togglePasswordVisibility(register_pass, register_pass_visible, registerPasswordToggle, isRegisterPasswordVisible);
        });
        registerPasswordConfirmToggle.setOnMouseClicked(event -> {
            isRegisterConfirmPasswordVisible = togglePasswordVisibility(register_confirm_pass, register_confirm_pass_visible, registerPasswordConfirmToggle, isRegisterConfirmPasswordVisible);
        });
    }

    // Toggle pass
    private boolean togglePasswordVisibility(PasswordField passwordField, TextField textField, ImageView toggleIcon, boolean isVisible) {
        if (isVisible) {
            int lastCaretPosition = textField.getCaretPosition();
            textField.setManaged(false);
            textField.setVisible(false);
            textField.setEditable(false);
            textField.setPrefWidth(0);
            passwordField.setPrefWidth(254);
            passwordField.setManaged(true);
            passwordField.setVisible(true);
            passwordField.setEditable(true);
            passwordField.setText(textField.getText());
            passwordField.requestFocus();
            passwordField.positionCaret(lastCaretPosition);

            passwordField.deselect();
            toggleIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResource("images/eye.png")).toString()));
        } else {
            int lastCaretPosition = passwordField.getCaretPosition();
            passwordField.setManaged(false);
            passwordField.setVisible(false);
            passwordField.setEditable(false);
            passwordField.setPrefWidth(0);
            textField.setPrefWidth(254);
            textField.setManaged(true);
            textField.setVisible(true);
            textField.setEditable(true);
            textField.setText(passwordField.getText());
            textField.requestFocus();
            textField.positionCaret(lastCaretPosition);

            toggleIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResource("images/eye_hide.png")).toString()));
        }
        return !isVisible;
    }

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
    protected void login(ActionEvent event) {
        String username = login_username.getText();
        String password = isLoginPasswordVisible ? login_password_visible.getText() : login_password.getText();

        Alert alert;
        if (DatabaseHelper.loginUser(username, password)) {
            if (!DatabaseHelper.isNewUser(username)){
                mainController.MainApplication(event, username);
            } else {
                mainController.UserInfo(event, username, password);
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome!");


        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password.");
        }
        alert.showAndWait();
        DatabaseHelper.close();
    }

    @FXML
    protected void register() {
        String username = register_username.getText();
        String password = isRegisterPasswordVisible ? register_pass_visible.getText() : register_pass.getText();
        String confirmpass = isRegisterConfirmPasswordVisible ? register_confirm_pass_visible.getText() : register_confirm_pass.getText();

        Alert alert;
        if (!username.isBlank() && !password.isBlank() && !confirmpass.isEmpty()) {
            if (password.equals(confirmpass)) {
                if (DatabaseHelper.registerUser(username, password)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("User registered successfully.");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("User " + username + " already exists!");
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
        DatabaseHelper.close();
    }
}
