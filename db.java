import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//,.............................WORK DONE...................................


public class db {
    static final String URL = "jdbc:mysql://localhost:3306/sysinfo";
    static String USERNAME = "root";
    static String PASSWORD = "root";
    static Connection connection = null;
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } 
            catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("\nUnsuccessfull connection.");
                System.exit(0);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            }
            catch(SQLException e){
            e.printStackTrace();
            } 
        }
    }

}
