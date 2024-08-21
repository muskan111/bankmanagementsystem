package projectjava;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
public class jdbc {

	 private static final String URL = "jdbc:mysql://localhost:3306/bankmanagmentsystem";
	    private static final String USER = "root";
	    private static final String PASSWORD = "muskan";

	    // JDBC variables for opening, closing and managing connection
	    private static Connection connection;
	    private static Statement statement;
	    private static ResultSet resultSet;

	    public static void main(String[] args) {
	    
	    
	    try{
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner scanner =  new Scanner(System.in);
            user user = new user(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            account_manager accountManager = new account_manager(connection, scanner);

            String email;
            long account_number;

            while(true){
                System.out.println("******* WELCOME TO BANKING SYSTEM *******");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");
                int choice1 = scanner.nextInt();
                switch (choice1){
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.login();
                        if(email!=null)
                        {
                        	
	                            System.out.println();
	                            System.out.println("User Logged In!");
	                            if(!accounts.account_exist(email))
	                            {
	                            
	                                System.out.println();
	                                System.out.println("1. Open a new Bank Account");
	                                System.out.println("2. Exit");
	                                if(scanner.nextInt() == 1) {
	                                    account_number = accounts.open_account(email);
	                                    System.out.println("Account Created Successfully");
	                                    System.out.println("Your Account Number is: " + account_number);
	                                }else{
	                                    break;
	                                }
	
	                            }
                            account_number = accounts.getAccount_number(email);
                            int choice2 = 0;
                            while (choice2!=5){
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your choice: ");
                                choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.getBalance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter Valid Choice!");
                                        break;
                                }
                            }

//account code

                        }
                        else{
                            System.out.println("Incorrect Email or Password!");
                        }
                            break;
                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }}
        catch (SQLException e){
            e.printStackTrace();
        }//printStackTrace() is a method in Java that belongs to the Throwable class, which is the superclass of all exceptions and errors in Java. This method is used to print the stack trace of the throwable object (such as an exception or error) to the standard error output.
    }

}