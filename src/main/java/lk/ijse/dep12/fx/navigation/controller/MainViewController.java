package lk.ijse.dep12.fx.navigation.controller;

import javafx.scene.control.Label;
import lk.ijse.dep12.fx.navigation.User;

public class MainViewController {
    public Label lblFullName;

    public void initData(User user) {
        lblFullName.setText(user.getFullName());
    }
}
