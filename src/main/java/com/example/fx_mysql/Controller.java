package com.example.fx_mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Boolean oldway = false;//true;//use this in the insertBtn_Click()

    @FXML
    Label id, name, email, language, isConnected, storedProcedure;
    @FXML
    Button nextBtn, previousBtn, lastBtn, firstBtn, updateBtn, delBtn, insertBtn;
    @FXML
    TextField userName, userEmail, userLang;

    @FXML
    private TableView<StudentMaster> table;
    @FXML
    private TableColumn<StudentMaster, Integer> idCol;
    @FXML
    private TableColumn<StudentMaster, String> nameCol;
    @FXML
    private TableColumn<StudentMaster, String> emailCol;
    @FXML
    private TableColumn<StudentMaster, String> langCol;
    private ObservableList<StudentMaster> data;


    ResultSet resultSet;
    Connection connection;

    public void button(ActionEvent actionEvent) {
        // textLabel.setText(textField.getText());
        System.out.println("Program is running");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/StudentsDemo", "root", "Ozzy1401");

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM students;";// WHERE username = '"+ userName.getText()+"' AND password = '"+userPassword.getText()+"';";
            resultSet = statement.executeQuery(sql);//can never return null

            if (resultSet.isBeforeFirst()) {
                isConnected.setText("Connected");
                while (resultSet.next()) {//best way to know if rs is not empty.. cz there is no rs.count or .isEmpty()
                    String i = resultSet.getString("id");
                    String n = resultSet.getString("first_name");
                    System.out.println(i + " | " + n);
                }
                resultSet.last();
                System.out.println("Last id is : " + resultSet.getString("id"));
            } else {
                isConnected.setText("Not Connected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//================now for tableView
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'UserMaster.fxml'.";
        idCol.setCellValueFactory(
                new PropertyValueFactory<StudentMaster, Integer>("userId"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<StudentMaster, String>("userName"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<StudentMaster, String>("userEmail"));
        langCol.setCellValueFactory(
                new PropertyValueFactory<StudentMaster, String>("userLang"));

        try {
            buildData();
        } catch (Exception ce) {
            System.out.println(ce.getMessage());
        }
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            String sql = "Select * from students";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                StudentMaster studentM_Obj = new StudentMaster();
                studentM_Obj.userId.set(rs.getInt("id"));
                studentM_Obj.userName.set(rs.getString("first_name"));
                studentM_Obj.userEmail.set(rs.getString("email"));
                studentM_Obj.userLang.set(rs.getString("language_chosen"));
                data.add(studentM_Obj);
            }
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @FXML
    public void storedProc_Click(MouseEvent event) throws Exception {
        //((Node)(event.getSource())).getScene().getWindow().hide(); // if need to close this scene first
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("StoredProc.fxml"));
        newStage.setTitle("StoredProcedures");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.show();

    }


    @FXML
    public void nextBtn_Click(ActionEvent actionEvent) {
        try {
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    @FXML
    public void lastBtn_Click(ActionEvent actionEvent) {
        try {
            resultSet.last();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    @FXML
    public void firstBtn_Click(ActionEvent actionEvent) {
        try {
            resultSet.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    @FXML
    public void previousBtn_Click(ActionEvent actionEvent) {
        try {
            resultSet.previous();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    public void display() {

        try {

            if (resultSet.isBeforeFirst()) {
                resultSet.first();
            } else if (resultSet.isAfterLast()) {
                resultSet.last();
            }

            id.setText(resultSet.getString(1));
            name.setText(resultSet.getString(2));
            email.setText(resultSet.getString(3));
            language.setText(resultSet.getString(4));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearStuff() {
        userName.setText(null);
        userEmail.setText(null);
        userLang.setText(null);
        buildData();
    }

    @FXML
    public void insertBtn_Click(ActionEvent event) {
        if (oldway) {
            Statement insertStatement = null;
            try {
                insertStatement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String tempName, tempEmail, tempLang;//Insert into students values (null,'ABCD', 'xyz@lt.com', '');
            tempEmail = userEmail.getText();
            tempLang = userLang.getText();
            tempName = userName.getText();
            if (userLang.getText().trim().isEmpty()) {
                System.out.println("EMPTY");
                return;
            } else {
                System.out.println("not...EMPTY");
            }


            String sql = "Insert into students values (null,'" + tempName + "', '" + tempEmail + "', '" + tempLang + "'); ";
            System.out.println(sql);

            try {
                int x = insertStatement.executeUpdate(sql);
                System.out.println("Returned Value: " + x);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//oldway ends====

        //new way
        else {
            String sql = "Insert into students(first_name, email, language_chosen) values (?,?,?);";
            String tempName, tempEmail, tempLang;
            tempEmail = userEmail.getText();
            tempLang = userLang.getText();
            tempName = userName.getText();


            PreparedStatement prepared_InsertStatement = null;
            try {
                prepared_InsertStatement = connection.prepareStatement(sql);

                prepared_InsertStatement.setString(1, tempName);
                prepared_InsertStatement.setString(2, tempEmail);
                prepared_InsertStatement.setString(3, tempLang);

                prepared_InsertStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        clearStuff();
    }

    @FXML
    public void updateBtn_Click(ActionEvent event) {
        String sql = "Update students set email = ?, language_chosen= ? where id = ?;";
        String tempEmail, tempLang;
        int tempId;
        tempEmail = userEmail.getText();
        tempLang = userLang.getText();
        tempId = Integer.valueOf(id.getText());


        PreparedStatement prepared_UpdateStatement = null;
        try {
            prepared_UpdateStatement = connection.prepareStatement(sql);

            prepared_UpdateStatement.setString(1, tempEmail);
            prepared_UpdateStatement.setString(2, tempLang);
            prepared_UpdateStatement.setInt(3, tempId);
            System.out.println(prepared_UpdateStatement.toString());

            prepared_UpdateStatement.executeUpdate();//return type is number of rows updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearStuff();
    }

    @FXML
    public void delBtn_Click(ActionEvent event) {
        try {
            Statement delStatement = connection.createStatement();
            String sql = "Delete from students where id = " + Integer.valueOf(id.getText());
            delStatement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        buildData();
    }


}
