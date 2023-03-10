package com.example.fx_mysql;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StoredProc implements Initializable {

    ResultSet resultSet;
    Connection connection;
    CallableStatement myStmt = null;

    @FXML
    Button btn1, btn2, btn3, btn4, btn5;
    @FXML
    TextField txt1, txt2, txt3, txt4, txt5;
    @FXML
    Label ans1, ans2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/StudentsDemo", "root", "abcdefg1");

            myStmt = connection.prepareCall("{call getAllTeachers()}");
            myStmt.execute();
            System.out.println("Executed....\n");

            // Get the result set
            resultSet = myStmt.getResultSet();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Connected=====================");
                while (resultSet.next()) {
                    String n = resultSet.getString("first_name");
                    String s = resultSet.getString("subject");
                    String p = resultSet.getString("points");
                    System.out.println(n + "\t | " + s + "\t | " + p);
                }
            } else {
                System.out.println("Not Connectedxxxxxxxxxxxxxxxx");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void btn1_click(ActionEvent event) {
        try {
            myStmt = connection.prepareCall("{call updatePoints(?)}");
            myStmt.setInt(1, Integer.valueOf(txt1.getText()));
            //Boolean passed = myStmt.execute();//diff between execute & executeUpdate... even if execute works fine it gives us a false boolean
            int passed = myStmt.executeUpdate();//int usually means num of rows updated.. same for DELETE/INSERT
            System.out.println("Executed....\n");
            System.out.println("Return value: " + passed);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void btn2_click(ActionEvent event) {
        try {
            myStmt = connection.prepareCall("{call getSubjectsTeacher(?)}");
            myStmt.setString(1, txt2.getText());
            myStmt.execute();
            System.out.println("Executed....\n");

            resultSet = myStmt.getResultSet();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String n = resultSet.getString("first_name");
                    String s = resultSet.getString("subject");
                    String p = resultSet.getString("points");
                    System.out.println(n + "\t | " + s + "\t | " + p);
                }
            } else {
                System.out.println("xxxxxxxxxxxxxxxx");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void btn3_click(ActionEvent event) {
        try {

            myStmt = connection.prepareCall("{call twoParameters(?,?)}");
            myStmt.setString(1, txt3.getText());
            myStmt.setInt(2, Integer.valueOf(txt4.getText()));
            myStmt.execute();
            resultSet = myStmt.getResultSet();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String n = resultSet.getString("first_name");
                    String s = resultSet.getString("subject");
                    String p = resultSet.getString("points");
                    System.out.println(n + "\t | " + s + "\t | " + p);
                }
            } else {
                System.out.println("xxxxxxxxxxxxxxxx");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void btn4_click(ActionEvent event) {
        try {
            myStmt = connection.prepareCall("{call getCountofPoints(?,?)}");
            myStmt.setInt(1, Integer.valueOf(txt5.getText()));
            myStmt.registerOutParameter(2, Types.INTEGER);
            myStmt.execute();

            System.out.println("Executed....\n");

            int numTeachers = myStmt.getInt(2);
            String str = "There are " + numTeachers + " teachers above " + txt5.getText() + " points";
            ans1.setText(str);
            System.out.println(str);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btn5_click(ActionEvent event) {
        try {
            myStmt = connection.prepareCall("{call getMaxPointsSubject(?)}");
            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.execute();

            System.out.println("Executed....\n");

            String highestSubject = myStmt.getString(1);

            String str = "The Subject with Maximum points is: " + highestSubject;
            ans2.setText(str);
            System.out.println(str);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}