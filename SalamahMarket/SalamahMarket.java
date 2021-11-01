import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SalamahMarket{
	public static void main(String [] args) throws FileNotFoundException{
		
		//Create Scanner/File
		Scanner user = new Scanner(System.in);
		File textFile = new File("database.txt");
		Scanner inventory = new Scanner(textFile);
		
		//Variables
		boolean exit = false;
		char choiceMenu;
		String choiceFruit;
		int i;
		boolean match = false;
		int numItems = 0;
		float price = 0;
		float totalWeight = 0;
		float totalPrice = 0;
		float numPounds = 0;
		char sure;
		double numBagsNeeded;
		double numBagsUser;
		double numBagsBought = 0;
		double cartPrice;
		float giftCardMoney;
		double giftCardRemaining;
		String fruit = "";
		
		//Main Menu loop
		while (!exit){
			
			//Display Menu
			System.out.println("Please select an option from the following menu");
			System.out.println("1. Add item");
			System.out.println("2. View cart");
			System.out.println("3. Clear cart");
			System.out.println("4. Check out");
			System.out.println("5. Exit");
			System.out.print(">");
			
			user = new Scanner(System.in);
			choiceMenu = user.nextLine().charAt(0);
			System.out.println("");
			//Execute the chosen option
			switch (choiceMenu){
				//Add item
				case '1':
					//Show inventory
					inventory = new Scanner(textFile);
					while (inventory.hasNextLine()){
						System.out.println(inventory.nextLine());
					}
					//Do this until we have found the fruit
					inventory = new Scanner(textFile);
					match = false;
					while (!match){
						System.out.println("Select a fruit to add to your cart");
						choiceFruit = user.nextLine();
						//Look for fruit
						inventory = new Scanner(textFile);
						while (inventory.hasNext()){
							//Found
							fruit = inventory.next();
							if (choiceFruit.equalsIgnoreCase(fruit)){
								price = inventory.nextFloat();
								match = true;
								numItems += 1;
								//Ask for pounds
								System.out.println("How many pounds would you like?");
								System.out.print(">");
								numPounds = user.nextFloat();
								System.out.println("");
								totalWeight += numPounds;
			
								//Calculate price for this item
								totalPrice += numPounds*price;
							}
						}
						//Error message
						if (!match){
							System.out.println("That fruit is not in our catalogue");
							match = true;
						}
					}
					break;
					
				//View cart
				case '2':
					System.out.println("You have bought " + numItems + " items");
					System.out.println("Your total is $" + totalPrice);
					System.out.println("(Your total might change because of additional costs before check out)");
					break;
				//Clear cart
				case '3':
					System.out.println("This will reset all of the items in your cart");
					System.out.println("Are you sure? (y/n)");
					sure = user.next().charAt(0);
					
					switch (sure){
						case 'y':
							totalWeight = 0;
							totalPrice = 0;
							numItems = 0;
							System.out.println("You have " + numItems + " items in your cart");
							System.out.println("Your total is " + totalPrice);
							break;
						case 'n':
							System.out.println("You have decided not to clear your cart");
					}
					break;
					
				//Check out
				case '4':
					numBagsNeeded = Math.ceil(totalWeight/5f);
					System.out.println("How many bags have you brought with you?");
					numBagsUser = user.nextInt();
					
					if ((numBagsNeeded - numBagsUser) > 0)
						numBagsBought = numBagsNeeded - numBagsUser;
					
					cartPrice = totalPrice + (numBagsBought*0.15f);
					
					System.out.printf("Your total is $%.2f\n", cartPrice);
					System.out.println("We only accept gift cards");
					System.out.println("Please enter the amount of money there is in your gift card");
					giftCardMoney = user.nextFloat();
					
					if ((giftCardMoney - cartPrice) >= 0){
						giftCardRemaining = giftCardMoney - cartPrice;
						System.out.printf("Your gift card has $%.2f remaining \n", giftCardRemaining);
						System.out.println("Thank you for shopping at Salamah's Market!");
						totalWeight = 0;
						totalPrice = 0;
						numItems = 0;
						exit = true;
					}
					else
						System.out.println("Sorry, that is not enough money");
					
					break;
					
				//Exit
				case '5':
					System.out.println("Thank you for shopping at Salamah's Market");
					exit = true;
					break;
					
				//Default
				default:
					System.out.println("please enter the digit associated with your desired option");
			}
		}
	}
}
		