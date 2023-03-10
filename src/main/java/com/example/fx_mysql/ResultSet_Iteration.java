import java.sql.*;
import java.util.Scanner;

public class ResultSet_Iteration {


    ResultSet resultSet;
    Connection connection;
    ResultSet recs;

    public ResultSet_Iteration() {
        makeTheConnection();
    }

    //https://stackoverflow.com/questions/55454688/how-to-fix-error-with-mysql-because-of-timezone-change
    public void makeTheConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/StudentsDemo", "root", "Ozzy1401");
//Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM students;";// WHERE username = '"+ userName.getText()+"' AND password = '"+userPassword.getText()+"';";
            resultSet = statement.executeQuery(sql);

            if (resultSet.isBeforeFirst()) {
                System.out.println("Hey! Connected to DB !!!");
                while (resultSet.next()) {
                    String i = resultSet.getString("id");
                    String n = resultSet.getString("first_name");
                    String e = resultSet.getString("email");
                    String l = resultSet.getString(4);
                    System.out.println(i + " | " + n + " | " + e + " | " + l);
                }
                resultSet.last();//comment to see EoRS error
                System.out.println("Last id is : " + resultSet.getString("id"));
            } else {
                System.out.println("Not Connected... ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //===========Iterations

    public void move_Next() {
        try {
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    public void move_Last() {
        try {
            resultSet.last();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    public void move_Previous() {
        try {
            resultSet.previous();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        display();
    }

    public void move_First() {
        try {
            resultSet.first();
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

            String str1 = (resultSet.getString(1));
            String str2 = (resultSet.getString(2));
            String str3 = (resultSet.getString(3));
            String str4 = (resultSet.getString(4));

            System.out.println(str1 + " | " + str2 + " | " + str3 + " | " + str4);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

