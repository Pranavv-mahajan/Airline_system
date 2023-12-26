import java.util.*;

//,.............................WORK DONE...................................


public class User{
    public static void main(String[] args){
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        while(true){
                System.out.println("\n*********   WELCOME TO INTERFACE   *********\n");
                System.out.println("1. Register User.");
                System.out.println("2. Register Admin.");
                System.out.println("-------------------------");
                System.out.println("3. Login User. ");
                System.out.println("4. Login Admin. ");
                System.out.println("-------------------------");
                System.out.println("5. Show scheduled flights. ");
                System.out.println("6. Exit Program. ");
                System.out.print("\nSelect an option: ");
                choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        Register.registerUser(scanner);
                        break;
                    case 2:
                        Register.registerAdmin(scanner);
                        break;
                    case 3:
                        Login.loginUser(scanner);
                        break;
                    case 4:
                        Login.loginAdmin(scanner);
                        break;
                    case 5:
                        //Flights flights = new Flights(scanner);
                        Flights.schedule(scanner);
                        break;
                    case 6:
                        System.out.println("\n Exiting app...THANKYOU!\n ");
                        System.exit(0);
                
                    default:
                        System.out.println("\nEnter a valid choice...");
                        break;
                }
    
            }
        

    }
}