import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//,.............................WORK DONE...................................


public class Flights {
    
    private static int flight_ID;
    private static String email;
    private static String password;

    //1. Showing flights
    public static void schedule(Scanner scanner) {

        System.out.println("\n***********  FLIGHT SCHEDULE  ************\n");

        Connection conn = db.getConnection();

        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM flights")) {
            ResultSet result = statement.executeQuery();

            // Print the table header
            System.out.format("%-10s%-20s%-20s%-12s%-12s%-10s%-10s%n",
                    "flight_ID", "FromLocation", "ToLocation", "FlightDate", "FlightTime", "av_seats", "price");
            System.out.println("+------------------------------------------------------------------------------------------+");

            // Print the flight data
            while (result.next()) {
                int flight_ID = result.getInt("flight_ID");
                String fromLocation = result.getString("FromLocation");
                String toLocation = result.getString("ToLocation");
                String flightDate = result.getString("FlightDate");
                String flightTime = result.getString("FlightTime");
                int avSeats = result.getInt("av_seats");
                int price = result.getInt("price");

                System.out.format("%-10d%-20s%-20s%-12s%-12s%-10d%-10d%n",
                        flight_ID, fromLocation, toLocation, flightDate, flightTime, avSeats, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

       // if()
        System.out.println("\n1. Go Back.");
        System.out.println("2. EXIT.");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        
        while (true) {
            switch (choice) {
                case 1:
                    return;
                case 2:
                    System.exit(0);;  // This will exit the current method, not necessarily the entire program.
                default:
                    System.out.println("Choose a valid option: ");
                    break;
            }
        
            System.out.println("\n1. Go Back.");
            System.out.println("2. EXIT.");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
        }
    }

    public static void scheduleAdmin(Scanner scanner) {

        System.out.println("\n***********  FLIGHT SCHEDULE  ************\n");

        Connection conn = db.getConnection();

        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM flights")) {
            ResultSet result = statement.executeQuery();

            // Print the table header
            System.out.format("%-10s%-20s%-20s%-12s%-12s%-10s%-10s%n",
                    "flight_ID", "FromLocation", "ToLocation", "FlightDate", "FlightTime", "av_seats", "price");
            System.out.println("+------------------------------------------------------------------------------------------+");

            // Print the flight data
            while (result.next()) {
                int flight_ID = result.getInt("flight_ID");
                String fromLocation = result.getString("FromLocation");
                String toLocation = result.getString("ToLocation");
                String flightDate = result.getString("FlightDate");
                String flightTime = result.getString("FlightTime");
                int avSeats = result.getInt("av_seats");
                int price = result.getInt("price");

                System.out.format("%-10d%-20s%-20s%-12s%-12s%-10d%-10d%n",
                        flight_ID, fromLocation, toLocation, flightDate, flightTime, avSeats, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // if()
        System.out.println("\n1. Go Back.");
        System.out.println("2. Go to Menu.");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        
        while (true) {
            switch (choice) {
                case 1:
                    Login.admin(email, password, choice, scanner);
                    break;
                case 2:
                    User.main(null);
                    break;  
                default:
                    System.out.println("Choose a valid option: ");
                    break;
            }
        
            System.out.println("\n1. Go Back.");
            System.out.println("2. Go to Menu.");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
        }
    }

    public static void scheduleUser(Scanner scanner) {

        System.out.println("\n***********  FLIGHT SCHEDULE  ************\n");

        Connection conn = db.getConnection();

        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM flights")) {
            ResultSet result = statement.executeQuery();

            // Print the table header
            System.out.format("%-10s%-20s%-20s%-12s%-12s%-10s%-10s%n",
                    "flight_ID", "FromLocation", "ToLocation", "FlightDate", "FlightTime", "av_seats", "price");
            System.out.println("+------------------------------------------------------------------------------------------+");

            // Print the flight data
            while (result.next()) {
                int flight_ID = result.getInt("flight_ID");
                String fromLocation = result.getString("FromLocation");
                String toLocation = result.getString("ToLocation");
                String flightDate = result.getString("FlightDate");
                String flightTime = result.getString("FlightTime");
                int avSeats = result.getInt("av_seats");
                int price = result.getInt("price");

                System.out.format("%-10d%-20s%-20s%-12s%-12s%-10d%-10d%n",
                        flight_ID, fromLocation, toLocation, flightDate, flightTime, avSeats, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

       // if()
       System.out.println("\n1. Go Back.");
       System.out.println("2. Go to Menu.");
       System.out.print("Select an option: ");
       int choice = scanner.nextInt();
       
       while (true) {
           switch (choice) {
               case 1:
                   Login.user(email, password, scanner);
                   break;
               case 2:
                    User.main(null);
                    break;  
               default:
                   System.out.println("Choose a valid option: ");
                   break;
           }
       
           System.out.println("\n\n1. Go Back.");
           System.out.println("2. Go to Menu.");
           System.out.print("Select an option: ");
           choice = scanner.nextInt();
       }
    }

    //2. update flights
    public static void update(Scanner scanner) {
        System.out.println("\n***********  MODIFY FLIGHT SCHEDULE  ************\n");
    
        Connection conn = db.getConnection();
        
        try {
            // Display the current flight information
            displayFlightInformation(conn, flight_ID);
    
            System.out.print("Enter flight ID to modify: ");
            flight_ID = scanner.nextInt();
            
            // Check if the flight with the entered flight_ID exists
            if (flightExists(conn, flight_ID)) {
                // Prompt the admin to edit the flight information
                editFlightInformation(conn, scanner, flight_ID);
            } else {
                System.out.println("Flight does not exist. Recheck the flight ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //2.1 display selected flight
    private static void displayFlightInformation(Connection conn, int flight_ID) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM flights WHERE flight_ID = ?")) {
            statement.setInt(1, flight_ID);
            ResultSet result = statement.executeQuery();
    
            // Print the flight information
            while (result.next()) {
                int id = result.getInt("flight_ID");
                String fromLocation = result.getString("FromLocation");
                String toLocation = result.getString("ToLocation");
                String flightDate = result.getString("FlightDate");
                String flightTime = result.getString("FlightTime");
                int avSeats = result.getInt("av_seats");
                int price = result.getInt("price");
    
                System.out.println("\nCurrent Flight Information:");
                System.out.format("flight_ID: %d\nFromLocation: %s\nToLocation: %s\nFlightDate: %s\nFlightTime: %s\nav_seats: %d\nprice: %d%n",
                        id, fromLocation, toLocation, flightDate, flightTime, avSeats, price);
            }
        }
    }
    
    //2.2 editFLights
    private static void editFlightInformation(Connection conn, Scanner scanner, int flight_ID) throws SQLException {
        while (true) {
            System.out.println("***** SELECT MODIFICATION ****** \n");
            System.out.println("1. flight_ID ");
            System.out.println("2. Beginning ");
            System.out.println("3. Destination ");
            System.out.println("4. Date of Departure ");
            System.out.println("5. Time ");
            System.out.println("6. seats available ");
            System.out.println("7. Price ");
            System.out.println("8. Exit ");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
    
            if (choice == 8) {
                // Exit and go back to the main menu
                User.main(null);
                break;  
            }
    
            switch (choice) {
                case 1:
                    // Modify flight_ID
                    System.out.print("Enter new flight_ID: ");
                    int newFlightID = scanner.nextInt();
                    updateFlightField(conn, flight_ID, "flight_ID", newFlightID);
                    break;
                case 2:
                    // Modify Beginning
                    System.out.print("Enter new beginning location: ");
                    String newFromLocation = scanner.next();
                    updateFlightField(conn, flight_ID, "FromLocation", newFromLocation);
                    break;
                case 3:
                    // Modify Destination
                    System.out.print("Enter new destination location: ");
                    String newToLocation = scanner.next();
                    updateFlightField(conn, flight_ID, "ToLocation", newToLocation);
                    break;
                case 4:
                    // Modify Date
                    System.out.print("Enter updated Date (YYYY-MM-DD): ");
                    String newFlightDateStr = scanner.next();
    
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = dateFormat.parse(newFlightDateStr);
                        java.sql.Date newFlightDate = new java.sql.Date(parsedDate.getTime());
                        updateFlightField(conn, flight_ID, "FlightDate", newFlightDate);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                    break;
                case 5:
                    // Update Time
                    System.out.print("Update time (HH:mm:ss): ");
                    String newTime = scanner.next();
                    updateFlightField(conn, flight_ID, "FlightTime", newTime);
                    break;
                case 6:
                    // Update Seats Available
                    System.out.print("Seats available: ");
                    int seats = scanner.nextInt();
                    if (seats <= 20) {
                        updateFlightField(conn, flight_ID, "av_seats", seats);
                    } else {
                        System.out.println("Invalid number of seats. Maximum allowed is 20.");
                    }
                    break;
                case 7:
                    // Update Price
                    System.out.print("Update Price: ");
                    int price = scanner.nextInt();
                    updateFlightField(conn, flight_ID, "price", price);
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    break;
            }
        }
    }

    //2.3 check if flight exits
    private static boolean flightExists(Connection conn, int flight_ID) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT 1 FROM flights WHERE flight_ID = ?")) {
            statement.setInt(1, flight_ID);
            ResultSet result = statement.executeQuery();
            return result.next();
        }
    }
    
    //2.4 updation
    private static void updateFlightField(Connection conn, int flight_ID, String field, Object newValue) throws SQLException {
        // Construct the SQL update statement
        String updateSQL = "UPDATE flights SET " + field + " = ? WHERE flight_ID = ?";
        try (PreparedStatement statement = conn.prepareStatement(updateSQL)) {
            if (newValue instanceof String) {
                statement.setString(1, (String) newValue);
            } else if (newValue instanceof Integer) {
                statement.setInt(1, (Integer) newValue);
            }
            statement.setInt(2, flight_ID);
            statement.executeUpdate();
            System.out.println("Flight information updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update flight information.");
        }
    }
    

    //5. SHOW flights
    public static void showAdmin(){

        Connection conn = db.getConnection();
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_flights")) {
            ResultSet result = statement.executeQuery();
    
            // Print the table header
            System.out.format("%-40s%-10s%-12s%-20s%-20s%-12s%-12s%-10s%n",
                    "Customer", "flight_ID", "tickets", "Departure", "Landing", "Date", "Time", "price");
            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------+");
    
            // Print the user_flights data
            while (result.next()) {
                String user_email = result.getString("user_email");
                int flight_ID = result.getInt("flight_ID");
                int num_tickets = result.getInt("num_tickets");
                String FromLocation = result.getString("FromLocation");
                String ToLocation = result.getString("ToLocation");
                String FlightDate = result.getString("FlightDate");
                String FlightTime = result.getString("FlightTime");
                int price = result.getInt("price");
    
                System.out.format("%-40s%-10d%-12d%-20s%-20s%-12s%-12s%-10d%n",
                        user_email, flight_ID, num_tickets, FromLocation, ToLocation, FlightDate, FlightTime, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\n1. GO back. ");
        System.out.println("2. Logout. ");
        System.out.print("Select an option: ");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    Login.admin(email, password, option, scanner);
                    break;
                case 2:
                    User.main(null);
                    break;
                default:
                    System.out.println("Please enter a valid choice: ");
            }
        }
    }



    //************* USER *************


    //3. CANCEL FLIGHT
    public static void cancel(String email, String password, Scanner scanner) {
        System.out.println("\n***********  CANCEL FLIGHT  ************\n");
        Connection conn = db.getConnection();
    
        try {
            conn.setAutoCommit(false); // Begin a transaction
    
            System.out.println("Your booked flights:\n (press 0 if none)");
            showUserBookings(conn, email); // Show the flights that the user has booked
    
            System.out.print("Enter flight_ID to cancel: ");
            int flightToCancel = scanner.nextInt();
    
            if (!isFlightValid(conn, flightToCancel)) {
                System.out.println("Invalid flight_ID. Enter a valid choice.");
                return;
            }
    
            System.out.print("Are you sure you want to cancel this flight? (yes/no): ");
            String confirmation = scanner.next();
    
            if (confirmation.equalsIgnoreCase("yes")) {
                int numTicketsToCancel = getNumTicketsToCancel(conn, email, flightToCancel);
                if (numTicketsToCancel > 0) {
                    removeTicketsFromUserFlights(conn, email, flightToCancel);
                    updateAvSeats(conn, flightToCancel, numTicketsToCancel);
    
                    conn.commit(); // Commit the transaction
                    System.out.println("Flight canceled successfully!");
                    Login.user(email, password, scanner);
                } else {
                    System.out.println("You have not booked any tickets for this flight.");
                }
            } else {
                System.out.println("Flight cancellation canceled.");
                Login.user(email, password, scanner);
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Roll back the transaction in case of an error
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true); // Restore auto-commit mode
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
    }
    
    private static void showUserBookings(Connection conn, String email) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_flights WHERE user_email = ?")) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
    
            // Print the user's flight bookings
            while (result.next()) {
                int flight_ID = result.getInt("flight_ID");
                String fromLocation = result.getString("FromLocation");
                String toLocation = result.getString("ToLocation");
                String flightDate = result.getString("FlightDate");
                String flightTime = result.getString("FlightTime");
                int num_tickets = result.getInt("num_tickets");
                int price = result.getInt("price");
    
                System.out.println("Flight ID: " + flight_ID);
                System.out.println("From: " + fromLocation);
                System.out.println("To: " + toLocation);
                System.out.println("Date: " + flightDate);
                System.out.println("Time: " + flightTime);
                System.out.println("Number of Tickets: " + num_tickets);
                System.out.println("Price: " + price);
                System.out.println("-------------");
            }
        }
    }
    
    private static boolean isFlightValid(Connection conn, int flightToCancel) throws SQLException {
        try (PreparedStatement checkStatement = conn.prepareStatement("SELECT * FROM flights WHERE flight_ID = ?")) {
            checkStatement.setInt(1, flightToCancel);
            ResultSet checkResult = checkStatement.executeQuery();
            return checkResult.next();
        }
    }
    
    private static int getNumTicketsToCancel(Connection conn, String email, int flightToCancel) throws SQLException {
        try (PreparedStatement getNumTicketsStatement = conn.prepareStatement("SELECT num_tickets FROM user_flights WHERE flight_ID = ? AND user_email = ?")) {
            getNumTicketsStatement.setInt(1, flightToCancel);
            getNumTicketsStatement.setString(2, email);
            ResultSet numTicketsResult = getNumTicketsStatement.executeQuery();
            if (numTicketsResult.next()) {
                return numTicketsResult.getInt("num_tickets");
            }
            return 0;
        }
    }
    
    private static void removeTicketsFromUserFlights(Connection conn, String email, int flightToCancel) throws SQLException {
        try (PreparedStatement removeTicketsStatement = conn.prepareStatement("DELETE FROM user_flights WHERE flight_ID = ? AND user_email = ?")) {
            removeTicketsStatement.setInt(1, flightToCancel);
            removeTicketsStatement.setString(2, email);
            removeTicketsStatement.executeUpdate();
        }
    }
    
    private static void updateAvSeats(Connection conn, int flightToCancel, int numTicketsToCancel) throws SQLException {
        try (PreparedStatement updateAvSeatsStatement = conn.prepareStatement("UPDATE flights SET av_seats = av_seats + ? WHERE flight_ID = ?")) {
            updateAvSeatsStatement.setInt(1, numTicketsToCancel);
            updateAvSeatsStatement.setInt(2, flightToCancel);
            updateAvSeatsStatement.executeUpdate();
        }
    }
    
    
    //5. SHOW flights

    public static void showUser(){

        Connection conn = db.getConnection();
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_flights")) {
            ResultSet result = statement.executeQuery();
    
            // Print the table header
            System.out.format("%-40s%-10s%-12s%-20s%-20s%-12s%-12s%-10s%n",
                    "Customer", "flight_ID", "tickets", "Departure", "Landing", "Date", "Time", "price");
            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------+");
    
            // Print the user_flights data
            while (result.next()) {
                String user_email = result.getString("user_email");
                int flight_ID = result.getInt("flight_ID");
                int num_tickets = result.getInt("num_tickets");
                String FromLocation = result.getString("FromLocation");
                String ToLocation = result.getString("ToLocation");
                String FlightDate = result.getString("FlightDate");
                String FlightTime = result.getString("FlightTime");
                int price = result.getInt("price");
    
                System.out.format("%-40s%-10d%-12d%-20s%-20s%-12s%-12s%-10d%n",
                        user_email, flight_ID, num_tickets, FromLocation, ToLocation, FlightDate, FlightTime, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\n1. GO back. ");
        System.out.println("2. Logout. ");
        System.out.print("Select an option: ");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        while (true) {
            switch (option) {
                case 1:
                    Login.user(email, password, scanner);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Please enter a valid choice: ");
            }
        }
    }


}
    
    


