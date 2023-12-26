import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//,.............................PENDING...................................


public class Booking {

    private static int admin_id;


    //ADMIN BOOKING FLIGHT FOR USER. UPDATE AV_SEATS AND USER_FLIGHTS TABLE
    public static void admin(Scanner scanner) {


        System.out.println("\n***********  BOOK FLIGHT FOR USER  ************\n");
        // Connect to the database
        Connection conn = db.getConnection();
        
        // Showing flights:
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
        System.out.println();
        System.out.print("Enter User's email: ");
        String email = scanner.next();

    try (PreparedStatement selectStatement = conn.prepareStatement("SELECT email FROM user WHERE email = ?")) {
        selectStatement.setString(1, email);
        ResultSet userResult = selectStatement.executeQuery();
        if (userResult.next()) {
            String user_email = userResult.getString("email");

            // Get flight_ID and seats to book from the admin.
            System.out.print("Enter flight ID: ");
            int flight_ID = scanner.nextInt();

            // Check if the flight exists
            try (PreparedStatement checkFlightStatement = conn.prepareStatement("SELECT av_seats FROM flights WHERE flight_ID = ?")) {
                checkFlightStatement.setInt(1, flight_ID);
                ResultSet flightResult = checkFlightStatement.executeQuery();
                if (flightResult.next()) {
                    int avSeats = flightResult.getInt("av_seats");
                    System.out.print("Enter the number of seats to book: ");
                    int num_tickets = scanner.nextInt();

                    int newAvSeats = avSeats - num_tickets;

                    if (newAvSeats >= 0) {
                        // Insert a new record into user_flights table
                        try (PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO user_flights (user_email, flight_ID, num_tickets) VALUES (?, ?, ?)")) {
                            insertStatement.setString(1, user_email);
                            insertStatement.setInt(2, flight_ID);
                            insertStatement.setInt(3, num_tickets);
                            insertStatement.executeUpdate();
                        }

                        // Fetch flight details from flights table
                        try (PreparedStatement selectFlightStatement = conn.prepareStatement("SELECT FromLocation, ToLocation, FlightDate, FlightTime, price FROM flights WHERE flight_ID = ?")) {
                            selectFlightStatement.setInt(1, flight_ID);
                            ResultSet flightDetailsResult = selectFlightStatement.executeQuery();

                            if (flightDetailsResult.next()) {
                                String FromLocation = flightDetailsResult.getString("FromLocation");
                                String ToLocation = flightDetailsResult.getString("ToLocation");
                                String FlightDate = flightDetailsResult.getString("FlightDate");
                                String FlightTime = flightDetailsResult.getString("FlightTime");
                                int price = flightDetailsResult.getInt("price");

                                // Update the corresponding fields in the user_flights table
                                try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE user_flights SET FromLocation = ?, ToLocation = ?, FlightDate = ?, FlightTime = ?, price = ? WHERE user_email = ? AND flight_ID = ?")) {
                                    updateStatement.setString(1, FromLocation);
                                    updateStatement.setString(2, ToLocation);
                                    updateStatement.setString(3, FlightDate);
                                    updateStatement.setString(4, FlightTime);
                                    updateStatement.setInt(5, price);
                                    updateStatement.setString(6, user_email);
                                    updateStatement.setInt(7, flight_ID);

                                    int updatedRows = updateStatement.executeUpdate();
                                    if (updatedRows > 0) {
                                        System.out.println("User_flights updated successfully.");
                                        System.out.println("Booking successful!\n");
                                    } else {
                                        System.out.println("User_flights not updated. Check the user_email and flight_ID.");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        // Update av_seats in the flights table
                        try (PreparedStatement updateAvSeatsStatement = conn.prepareStatement("UPDATE flights SET av_seats = ? WHERE flight_ID = ?")) {
                            updateAvSeatsStatement.setInt(1, newAvSeats);
                            updateAvSeatsStatement.setInt(2, flight_ID);
                            updateAvSeatsStatement.executeUpdate();
                        }

                        Login.admin(email, user_email, newAvSeats, scanner);
                    } else {
                        System.out.println("Seats are more than available. Please try again.");
                        Login.admin(email, user_email, newAvSeats, scanner);
                    }
                } else {
                    System.out.println("Flight not found. Please check the flight_ID.");
                    Login.admin(email, user_email, flight_ID, scanner);
                }
            }
        } else {
            System.out.println("User not found. Please check the email.");
            Login.admin(email, email, admin_id, scanner);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
   //*******BOOKING OPTION FOR USER***********/
    public static void bookFlight(String email, String password, Scanner scanner) {
        System.out.println("\n***********  Welcome to BOOKING SECTION  ************\n");
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

        while (true) {
            System.out.println();
            System.out.print("Enter flight ID: ");
            int flight_ID = scanner.nextInt();

            // Check if the flight exists
            try {
                try (PreparedStatement checkFlightStatement = conn.prepareStatement("SELECT av_seats, price FROM flights WHERE flight_ID = ?")) {
                    checkFlightStatement.setInt(1, flight_ID);
                    ResultSet flightResult = checkFlightStatement.executeQuery();
                    if (flightResult.next()) {
                        int avSeats = flightResult.getInt("av_seats");
                        int pricing = flightResult.getInt("price");
                        System.out.print("Enter the number of seats to book: ");
                        int num_tickets = scanner.nextInt();

                        int totalCost = num_tickets * pricing;

                        while (true) {
                            System.out.printf("Total cost: $%d%n", totalCost);
                            System.out.print("Confirm booking (y/n): ");
                            String confirm = scanner.next().trim().toLowerCase();

                            if (confirm.equals("y")) {
                                break;
                            } else if (confirm.equals("n")) {
                                System.out.println("Booking canceled. ");
                                Login.user(email, password, scanner);
                                return; // Exit the method to avoid duplicate entry exception
                            } else {
                                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                                // Optionally, you can add a loop or take other actions to handle the invalid input.
                            }
                        }

                        // Check if the user has already booked tickets for the same flight
                        try (PreparedStatement checkExistingBooking = conn.prepareStatement("SELECT * FROM user_flights WHERE user_email = ? AND flight_ID = ?")) {
                            checkExistingBooking.setString(1, email);
                            checkExistingBooking.setInt(2, flight_ID);
                            ResultSet existingBookingResult = checkExistingBooking.executeQuery();

                            if (existingBookingResult.next()) {
                                // User has already booked tickets for this flight, update the number of tickets
                                int existingNumTickets = existingBookingResult.getInt("num_tickets");
                                int newNumTickets = existingNumTickets + num_tickets;

                                try (PreparedStatement updateNumTickets = conn.prepareStatement("UPDATE user_flights SET num_tickets = ? WHERE user_email = ? AND flight_ID = ?")) {
                                    updateNumTickets.setInt(1, newNumTickets);
                                    updateNumTickets.setString(2, email);
                                    updateNumTickets.setInt(3, flight_ID);
                                    updateNumTickets.executeUpdate();
                                }

                                // Update available seats in the flights table
                                int newAvSeats = avSeats - num_tickets;
                                try (PreparedStatement updateAvSeatsStatement = conn.prepareStatement("UPDATE flights SET av_seats = ? WHERE flight_ID = ?")) {
                                    updateAvSeatsStatement.setInt(1, newAvSeats);
                                    updateAvSeatsStatement.setInt(2, flight_ID);
                                    updateAvSeatsStatement.executeUpdate();
                                }

                                // Rest of your code...
                                // ... (Fetching flight details, updating av_seats, etc.)

                                System.out.println("User_flights updated successfully.");
                                System.out.println("Booking successful!\n");

                                Login.user(email, password, scanner);
                                return;  // Exit the method to avoid duplicate entry exception
                            }
                        }

                        // If the user has not booked tickets for this flight, proceed with the regular booking logic

                        // Insert a new record into user_flights table
                        try (PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO user_flights (user_email, flight_ID, num_tickets) VALUES (?, ?, ?)")) {
                            insertStatement.setString(1, email);
                            insertStatement.setInt(2, flight_ID);
                            insertStatement.setInt(3, num_tickets);
                            insertStatement.executeUpdate();
                        }

                        // Update available seats in the flights table
                        int newAvSeats = avSeats - num_tickets;
                        try (PreparedStatement updateAvSeatsStatement = conn.prepareStatement("UPDATE flights SET av_seats = ? WHERE flight_ID = ?")) {
                            updateAvSeatsStatement.setInt(1, newAvSeats);
                            updateAvSeatsStatement.setInt(2, flight_ID);
                            updateAvSeatsStatement.executeUpdate();
                        }

                        // Rest of your code...
                        // ... (Fetching flight details, updating av_seats, etc.)

                        System.out.println("\n.....BOOKING SUCCESSFUL.....\n");
                        Login.user(email, password, scanner);
                    } else {
                        System.out.println("Flight not found. Please check the flight_ID.");
                        Login.user(email, email, scanner);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }   
}

    
