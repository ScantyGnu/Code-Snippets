import java.util.Scanner;

public class SignIn{
	public static void main(String [] args){
		Scanner User = new Scanner(System.in);
		
		boolean sentinel = true;
		boolean digit = true;
		boolean upper = true;
		boolean lower = true;
		boolean special = true;
		boolean correct = false;
		int i = 0;
		
		System.out.println("Welcome to spotify!");
		System.out.println("Please provide us with your email and password");
		System.out.print("Email >");
		String email = User.nextLine();
		
		while (!(email.contains("@"))){
			System.out.println("That is not a valid email address");
			System.out.print("Email >");
			email = User.nextLine();
		}
		
		System.out.print("Password >");
		String pass = User.nextLine();
		
		char letter;
		
		while (!(correct)){
			if ((!(pass.length() > 8)) && (!(pass.length() < 21)))
				sentinel = false;
			if (sentinel)
				for (i = 0; i > pass.length() - 1; i++){
					letter = pass.charAt(i);
					if (Character.isDigit(letter))
						digit = false;
					if (Character.isUpperCase(letter))
						upper = false;
					if (Character.isLowerCase(letter))
						lower = false;
					if ((letter == '!') || (letter == '#') || (letter == '$') || (letter == '*'))
						special = false;
				}
			if ((special) && (lower) && (upper) && (digit) && (sentinel))
				correct = true;
			if (!(correct)){
				System.out.println("That is not a valid password");
				System.out.println("Remember it must follow these criteria:");
				System.out.println("1. Contains at least 8 characters and at most 20 characters");
				System.out.println("2. Contains at least one upper-case letter");
				System.out.println("3. Contains at least one lower-case letter letter");
				System.out.println("4. Contains at least one digit");
				System.out.println("5. It contains at least one of the following special characters: [!, #, $, *]");
				System.out.print("Password >");
				pass = User.nextLine();
			}
		}
		//Signing in
		String emailSign;
		String passSign;
		boolean sign = false;
		
		System.out.println("You have succesfully created an account!");
		System.out.println("Please sign in");
		System.out.print("Email >");
		emailSign = User.nextLine();
		System.out.print("Password >");
		passSign = User.nextLine();
		
		while (!sign)
			if ((!emailSign.equals(email)) || (!passSign.equals(pass))){
				System.out.println("Either your password or your email are wrong");
				System.out.println("Try again");
				System.out.print("Email >");
				emailSign = User.nextLine();
				System.out.print("Password >");
				passSign = User.nextLine();
			}
			else
				sign = true;
		
		//Check for miners.utep.edu
		float month;
		String status;
		
		if (email.contains("miners.utep.edu")){
			month = 4.99f;
			status = "You qualify for our discount plan!";
		}
		else{
			month = 9.99f;
			status = "Sorry, you do not qualify for our discount plan";
		}
		
		float total = month + (month * 0.0725f);
		
		System.out.println("Welcome!");
		System.out.println(status);
		System.out.printf("Your total is: %.2f" total);
		
	}
}
		