package com.example.fx_mysql;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentMaster {


    public SimpleIntegerProperty userId = new SimpleIntegerProperty();
    public SimpleStringProperty userName = new SimpleStringProperty();
    public SimpleStringProperty userEmail = new SimpleStringProperty();
    public SimpleStringProperty userLang = new SimpleStringProperty();

    public Integer getUserId() {
        return userId.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public String getUserLang() {
        return userLang.get();
    }

}
