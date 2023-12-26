import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

//,.............................WORK DONE...................................


public class Register {
    private static String password;
    private static String name;
    private static String email;
    private static String phone;
    private static int age;
    private static int admin_id;

    //USER SECTION
    public static void registerUser(Scanner scanner) {

        System.out.println("\n***********  Welcome to User Registration  ************\n");
            
            //Name
            System.out.print("Enter name: ");
            String name = scanner.next();
            scanner.nextLine();

            //email
            while(true){
            System.out.print("Enter Email : ");
            email = scanner.next();
            scanner.nextLine();
            if(!email.contains("@gmail.com")){
                System.out.println("\nInvalid email. Please re enter email\n");
            }
            else{
                break;
            }
        }

            //password
            while (true) {
                System.out.print("Enter password: ");
                password = scanner.next();
                System.out.print("Re-confirm password: ");
                String re_p = scanner.next();
                if (!re_p.equals(password)) {
                    System.out.println("Password does not match. Re-enter");
                } else {
                    break;
                }
            }
            
            //age
            while (true) {
                System.out.print("Enter age: ");
                try {
                    age = scanner.nextInt();
                    if (age > 0 && age <= 120) {
                        break;
                    } else {
                        System.out.println("Please enter a valid age.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Enter a real and valid number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            //phone no
            while (true) {
            System.out.print("Enter phone no: ");
            phone = scanner.next();
        
            if (phone.length() == 10) {
                try {
                    long phoneNum = Long.parseLong(phone);
                    if (phoneNum >= 6000000000L && phoneNum <= 9999999999L) {
                        // Valid phone number, exit the loop
                        break;
                    } else {
                        System.out.println("Enter a valid phone number.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid phone number (numeric characters only).\n");
                }
            } else {
                System.out.println("Enter a valid phone number with exactly 10 digits.\n");
            }
        }

            //form connection
            Connection conn = db.getConnection();
            
            //check user from database
            try (PreparedStatement statement = conn.prepareStatement("SELECT email FROM user WHERE email = ?")) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("\n\n# User Already exists. Use another Email. # \n");
                    System.out.println("\n1. Go Back.");
                    System.out.println("2. Go to Menu.");
                    System.out.print("Select an option: ");
                    int choice = scanner.nextInt();
                    
                    while (true) {
                        switch (choice) {
                            case 1:
                                Register.registerUser(scanner);
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
            } 
            catch (Exception e) {
                 e.printStackTrace();
            }

            //enter data into database
            try(PreparedStatement statement = conn.prepareStatement("INSERT INTO user(name , email, password, phone, age) VALUES(?,?,?,?,?)")){
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, phone);
                statement.setInt(5, age);

                int rowInserted = statement.executeUpdate();
                if(rowInserted > 0){
                    System.out.println("\nUser Created...");      
                }
                else{
                    System.out.println("\nError! User not created.");
                }

            }
            catch(SQLException e){
                e.printStackTrace();
            }
        
    }

    

    //ADMIN SECTION
    public static void registerAdmin(Scanner scanner){
        
        System.out.println("\n***********  Welcome to Admin Registration  ************\n");

        //admin_id
        while (true) {
            System.out.print("Enter ADMIN ID: ");
            admin_id = scanner.nextInt();
            
            if (admin_id >= 1000 && admin_id <= 9999) {
                // Valid 4-digit ID, exit the loop
                break;
            } else {
                System.out.println("Please enter a valid 4-digit ID.");
            }
        }
        
        //name
        System.out.print("Enter Name : ");
        name = scanner.next();
        
        //email
        while(true){
            System.out.print("Enter Email : ");
            email = scanner.next();
            if(!email.contains("@gmail.com")){
                System.out.println("\nInvalid email. Please re enter email\n");
            }
            else{
                break;
            }


        }

        //password
        while(true){
            System.out.print("Enter Password : ");
            password = scanner.next();
            System.out.print("Re-confirm Password : ");
            String re_p = scanner.next();
            
            if(!re_p.equals(password)){
                System.out.println("Password does not match. Re-enter");
            }
            else{
                break;
            }
        }

        //phone no
        while (true) {
            System.out.print("Enter phone no: ");
            phone = scanner.next();
        
            if (phone.length() == 10) {
                try {
                    long phoneNum = Long.parseLong(phone);
                    if (phoneNum >= 6000000000L && phoneNum <= 9999999999L) {
                        // Valid phone number, exit the loop
                        break;
                    } else {
                        System.out.println("Enter a valid phone number.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid phone number (numeric characters only).\n");
                }
            } else {
                System.out.println("Enter a valid phone number with exactly 10 digits.\n");
            }
        }
        

        Connection conn = db.getConnection();

        //If email exists

        try (PreparedStatement statement = conn.prepareStatement("SELECT email from admin where email = ?")){
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                System.out.println("\n\n# User Already exists. Use another Email. #\n ");
                Register.registerAdmin(scanner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Entering data into admin table
        try(PreparedStatement statement = conn.prepareStatement("INSERT INTO admin(admin_id, name, email, password, phone) VALUES(?,?,?,?,?)")){
            statement.setInt(1, admin_id);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, phone);
            
            int rowInserted = statement.executeUpdate();
                if(rowInserted > 0){
                    System.out.println("\nAdmin Created...\n");      
                }
                else{
                    System.out.println("\nError! Admin not created.");
                }

        } catch(SQLException e){
            e.printStackTrace();
        }        

    }
}