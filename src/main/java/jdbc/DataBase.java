package jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

    private static PreparedStatement resultSet;
    private static Connection connection;
    private static final Logger logger = Logger.getLogger(DataBase.class.getName());



    public DataBase(String url, String user, String pwd) {
        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            connection = DriverManager.getConnection(url, user, pwd);
            if (connection != null) {
                logger.log(Level.INFO, "Connection Successful! Enjoy. Now it's time to push data");
            } else {
                logger.log(Level.SEVERE, "Failed to make connection!");
            }
        } catch (SQLException e) {
            logger.log(Level.INFO,"MySQL Connection Failed!");
            return;
        }
    }

    public static Person getDataFromDB() {
        try {
            // MySQL Select Query Tutorial
            String getQueryStatement = "SELECT * FROM personne";

            resultSet = connection.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = resultSet.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                int id = rs.getInt("Id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                // Simply Print the results
                Person person = new Person(id,firstName,lastName);
                logger.log(Level.INFO,  "Ajout de la personne : "+ person);
                return person;
            }
        } catch (SQLException e) {
            logger.log(Level.INFO,"MySQL PrepareStatement Failed! : " +e.getMessage());
        }
        return null;
    }


}
