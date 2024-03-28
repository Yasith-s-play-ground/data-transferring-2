package lk.ijse.dep12.fx.navigation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep12.fx.navigation.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class LoginViewController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;
    private ArrayList<User> userList = new ArrayList<>();

    public void initialize() {
        userList.add(new User("Kasun Sampath", "kasun@ijse.lk", "kasun123"));
        userList.add(new User("Nuwan Ramindu", "nuwan@ijse.lk", "nuwan123"));
        userList.add(new User("Ruwan Perera", "ruwan@ijse.lk", "ruwan123"));
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().isBlank()) {
            txtUserName.requestFocus();
            return;
        } else if (txtPassword.getText().isBlank()) {
            txtPassword.requestFocus();
            return;
        }

        for (User user : userList) {
            if (user.getUsername().equals(txtUserName.getText().strip()) && user.getPassword().equals(txtPassword.getText().strip())) {
                //navigate to main view

                Stage stage = new Stage();
                //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainView.fxml"))));

                URL resource = getClass().getResource("/view/MainView.fxml");
                //AnchorPane container = FXMLLoader.load(resource);
                FXMLLoader fxmlLoader = new FXMLLoader(resource); // create an instance of FXML loader
                AnchorPane container = fxmlLoader.load();
                Scene mainScene = new Scene(container);
                stage.setScene(mainScene);

                MainViewController controller = fxmlLoader.getController(); // get the controller object of fxml loader
                controller.initData(user); // call initData method created by us in MainViewController by passing user object

                stage.setTitle("Main View");
                stage.show();
                stage.centerOnScreen();

                stage.setOnCloseRequest(windowEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
                    // create an alert for confirmation on closing the window and logging out
                    alert.setTitle("Logout?");
                    alert.setHeaderText("Logout");
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get() == ButtonType.NO) {
                        windowEvent.consume(); // consume methods stops event from continuing
                    } else {
                        Stage loginStage = new Stage(); // create new stage for login
                        try {
                            loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        loginStage.setResizable(false);
                        loginStage.setTitle("Login");
                        loginStage.centerOnScreen();
                        loginStage.show();
                    }
                });

                ((Stage) (btnLogin.getScene().getWindow())).close(); // close the stage where btnLogin is in
                return;
            }
        }

        new Alert(Alert.AlertType.ERROR, "Invalid login credentials").show();
        txtUserName.requestFocus();
    }
}
