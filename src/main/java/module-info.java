module com.epds.javafx_login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires retrofit2.adapter.rxjava3;
    requires io.reactivex.rxjava3;
    requires okhttp3;
    requires com.google.gson;
    requires ormlite.jdbc;

    exports com.epds.javafx_login;
    opens com.epds.javafx_login to javafx.fxml;
    exports com.epds.javafx_login.utilities;
    opens com.epds.javafx_login.utilities to javafx.fxml;
    exports com.epds.javafx_login.utilities.controllers;
    opens com.epds.javafx_login.utilities.controllers to javafx.fxml;
    exports com.epds.javafx_login.ui;
    opens com.epds.javafx_login.ui to javafx.fxml;
    exports com.epds.javafx_login.api.chat.model;
    opens com.epds.javafx_login.api.chat.model to com.google.gson;
    exports com.epds.javafx_login.entities;
    opens com.epds.javafx_login.entities to ormlite.jdbc;
    exports com.epds.javafx_login.ui.callbacks;
}