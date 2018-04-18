
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConfig {
    public static Connection getMySqlConnection() {
        Connection mysqlConnection = null;
        try {
            //returns the Class object associated with the class or interface with the given string name
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost:3306/VetClinicDB"; //could be ip address or hostname, who is hosting my database. server on the network that i have access to
            mysqlConnection = DriverManager.getConnection(connectionUrl, "root", ""); //purposefully did not use password

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mysqlConnection;
    }
}
