package com.epds.javafx_login.utilities.controllers;

import com.epds.javafx_login.Main;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private GridPane grid;
    @FXML
    private Button home_btn, chat_btn, page_3, logout_btn;
    @FXML
    public AnchorPane main_pane;
    @FXML
    public Text username;
    @FXML
    private ImageView profile;

    private Stage stage;
    private Scene scene;

    private static final String USERINFO_FXML_PATH = "/com/epds/javafx_login/scenes/user-details.fxml";
    private static final String MAIN_FXML_PATH = "/com/epds/javafx_login/scenes/main.fxml";
    private static final String LOGIN_REGISTER_FXML_PATH = "/com/epds/javafx_login/scenes/login-register.fxml";

    private final Map<String, Node> scenes = new HashMap<>();

    @FXML
    public void initialize() {
        loadAllScenes();

        home_btn.setOnAction(event -> setScene("home"));
        chat_btn.setOnAction(event -> setScene("chat"));
        page_3.setOnAction(event -> setScene("page3"));

        logout_btn.setOnAction(this::navigateToLoginRegister);

        //Initial scene
        setScene("home");
    }

    private void loadAllScenes() {
        try {
            scenes.put("home", loadFXML("/com/epds/javafx_login/scenes/home.fxml"));
            scenes.put("chat", loadFXML("/com/epds/javafx_login/scenes/chat.fxml"));
            scenes.put("page3", loadFXML("/com/epds/javafx_login/scenes/page3.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setScene(String sceneName) {
        Node scene = scenes.get(sceneName);
        if (scene != null) {
            grid.getChildren().removeIf(node -> {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                return (columnIndex != null && columnIndex == 1) && (rowIndex != null && rowIndex == 0);
            });
            GridPane.setColumnIndex(scene, 1);
            GridPane.setRowIndex(scene, 0);
            grid.getChildren().add(scene);
        } else {
            System.err.println("Scene not found: " + sceneName);
        }
    }

    private Node loadFXML(String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        return loader.load();
    }

    @FXML
    public void MainApplication(ActionEvent event, String user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_FXML_PATH));
            Parent root = loader.load();
            MainController mainController = loader.getController();
            mainController.setUserName(user);
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
    public void UserInfo(ActionEvent event, String user, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(USERINFO_FXML_PATH));
            Parent root = loader.load();
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

    @FXML
    public void navigateToLoginRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_REGISTER_FXML_PATH));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setUserName(String user) {
        username.setText(user);
    }
}
