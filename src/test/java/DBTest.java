import jdbc.DataBase;
import jdbc.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTest {

    String user;
    String database;
    String password;

    void initData() throws IOException {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFile = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFile);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("propertiy file " + propFile + "not found");
            }
            user = prop.getProperty("user");
            password = prop.getProperty("pwd");
            database = prop.getProperty("database");
        }catch (Exception e ){
            System.out.println("Exception" + e.getMessage());
        }finally {
            inputStream.close();
        }
    }

    @Test
    void shouldGetTeamDataEqualsWhenPlayerAdded() throws IOException {
        Person person = new Person(1,"Test","FirstTest");
        initData();
        DataBase db = new DataBase("jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC",user,password);
        Person p2 =  db.getDataFromDB();
        Assertions.assertEquals(person.getFirstName(), p2.getFirstName());
    }
}
