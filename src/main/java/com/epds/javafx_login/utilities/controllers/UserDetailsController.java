package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.Main;
import com.epds.javafx_login.utilities.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDetailsController {

    @FXML public TextField mobileNumberField;
    @FXML public TextField emailField;
    @FXML public TextField firstNameField;
    @FXML public TextField lastNameField;
    @FXML public TextField middleNameField;
    @FXML public TextField nameExtensionField;
    @FXML public DatePicker birthdayPicker;
    @FXML public ChoiceBox<String> sexChoiceBox;
    @FXML public Button user_detail_submit;
    @FXML public TextField usernameField;
    @FXML private boolean isLoginPasswordVisible = false;
    @FXML public ImageView loginPasswordToggle;
    @FXML public TextField login_password_visible;
    @FXML public PasswordField login_password;

    @FXML
    protected void initialize() throws IOException {
        loginPasswordToggle.setOnMouseClicked(event -> {
            isLoginPasswordVisible = togglePasswordVisibility(login_password, login_password_visible, loginPasswordToggle, isLoginPasswordVisible);
        });

        user_detail_submit.setOnAction(event -> {
            try {
                saveData(
                        event,
                        usernameField.getText(),
                        login_password.getText(),
                        mobileNumberField.getText(),
                        emailField.getText(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        middleNameField.getText(),
                        nameExtensionField.getText(),
                        birthdayPicker.getValue() != null ? birthdayPicker.getValue().toString() : null,
                        sexChoiceBox.getValue()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveData(ActionEvent event, String username, String password, String mobileNumber, String email, String firstName, String lastName, String middleName, String nameExtension, String birthday, String sex) throws IOException {
        List<String> missingFields = new ArrayList<>();

        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            missingFields.add("Mobile Number");
        }
        if (email == null || email.trim().isEmpty()) {
            missingFields.add("Email");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            missingFields.add("First Name");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            missingFields.add("Last Name");
        }
        if (middleName == null || middleName.trim().isEmpty()) {
            missingFields.add("Middle Name");
        }
        if (nameExtension == null || nameExtension.trim().isEmpty()) {
            missingFields.add("Name Extension");
        }
        if (birthday == null || birthday.trim().isEmpty()) {
            missingFields.add("Birthday");
        }
        if (sex == null || sex.trim().isEmpty()) {
            missingFields.add("Sex");
        }

        if (!missingFields.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Please fill in the following fields:");
            alert.setContentText(String.join("\n", missingFields));
            alert.showAndWait();
        } else {
            // Implement your data saving logic here, for example, saving to a database.
            // This is just a simple print statement for demonstration purposes.
            System.out.println("Mobile Number: " + mobileNumber);
            System.out.println("Email: " + email);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Middle Name: " + middleName);
            System.out.println("Name Extension: " + nameExtension);
            System.out.println("Birthday: " + birthday);
            System.out.println("Sex: " + sex);

            if (DatabaseHelper.updateUser(username, password, mobileNumber, email, firstName, lastName, middleName, nameExtension, birthday, sex, 0)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/epds/javafx_login/scenes/main.fxml"));
                Parent root = loader.load();
                MainController mainController = loader.getController();
                mainController.MainApplication(event, username);
            }
        }
    }

    private boolean togglePasswordVisibility(PasswordField passwordField, TextField textField, ImageView toggleIcon, boolean isVisible) {
        if (isVisible) {
            textField.setManaged(false);
            textField.setVisible(false);
            textField.setEditable(false);
            textField.setPrefWidth(0);
            passwordField.setPrefWidth(254);
            passwordField.setManaged(true);
            passwordField.setVisible(true);
            passwordField.setEditable(true);
            passwordField.setFocusTraversable(true);
            passwordField.setText(textField.getText());
            passwordField.requestFocus();

            passwordField.selectPositionCaret(passwordField.getText().length());

            passwordField.deselect();
            toggleIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResource("images/eye.png")).toString()));
        } else {
            passwordField.setManaged(false);
            passwordField.setVisible(false);
            passwordField.setPrefWidth(0);
            passwordField.setEditable(false);
            textField.setPrefWidth(254);
            textField.setManaged(true);
            textField.setVisible(true);
            textField.setEditable(true);
            textField.setFocusTraversable(true);
            textField.setText(passwordField.getText());
            textField.requestFocus();

            textField.selectPositionCaret(passwordField.getText().length());

            textField.deselect();
            toggleIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResource("images/eye_hide.png")).toString()));
        }
        return !isVisible;
    }

    public void setUserPass(String user, String password) {
        usernameField.setText(user);
        login_password.setText(user);
    }
}
