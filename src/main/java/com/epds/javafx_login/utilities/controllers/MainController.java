package com.epds.javafx_login.utilities.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private GridPane grid;
    @FXML
    private Button home_btn, chat_btn,
                    page_3, logout_btn;
    @FXML
    public AnchorPane main_pane;
    @FXML
    private Text username;
    @FXML
    private ImageView profile;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final String USERINFO_FXML_PATH = "/com/epds/javafx_login/scenes/user-details.fxml";
    private static final String MAIN_FXML_PATH = "/com/epds/javafx_login/scenes/main.fxml";
    private static final String LOGIN_REGISTER_FXML_PATH = "/com/epds/javafx_login/scenes/login-register.fxml";

    @FXML
    public void MainApplication(ActionEvent event, String user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_FXML_PATH));
            root = loader.load();

            // Retrieve the controller from the loader and set the user
            MainController controller = loader.getController();
            controller.setUser(user);

            controller.navigationInit();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while loading the main application scene.");
        }
    }

    @FXML
    private void initialize() {
        home_btn.setOnAction(event -> navigationInit());
        chat_btn.setOnAction(event -> navigateToChat());
        page_3.setOnAction(event -> navigateToPage3());
        logout_btn.setOnAction(event -> navigateToLoginRegister(event));
        Circle clip = new Circle(63.5, 60, 60);
        profile.setClip(clip);
    }

    @FXML
    public void UserInfo(ActionEvent event, String user, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(USERINFO_FXML_PATH));
            root = loader.load();

            // Retrieve the controller from the loader and set the user
            UserDetailsController controller = loader.getController();
            controller.setUserPass(user, password);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while loading the user info application scene.");
        }
    }

    private void setUser(String user) {
        username.setText("Hello " + user);
    }

    @FXML
    public void navigateToLoginRegister(ActionEvent event) {
        try {
            switchScene(event, LOGIN_REGISTER_FXML_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while loading the login/register scene.");
        }
    }

    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void navigationInit() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/epds/javafx_login/scenes/home.fxml")));
            grid.add(root, 1, 0); // Assuming 'grid' is your GridPane instance
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateToPage3() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/epds/javafx_login/scenes/page3.fxml")));
            grid.add(root, 1, 0); // Assuming 'grid' is your GridPane instance
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void navigateToChat() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/epds/javafx_login/scenes/chat.fxml")));
            grid.add(root, 1, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
