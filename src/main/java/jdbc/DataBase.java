package jdbc;

import java.sql.*;
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

    ResultSet getDataScrollable() throws SQLException {
        String query = "SELECT * FROM personne";
        Statement stmt = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        return stmt.executeQuery(query);
    }

    ResultSet getUpdatableData() throws SQLException {
        String query = "SELECT * FROM personne";
        Statement stmt = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        return stmt.executeQuery(query);
    }

    boolean updateLine(int line_id, String firstName, String lastName, Date dob) throws SQLException {
        String query = "UPDATE personne SET first_name=?,last_name=?,dob=? WHERE id=?";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        stmt.setInt(1,line_id);
        stmt.setString(2,firstName);
        stmt.setString(3,lastName);
        stmt.setDate(4,dob);

        return stmt.execute();
    }

    boolean deleteLine(int line_id) throws SQLException {
        String query = "DELETE FROM personne WHERE id=?";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        stmt.setInt(1,line_id);

        return stmt.execute();
    }


}
