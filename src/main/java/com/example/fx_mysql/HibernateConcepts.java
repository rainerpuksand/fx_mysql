package com.example.fx_mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HibernateConcepts {
    public static void main(String[] args) {
        ResultSet resultSet;
        Connection connection;
        List<Student> myList = new ArrayList();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/StudentsDemo", "root", "abcdefg1");

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM students;";// WHERE username = '"+ userName.getText()+"' AND password = '"+userPassword.getText()+"';";
            resultSet = statement.executeQuery(sql);//can never return null

            if (resultSet.isBeforeFirst()) {
                System.out.println("Connected");
                while (resultSet.next()) {//best way to know if rs is not empty.. cz there is no rs.count or .isEmpty()
                    int i = resultSet.getInt("id");
                    String n = resultSet.getString("first_name");
                    String e = resultSet.getString("email");
                    String l = resultSet.getString("language_chosen");
                    Student obj = new Student(i, n, e, l);
                    System.out.println(obj.id + " | " + obj.firstName);
                    myList.add(obj);
                }
                resultSet.last();
                System.out.println("Last id is : " + resultSet.getString("id"));
            } else {
                System.out.println("Not Connected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(myList.size());

        //get the student obj from the arraylist

        //print out its language chosen


    }
}

class Student {
    int id;
    String firstName, email, languageChosen;

    public Student(int id, String firstName, String email, String languageChosen) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.languageChosen = languageChosen;
    }


}
