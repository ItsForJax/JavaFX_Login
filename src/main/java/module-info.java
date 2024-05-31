module com.epds.javafx_login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    exports com.epds.javafx_login;
    opens com.epds.javafx_login to javafx.fxml;
    exports com.epds.javafx_login.utilities;
    opens com.epds.javafx_login.utilities to javafx.fxml;
    exports com.epds.javafx_login.utilities.controllers;
    opens com.epds.javafx_login.utilities.controllers to javafx.fxml;
    exports com.epds.javafx_login.ui;
    opens com.epds.javafx_login.ui to javafx.fxml;
}