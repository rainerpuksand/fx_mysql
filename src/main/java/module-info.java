module com.example.fx_mysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fx_mysql to javafx.fxml;
    exports com.example.fx_mysql;
}