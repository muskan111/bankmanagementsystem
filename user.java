package projectjava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class user {

	 private Connection connection;
	    private Scanner scanner;

	    public user(Connection connection, Scanner scanner){
	        this.connection = connection;
	        this.scanner = scanner;
	    }
	    private boolean isValidEmail(String email) {
	        String emailRegex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}";
	        Pattern pattern = Pattern.compile(emailRegex);
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	    }

	    public void register(){
	      
	        System.out.print("Full Name:");
	        scanner.nextLine();
	        String full_name=scanner.nextLine();
	       
	        System.out.print("Email:");
	        
	        String email = scanner.nextLine();
	        if (!isValidEmail(email)) {
	            System.out.println("Invalid email format. Please enter a valid email.");
	            return;
	        }

	        System.out.print("Password:");
	        String password = scanner.nextLine();
	        scanner.nextLine();
	        if(user_exist(email)) {
	            System.out.println("User Already Exists for this Email Address!!");
	            return;
	        }
	        String register_query = "InSERT INTO user(Name, Email, pass) VALUES(?, ?, ?)";
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
	            preparedStatement.setString(1, full_name);
	            preparedStatement.setString(2, email);
	            preparedStatement.setString(3, password);
	            int affectedRows = preparedStatement.executeUpdate();//ecuteUpdate method in JDBC is used to execute SQL statements that modify the database, such as INSERT,
	            //UPDATE, or DELETE statements
	            if (affectedRows > 0) {
	                System.out.println("Registration Successfull!");
	            } else {
	                System.out.println("Registration Failed!");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public String login(){
	       
	        System.out.print("Email: ");
	       System.out.println();
	       String email = scanner.next();
	    
	       
	        System.out.print("Password: ");
	        System.out.println();
	  
	        String password = scanner.next();
	        String login_query = "SELECT * FROM user WHERE email = ? AND pass = ?";
	        try{
	            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if(resultSet.next())
	            {
	                return email;
	            }
	            else{
	                return null;
	            }
	        }catch (SQLException e){
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public boolean user_exist(String email){
	        String query = "SELECT * FROM user WHERE email = ?";
	        try{
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setString(1, email);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
	                return true;
	            }
	            else{
	                return false;
	            }
	        }catch (SQLException e){
	            e.printStackTrace();
	        }
	        return false;
	    }
	}
			


//}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
