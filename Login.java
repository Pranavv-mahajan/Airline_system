import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


//,.............................PENDING...................................


public class Login {
    private static String email;
    private static String password;
    private static String name;
    private static int admin_id;

    //******************************************************/
    //***************** LOGIN USER *************************
    //******************************************************/

    public static void loginUser(Scanner scanner){
        System.out.println("\n***********  Welcome to User Login  ************\n");
    
        Connection conn = db.getConnection();
    
        System.out.print("Enter Email : ");
        email = scanner.next();
    
        System.out.print("Enter Password : ");
        password = scanner.next();
    
        boolean loginSuccessful = false; // Variable to track login success
    
        // Check if the user exists
        try (PreparedStatement statement = conn.prepareStatement("SELECT email, password, name FROM user WHERE email = ? AND password = ?")){
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                name = result.getString("name");
                System.out.println("\nWelcome, " + name + "!");
                loginSuccessful = true; // Set login success flag to true
            } else {
                System.out.println("\nWrong credentials. Please try again.\n");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    
        // If login was unsuccessful, offer the option to try again
       while (!loginSuccessful) {
        try{
            System.out.println("1. Try Login Again");
            System.out.println("2. Back to Main Menu");
            System.out.print("\nSelect an option: ");
            int option = scanner.nextInt();
            
            if (option == 1) {
                Login.loginUser(scanner);
            } else if(option == 2) {
                return;
            }
            else{
                System.out.println("Please enter a valid choice.\n");
            }
        }

        catch(InputMismatchException e){
            scanner.nextLine();
            System.out.println("Enter a valid option. \n");
        }
        }

        //Login login = new Login();
        Login.user(email, password, scanner);
            
    }

    //******************************************************/
    //********************** LOGIN ADMIN *******************
    //******************************************************/

    public static void loginAdmin(Scanner scanner){
        System.out.println("\n***********  Welcome to Admin Login  ************\n");


        System.out.print("Enter Email: ");
        email = scanner.next();

        System.out.print("Enter Password: ");
        password = scanner.next();

        System.out.print("Enter admin_id: ");
        admin_id = scanner.nextInt();

        Connection conn = db.getConnection();

        boolean loginSuccessful = false;
        
        try(PreparedStatement statement = conn.prepareStatement("SELECT email, password, admin_id, name FROM admin WHERE email = ? AND password = ? AND admin_id = ? ")){
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setInt(3, admin_id);

            ResultSet result = statement.executeQuery();
            if(result.next()){
                name = result.getString("name");
                System.out.println("\nWelcome, "+ name +"!");
                loginSuccessful =true;
            }
            else {
                System.out.println("\nWrong credentials. Please try again. \n");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        while(!loginSuccessful){
            try{
                System.out.println("1. Try Logging-IN Again. ");
                System.out.println("2. Back to Main Menu. ");
                System.out.println("\nSelect an option: ");
                int option = scanner.nextInt();
            
                if (option == 1) {
                    Login.loginAdmin(scanner);
                } else if (option == 2) {
                    return;
                } else {
                    System.out.println("Please enter a valid choice.\n");
                }
            }
            catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("\nPlease make a valid choice. \n");
            }
        }

        Login.admin(email, password, admin_id, scanner);

    }

    //udpate user information

    public static void updateInformationAdmin(Scanner scanner) {

        System.out.println("\n***********  Update User Information  ************\n");

        System.out.print("Enter the user's email: ");
        String email = scanner.next();
        System.out.print("Enter user's phone number: ");
        String phone = scanner.next();
    
        // Connect to the database
        Connection conn = db.getConnection();
    
        try (PreparedStatement selectStatement = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND phone = ?")) {
            selectStatement.setString(1, email);
            selectStatement.setString(2, phone);
            ResultSet result = selectStatement.executeQuery();
    
            if (result.next()) {
                
                // User with matching email and phone found
                String Uemail = result.getString("email");
                String name = result.getString("name");
                int age = result.getInt("age");

                System.out.println("\n---------------------");
                System.out.println("User email: " + Uemail);
                System.out.println("Name: " + name);
                System.out.println("Phone: " + phone);
                System.out.println("Age: " + age);
                System.out.println("---------------------\n");
    
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new age: ");
                int newAge = Integer.parseInt(scanner.nextLine());
    
                // Update the user's information
                updateUserInfo(conn, Uemail, newName, newAge);
    
                System.out.println("\nUSER INFORMATION UPDATED SUCCESSFULLY.\n");
                Login.admin(email, password, admin_id, scanner);
            } else {
                System.out.println("User with provided email and phone number not found.");
                Login.admin(email, password, admin_id, scanner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void updateUserInfo(Connection conn, String Uemail, String newName, int newAge) throws SQLException {
        String updateSQL = "UPDATE user SET name = ?, age = ? WHERE email = ?";
        try (PreparedStatement updateStatement = conn.prepareStatement(updateSQL)) {
            updateStatement.setString(1, newName);
            updateStatement.setInt(2, newAge);
            updateStatement.setString(3, Uemail);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update User Main informaton 

    private static void updateInformationUser(String email, String password, Scanner scanner) {
        System.out.println("\n***********  Update User Information  ************\n");
        Connection conn = db.getConnection();
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        System.out.println("3. Update Phone");
        System.out.println("4. Update Password");
        System.out.println("5. Go Back");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
    
        if (option == 5) {
            Login.user(email, password, scanner);
            return;
        } else if (option >= 1 && option <= 4) {
            scanner.nextLine(); // Consume the newline character left after reading the integer
    
            // Verify the password first
            System.out.print("Enter your current password: ");
            String currentPassword = scanner.nextLine();
            if (!verifyPassword(email, currentPassword)) {
                System.out.println("Incorrect password. Please enter the correct password.");
                Login.user(email, password, scanner);
                return;
            }
    
            // Read the new information
            System.out.print("Enter the new information: ");
            String newInfo = scanner.nextLine();
    
            // Update the user table
            String updateQuery = null;
            switch (option) {
                case 1:
                    updateQuery = "UPDATE user SET name = ? WHERE email = ?";
                    break;
                case 2:
                    updateQuery = "UPDATE user SET age = ? WHERE email = ?";
                    break;
                case 3:
                    updateQuery = "UPDATE user SET phone = ? WHERE email = ?";
                    break;
                case 4:
                    updateQuery = "UPDATE user SET password = ? WHERE email = ?";
                    break;
            }
    
            try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newInfo);
                updateStatement.setString(2, email);
                int updatedRows = updateStatement.executeUpdate();
    
                if (updatedRows > 0) {
                    System.out.println("\nUser information updated successfully.\n");
                } else {
                    System.out.println("\nUser information not updated. Please check your input.\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            Login.user(email, password, scanner);
        }
    }
    
    private static boolean verifyPassword(String email, String password) {
        Connection conn = db.getConnection();
        String query = "SELECT email FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // Delete account 

    private static void delete(String email, String password, Scanner scanner) {
        System.out.println("\n***********  Account Deletion  ************\n");
    
        // Ask for confirmation
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        String confirmation = scanner.next().toLowerCase();
    
        if (confirmation.equals("yes")) {
            // Ask for password
            System.out.print("Enter your password to confirm: ");
            String enteredPassword = scanner.next();
    
            if (verifyPassword(email, enteredPassword)) {
                // Check if the user has any tickets in user_flights
                if (hasBookedFlights(email)) {
                    System.out.println("You have booked flights. Please cancel your tickets before deleting your account.");
                    Login.user(email, password, scanner);
                    return;
                }
    
                // Delete the user from the user table
                if (deleteUser(email)) {
                    System.out.println("Account deleted successfully.");
                } else {
                    System.out.println("Account deletion failed. Please try again.");
                }
            } else {
                System.out.println("Incorrect password. Please enter the correct password.");
                Login.user(email, password, scanner);
            }
        } else if (confirmation.equals("no")) {
            System.out.println("Account deletion canceled.");
            Login.user(email, password, scanner);
        }
    }
    
    private static boolean hasBookedFlights(String email) {
        // Check if the user has booked flights in the user_flights table
        Connection conn = db.getConnection();
        String query = "SELECT COUNT(*) FROM user_flights WHERE user_email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static boolean deleteUser(String email) {
        // Delete the user from the user table
        Connection conn = db.getConnection();
        String query = "DELETE FROM user WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            int deletedRows = statement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Show Booked user flights

   private static void showUserFlights(String email, String password, Scanner scanner) {
    System.out.println("\n***********  YOUR BOOKED FLIGHTS  ************\n");

    // Display the booked flights for the user
    Connection conn = db.getConnection();
    String query = "SELECT uf.flight_ID, uf.num_tickets, f.FromLocation, f.ToLocation, f.FlightDate, f.FlightTime " +
                   "FROM user_flights uf " +
                   "JOIN flights f ON uf.flight_ID = f.flight_ID " +
                   "WHERE uf.user_email = ?";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setString(1, email);
        ResultSet result = statement.executeQuery();

        System.out.format("%-10s%-12s%-20s%-20s%-12s%-12s%n", "Flight ID", "Num Tickets", "From Location", "To Location", "Flight Date", "Flight Time");
        System.out.println("+-----------------------------------------------------------------------+");

        while (result.next()) {
            int flightID = result.getInt("flight_ID");
            int numTickets = result.getInt("num_tickets");
            String fromLocation = result.getString("FromLocation");
            String toLocation = result.getString("ToLocation");
            String flightDate = result.getString("FlightDate");
            String flightTime = result.getString("FlightTime");

            System.out.format("%-10d%-12d%-20s%-20s%-12s%-12s%n", flightID, numTickets, fromLocation, toLocation, flightDate, flightTime);
        }

        System.out.println("\n1. GO back. ");
        System.out.println("2. Logout. ");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    Login.user(email, password, scanner);;
                    break;
                case 2:
                    User.main(null);
                    break;  
                default:
                    System.out.println("Please enter a valid choice: ");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    

    //******************************************************/
    //.....................WORKING USER.......................
    //******************************************************/

    public static void user(String email, String password, Scanner scanner){

        // Display booking options
        System.out.println();
        System.out.println("1. Show flights. ");
        System.out.println("2. Book a ticket. ");
        System.out.println("3. Cancel a ticket. ");
        System.out.println("4. Update information. ");
        System.out.println("5. Show booked flights. ");
        System.out.println("6. Delete Account. ");
        System.out.println("7. Logout. ");
        System.out.println();
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        
        switch (option) {
            case 1:
                Flights.scheduleUser(scanner);
                break;
                //DONE...........
            case 2:
                Booking.bookFlight(email, password, scanner);
                break;
                //DONE...........
            case 3:
                Flights.cancel(email, password, scanner);
                break;
                //DONE............
            case 4: 
                Login.updateInformationUser(email, password, scanner);
                break;
                //DONE............
            case 5: 
                Login.showUserFlights(email, password, scanner);
                break;
            case 6:
                Login.delete(email, password, scanner);
                break;
                //DONE............
            case 7: 
                User.main(null);
                break;
            default:
                break;
        }
    }

    //******************************************************/
    //....................DONE ADMIN.......................
    //******************************************************/

    public static void admin(String email, String password, int admin_id, Scanner scanner){
        
        //Display admin option
        System.out.println("1. Show Flights. ");
        System.out.println("2. Update FLights. ");
        System.out.println("3. Create a User. ");
        System.out.println("4. Book a flight for User. ");
        System.out.println("5. Show booked flights. ");
        System.out.println("6. Update User information. ");
        System.out.println("7. Logout. ");
        System.out.println();
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        
        while(true){
            switch (option) {
            case 1:
                Flights.scheduleAdmin(scanner);
                break;
                //DONE.............

            case 2:
                Flights.update(scanner);
                break;
                //DONE.............

            case 3:
                Register.registerUser(scanner);
                break;
                //DONE...........

            case 4:
                Booking.admin(scanner);
                break;
                //DONE..........

            case 5:
               Flights.showAdmin();
                break;
                //DONE...........
                
            case 6: 
                Login.updateInformationAdmin(scanner);
                break;
               //DONE............

            case 7: 
                User.main(null);
                break;  
                //DONE...........
            
                default:
                break;
            }
        }
    }

}
