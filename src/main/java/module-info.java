module com.epds.javafx_login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.epds.javafx_login to javafx.fxml;
    exports com.epds.javafx_login;
    exports com.epds.javafx_login.utilities;
    opens com.epds.javafx_login.utilities to javafx.fxml;
}