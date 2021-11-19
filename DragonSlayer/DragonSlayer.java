import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

/*
    Emilio Tadeo de la Rocha Galan
    [CS1101] CL2 - Dragon Slayer
    Through my submission, I certify that all written code belongs to me.
    I acknowledge that I will be held responsible for my dishonesty should
    the Instructional Team find any evidence of academic dishonesty.
*/


public class DragonSlayer{
	static int dungeonRows = 4;
	static int dungeonColumns = 4;
	static int lesserMonsters = 2;
	static int dragon = 1;

	public static void main(String[] args)  throws FileNotFoundException{
		//Your code STARTS HERE
		Scanner user = new Scanner(System.in);
		boolean userDeath = false;
		boolean exit = false;
		int userOptionMainMenu;
		char userOptionSure = 'p';
		//First 2 are the position, third is hp, fourth and fifth are shield and sword respectively
		int[] userStatus = {0, 0, 10, 0, 0};
		//First 2 are position, third is hp, fourth is sword (1) or shield (0), fifth is sprite
		//For dragon first 2 are position, third is hp, fourth is how many times hes seen you, fifth is alive or dead
		int[][] monsterArray = new int[lesserMonsters + dragon][5];
		int movementInput = 0;
		boolean dragonDeath = false;
		while (exit == false){
			try {
				printMainMenu();
			}
			catch (FileNotFoundException e){
				System.out.println("ERROR");
			}
			userOptionMainMenu = user.nextInt();
			switch (userOptionMainMenu){
				
				//Start game
				case 1:
					//get the monsterLocations and store them in monsterLocations
					int [][] monsterLocations = getMonstersLocation();
					//Create the world
					int [][] world = worldGenerator(monsterLocations, monsterArray);
					printIntro();
					System.out.println("Enter anything to Continue");
					user.next();
					discoverSquares(userStatus, world);
					printWorld(world, userStatus);
					printWorldStatus(userStatus, world);
					//Take movement input and see if it is valid
					while (!moveUser(userStatus, movementInput, world)){
						movementInput = user.nextInt();
					}
					movementInput = 0;
					//Gameplay loop
					while (dragonDeath == false){
						tileEvent(userStatus, world, monsterArray);
						dragonDeath = isDragonDead(monsterArray);
						if (userStatus[2] == 0){
							userDeath = true;
							break;
						}
						if (dragonDeath)
							break;
						discoverSquares(userStatus, world);
						printWorld(world, userStatus);
						printWorldStatus(userStatus, world);
						while (!moveUser(userStatus, movementInput, world)){
							movementInput = user.nextInt();
						}
						movementInput = 0;
						System.out.println("+----------------------------------------------+");
					}
					
					if (userDeath && dragonDeath){
						try {
							printNeutralEndScene();
						}
						catch (FileNotFoundException e){
							System.out.println("ERROR");
						}
						System.out.println("Enter anything to Continue");
						user.next();
						printCredits();
						exit = true;
					}
					else if (userDeath){
						try {
							printBadEndScene();
						}
						catch (FileNotFoundException e){
							System.out.println("ERROR");
						}
						System.out.println("Enter anything to Continue");
						user.next();
						printCredits();
						exit = true;
					}
					else if (dragonDeath){
						try {
							printGoodEndScene();
						}
						catch (FileNotFoundException e){
							System.out.println("ERROR");
						}
						System.out.println("Enter anything to Continue");
						user.next();
						printCredits();
						exit = true;
					}						
					break;
				//Show credits
				case 2:
					printCredits();
					System.out.println("Enter anything to Return");
					user.next();
					break;
					
				//Exit
				case 3:
					System.out.println("This will end the game");
					System.out.println("Are you sure? (y/n)");
		
					while (userOptionSure != 'y' && userOptionSure != 'n'){
						userOptionSure = user.next().charAt(0);
					}
					
					if (userOptionSure == 'y'){
						exit = true;
					}
					userOptionSure = 'p';
					break;
					
				default:
					System.out.println("Please enter a valid input");
					break;
			}
		}	
	}

	 //Your methods START HERE
	public static int worldGeneratorXL(int [][] monsterLocations, int worldRows, int worldColumns){
		return -1;
	}
	
	//This method will go through the event of the current Tile
	public static void tileEvent(int[] userStatus, int [][] world, int[][]  monsterArray){
		boolean eventEnd = false;
		int monsterNum = 1;
		Scanner user = new Scanner(System.in);
		int userChoice;
		
		//Tile is empty
		if (world[userStatus[1]][userStatus[0]] == 0 || world[userStatus[1]][userStatus[0]] == 6){
			System.out.println("There seems to be nothing here");
		}
		
		//Monster Tile
		else if (world[userStatus[1]][userStatus[0]] == 1 || world[userStatus[1]][userStatus[0]] == 4){
			//Identify which monster is in the tile
			for (int i = 0; i < monsterArray.length; i++){
				if ( monsterArray[i][0] == userStatus[0] && monsterArray[i][1] == userStatus[1]){
					monsterNum = i;
				}
			}
			System.out.println("You encounter a horrid creature as it wanders around aimlessly");
			if (monsterArray[monsterNum][3] == 0)
				System.out.println("In its hands you can see it holds your precious shield");
			else if (monsterArray[monsterNum][3] == 1)
				System.out.println("He's the one that stole your sword!");
			//Combat loop
			while (!eventEnd){
				try{
					printCombatMonster(userStatus, monsterArray, monsterNum, monsterArray[monsterNum][4]);
				}
				catch (FileNotFoundException e) {
					System.out.println("Game files not found");
				}
				userChoice = user.nextInt();
				switch (userChoice){
					//1. Do nothing
					case 1:
						System.out.println("+-----------------------------------+");
						System.out.println("You have chosen to do nothing.");
						if (monsterArray[monsterNum][3] == 0){
							System.out.println("The monster hits you with its claws.");
							userStatus[2] --;
						}
						if (monsterArray[monsterNum][3] == 1){
							System.out.println("The monster hits you with your own sword!.");
							userStatus[2] -= 2;
						}
						break;
					//2. Hit the enemy
					case 2:
						System.out.println("+-----------------------------------+");
						//Player Attack message
						switch (userStatus[4]){
						//player has no sword
							case 0:
								System.out.println("You have chosen to hit the monster.");
								monsterArray[monsterNum][2] --;
								break;
						//Player has Sword
							case 1:
								System.out.println("You have chosen to hit the monster with your sword.");
								monsterArray[monsterNum][2] -= 2;
								break;
						}
						//Monster Attack message
						switch (monsterArray[monsterNum][3]){
							//Monster has no sword
							case 0:
								switch (userStatus[3]){
								//Player has no shield
									case 0:
										System.out.println("The monster hits you with its claws.");
										userStatus[2] --;
										break;
								//player has shield
									case 1:
										System.out.println("The monster tries to hit you with its claws.");
										System.out.println("You are able to block the monster's attack with your shield!");
										break;
								}
								break;
							//Monster has sword
							case 1:
								switch (userStatus[3]){
								//Player has no shield
									case 0:
										System.out.println("The monster hits you with your own sword!.");
										userStatus[2] -= 2;
										break;
								//player has shield
									case 1:
										System.out.println("The monster hits you with your own sword!.");
										System.out.println("You are able to partially block the monster's attack with your shield!");
										userStatus[2] --;
										break;
								}
						}						
						break;
					//3. Run away
					case 3:
						System.out.println("+-----------------------------------+");
						System.out.println("You have chosen to run away.");
						eventEnd = true;
						break;
					//Wrong input
					default:
						System.out.println("+-----------------------------------+");
						switch (monsterArray[monsterNum][3]){
							//Monster does not have sword
							case 0:
								System.out.println("You stand still as the monster attacks you");
								userStatus[2] --;
								break;
							//Monster has sword
							case 1:
								System.out.println("You stand still as the monster slashes you");
								userStatus[2] -= 2;
								break; 
						}
						break;
				}
				if (monsterArray[monsterNum][2] <= 0){
					System.out.println("The monster colapses as life escapes his body");
					switch (monsterArray[monsterNum][3]){
						//Monster had the shield
						case 0:
							System.out.println("You have recovered your shield!");
							try {
								printShield();
							}
							catch (FileNotFoundException e){
								System.out.println("ERROR");
							}
							System.out.println("Enter anything to Continue");
							user.next();
							//Give the player the shield
							userStatus[3] = 1;
							break;
						//Monster had the sword
						case 1:
							System.out.println("You have recovered your sword!");
							try{
							printSword();
							}
							catch (FileNotFoundException e){
								System.out.println("ERROR");
							}
							System.out.println("Enter anything to Continue");
							user.next();
							//Give the player the sword
							userStatus[4] = 1;
							break;
					}
					//If both combatants die at the end make the player be barely alive
					if (userStatus[2] <= 0)
						userStatus[2] = 1;
					eventEnd = true;
					//Turn the Tile into a dead monster
					world[userStatus[1]][userStatus[0]] = 7;
				}
				//Death
				if (userStatus[2] <= 0)
					eventEnd = true;
			}
		}
		
		//Dragon
		else if (world[userStatus[1]][userStatus[0]] == 2 || world[userStatus[1]][userStatus[0]] == 5){
			for (int i = 0; i < monsterArray.length; i++){
				if ( monsterArray[i][0] == userStatus[0] && monsterArray[i][1] == userStatus[1]){
					monsterNum = i;
				}
			}
			if (monsterArray[monsterNum][3] == 1){
				System.out.println("The dragon has been waiting for your return");
				monsterArray[monsterNum][3] ++;
			}
			if (monsterArray[monsterNum][3] == 0){
				System.out.println("Here he is. The Terrible Dragon!");
				monsterArray[monsterNum][3] ++;
			}
			else if (monsterArray[monsterNum][3] == 2)
				System.out.println("The dragon is not amused by your cowardice");
			if (userStatus[3] == 1 && userStatus[4] == 1){
				System.out.println("You feel prepared for this fight");
			}
			else if (userStatus[3] == 1 || userStatus[4] == 1){
				System.out.println("You have a bad feeling about this");
			}
			else if (userStatus[3] != 1 && userStatus[4] != 1){
				System.out.println("You know you won't win this. Run!");
			}
			//Combat loop
			while (!eventEnd){
				//print sprite
				try{
					printDragon(userStatus, monsterArray, monsterNum);
				}
				catch (FileNotFoundException e) {
					System.out.println("Game files not found");
				}
				
				userChoice = user.nextInt();
				switch (userChoice){
					//1. Do nothing
					case 1:
						System.out.println("+-----------------------------------+");
						System.out.println("You have chosen to do nothing.");
						System.out.println("The dragon slashes you!.");
						userStatus[2] -= 3;
						break;
					//2. Hit the enemy
					case 2:
						System.out.println("+-----------------------------------+");
						//Player Attack message
						switch (userStatus[4]){
						//player has no sword
							case 0:
								System.out.println("You have chosen to hit the dragon.");
								monsterArray[monsterNum][2] --;
								break;
						//Player has Sword
							case 1:
								System.out.println("You have chosen to hit the dragon with your sword.");
								monsterArray[monsterNum][2] -= 2;
								break;
						}
						//Monster Attack message
						switch (userStatus[3]){
						//Player has no shield
							case 0:
								System.out.println("The dragon hits you with its claws.");
								userStatus[2] -= 2;
								break;
						//player has shield
							case 1:
								System.out.println("The dragon tries to hit you with its claws.");
								System.out.println("You are partially able to block the dragon's attack with your shield!");
								userStatus[2] -= 1;
								break;
						}	
						break;
					//3. Run away
					case 3:
						System.out.println("+-----------------------------------+");
						System.out.println("You have chosen to run away.");
						monsterArray[monsterNum][2] = 10;
						eventEnd = true;
						break;
					//Wrong input
					default:
						System.out.println("+-----------------------------------+");
						System.out.println("You stand still as the Dragon attacks you");
						userStatus[2] -= 3;
						break;
				}
				if (monsterArray[monsterNum][2] <= 0){
					System.out.println("The dragon colapses as life escapes his body");
					eventEnd = true;
					monsterArray[monsterNum][4] = 1;
				}
				//Death
				if (userStatus[2] <= 0)
					eventEnd = true;
			}
				
		}
		//Home
		else if (world[userStatus[1]][userStatus[0]] == 3){
			System.out.println("You have returned to your village");
			System.out.println("You meet your granpa at his small hut");
			while (!eventEnd){
				try{
					printHome(userStatus);
				}
				catch (FileNotFoundException e) {
					System.out.println("Game files not found");
				}
				userChoice = user.nextInt();
				switch (userChoice){
					//Ask for advice
					case 1:
						System.out.println("+----------------------------------------------+");
						try {
							printRandomAdvice(userStatus);
						}
						catch (FileNotFoundException e){
							System.out.println("Game files not found");
						}
						break;
					//leave
					case 2:
						eventEnd = true;
						System.out.println("+----------------------------------------------+");
						System.out.println("You say goodbye to everyone and get ready to go out on your adventure");
						break;
					case 3:
						if (userStatus[2] < 10){
							userStatus[2] = 10;
							System.out.println("+----------------------------------------------+");
							System.out.println("\"You should be careful out there\"");
						}
						else{
							System.out.println("+----------------------------------------------+");
							System.out.println("\"What? i can't hear you, speak up!\"");
						}
						break;
				}
		}
		}
	}
	
	public static void printRandomAdvice(int[] userStatus)throws FileNotFoundException{
		int lineCount = 0;
		File advice = new File("RandomAdvice.txt");
		Scanner adviceScanner = new Scanner(advice);
		//Figure out how many options we have
		while (adviceScanner.hasNextLine()){
			lineCount++;
			adviceScanner.nextLine();
		}
		//choose a random one
		int randomChoice = (int)(Math.random()*(lineCount+1));
		//move towards it and print it
		adviceScanner = new Scanner(advice);
		int currentLine = 0;
		while (currentLine < randomChoice-1){
			adviceScanner.nextLine();
			currentLine++;
		}
		System.out.println(adviceScanner.nextLine());
	}
	
	//Printing Stuff methods for the code to be more readable
	public static void printShield()throws FileNotFoundException{
		File shield = new File("Shield.txt");
		Scanner shieldScanner = new Scanner(shield);
		while (shieldScanner.hasNextLine())
			System.out.println(shieldScanner.nextLine());
	}
	
	public static void printSword()throws FileNotFoundException{
		File sword = new File("Sword.txt");
		Scanner swordScanner = new Scanner(sword);
		while (swordScanner.hasNextLine())
			System.out.println(swordScanner.nextLine());
	}
	
	public static void printHome(int[] userStatus)throws FileNotFoundException{
		File house = new File("Home.txt");
		Scanner houseScanner = new Scanner(house);
		while (houseScanner.hasNextLine())
			System.out.println(houseScanner.nextLine());
		houseScanner.close();
		System.out.println("ACTIONS:");
		System.out.println("1. Ask for advice");
		System.out.println("2. Leave");
		if (userStatus[2] < 10)
			System.out.println("3. Ask for healing");
	}
	
	public static void printDragon(int [] userStatus, int[][] monsterArray, int monsterNum) throws FileNotFoundException{
		File dragon = new File("Dragon.txt");
		Scanner dragonScanner = new Scanner(dragon);
		while (dragonScanner.hasNextLine())
			System.out.println(dragonScanner.nextLine());
		System.out.print("|   HP:"); 
		printHP(monsterArray[monsterNum][2]);
		System.out.println("");
		System.out.println("+-----------------------------------+");
		System.out.print("HP:"); 
		printHP(userStatus[2]);
		System.out.println("");
		System.out.println("ACTIONS:");
		System.out.println("1. Do nothing");
		System.out.println("2. Hit the enemy");
		System.out.println("3. Run away");
	}	
	
	public static void printCombatMonster(int [] userStatus, int[][] monsterArray, int monsterNum, int monsterType) throws FileNotFoundException{
		switch (monsterType){
			case 0:
				File enemy1 = new File("Enemy1.txt");
				Scanner enemy1Scanner = new Scanner(enemy1);
				while (enemy1Scanner.hasNextLine())
					System.out.println(enemy1Scanner.nextLine());
				enemy1Scanner.close();
				break;
			case 1:
				File enemy2 = new File("Enemy2.txt");
				Scanner enemy2Scanner = new Scanner(enemy2);
				while (enemy2Scanner.hasNextLine())
					System.out.println(enemy2Scanner.nextLine());
				enemy2Scanner.close();
				break;
			case 2:
				File enemy3 = new File("Enemy3.txt");
				Scanner enemy3Scanner = new Scanner(enemy3);
				while (enemy3Scanner.hasNextLine())
					System.out.println(enemy3Scanner.nextLine());
				enemy3Scanner.close();
				break;
			default:
				File enemy4 = new File("Enemy4.txt");
				Scanner enemy4Scanner = new Scanner(enemy4);
				while (enemy4Scanner.hasNextLine())
					System.out.println(enemy4Scanner.nextLine());
				enemy4Scanner.close();
				break;
		}
			System.out.print("|   HP:"); 
			printHP(monsterArray[monsterNum][2]);
			System.out.println("");
			System.out.println("+-----------------------------------+");
			System.out.print("HP:"); 
			printHP(userStatus[2]);
			System.out.println("");
			System.out.println("ACTIONS:");
			System.out.println("1. Do nothing");
			System.out.println("2. Hit the enemy");
			System.out.println("3. Run away");
	}
	
	public static void printHP(int hp){
		for (int i = 0; i < hp; i++)
			System.out.print("[]");
	}
	
	public static void printBadEndScene()throws FileNotFoundException{
		File intro = new File("BadEnd.txt");
		Scanner introScanner = new Scanner(intro);
		while (introScanner.hasNextLine())
			System.out.println(introScanner.nextLine());		
	}
	
	public static void printGoodEndScene()throws FileNotFoundException{
		File intro = new File("GoodEnd.txt");
		Scanner introScanner = new Scanner(intro);
		while (introScanner.hasNextLine())
			System.out.println(introScanner.nextLine());		
	}
	
	public static void printNeutralEndScene()throws FileNotFoundException{
		File intro = new File("NeutralEnd.txt");
		Scanner introScanner = new Scanner(intro);
		while (introScanner.hasNextLine())
			System.out.println(introScanner.nextLine());		
	}	
	
	public static int [][] worldGenerator(int[][] monsterLocations, int[][] monsterArray){
		int [][] world = new int [dungeonRows][dungeonColumns];
		int n = lesserMonsters + dragon;
		int n2 = 1;
		//Initializing the rest of the world (0 = nothing, 1 = monster, 2 = dragon)
		for (int i = 0; i < world.length; i++)
			for (int j = 0; j < world[i].length; j++)
				world[i][j] = 0;
			
		//Setting where the monsters are
		for (int i = 0; i < monsterLocations.length; i++){
			if (n <= dragon){
				world[ monsterLocations[i][0] ] [ monsterLocations[i][1] ] = 2;
				monsterArray[i][0] = monsterLocations[i][1];
				monsterArray[i][1] = monsterLocations[i][0];
				monsterArray[i][2] = 10;
				monsterArray[i][4] = -1;
			}
			else{
				world[ monsterLocations[i][0] ] [ monsterLocations[i][1] ] = 1;
				monsterArray[i][0] = monsterLocations[i][1];
				monsterArray[i][1] = monsterLocations[i][0];
				monsterArray[i][2] = 5;
				n--;
				if (n2 == 1){
					monsterArray[i][3] = 1;
					n2 ++;
				}
				else 
					monsterArray[i][3] = 0;
				monsterArray[i][4] = (int)(Math.random()*4);
		}
		//Home is always at 0,0 (3 = home)
		world[0][0] = 3;
		}
		return world;
	}
	
	public static boolean moveUser(int[] userStatus, int input, int [][] world){
		int number = 1;
		int moveNorth = -1;
		int moveEast = -1;
		int moveWest = -1;
		int moveSouth = -1;
		//Identify what directions are available
		if (userStatus[1] != 0){
			moveNorth = number;
			number++;
		}
		if (userStatus[0] != world[userStatus[1]].length - 1){
			moveEast = number;
			number++;
		}
		if (userStatus[0] != 0){
			moveWest = number;
			number++;
		}
		if (userStatus[1] != world.length -1)
			moveSouth = number;
		
		//Move the user
		if (input == moveNorth && input > 0){
			userStatus[1] -= 1;
			return true;
		}
		else if (input == moveEast && input > 0){
			userStatus[0] += 1;
			return true;
		}
		else if (input == moveWest && input > 0){
			userStatus[0] -= 1;
			return true;
		}
		else if (input == moveSouth && input > 0){
			userStatus[1] += 1;
			return true;
		}
		else
			return false;
	}
	
	public static void discoverSquares(int [] userStatus, int [][] world){
		for (int i = 0; i < world.length; i++){
			for (int j = 0; j < world[i].length; j++){
				if ((userStatus[0] == j - 1 && userStatus[1] == i) || (userStatus[0] == j && userStatus[1] == i + 1) || (userStatus[0] == j && userStatus[1] == i - 1) || (userStatus[0] == j + 1 && userStatus[1] == i)){
					if (world[i][j] == 0){
						world[i][j] = 6;
					}
					if (world[i][j] == 1){
						world[i][j] = 4;
					}
					if (world[i][j] == 2){
						world[i][j] = 5;
					}
				}
			}
		}
	}
	
	public static void printWorldStatus(int[] userStatus, int[][] world){
		System.out.println("+----------------------------------------------+");
		System.out.println("You are in the [" + (userStatus[0]+1) + "," + (userStatus[1]+1) + "] Square");
		if (userStatus[4] == 0)
			System.out.println("You currently do not have a Sword");
		else
			System.out.println("You wield a powerful sword");
		if (userStatus[3] == 0)
			System.out.println("You currently do not have a shield");
		else 
			System.out.println("Your trusty shield protects you");
		System.out.print("HP: ");
		for (int i = 0; i < userStatus[2]; i++)
			System.out.print("[]");
		System.out.println("");
		System.out.println("ACTIONS:");
		int number = 1;
		if (userStatus[1] != 0){
			System.out.println( number + ". Go North");
			number++;
		}
		if (userStatus[0] != (world[userStatus[1]].length - 1)){
			System.out.println(number + ". Go East");
			number++;
		}
		if (userStatus[0] != 0){
			System.out.println(number + ". Go West");
			number++;
		}
		if (userStatus[1] != world.length -1)
			System.out.println(number + ". Go South");
		System.out.println("+----------------------------------------------+");
	}
	
	public static void printWorld(int[][] world, int [] userStatus){
		System.out.println("+----------------------------------------------+");
		for (int i = 0; i < world.length; i++){
			for (int j = 0; j < world[i].length; j++){
				//User position
				if (i == userStatus[1] && j == userStatus[0]){
					System.out.print("[HERO] ");
				}
				//Unexplored
				else if (world[i][j] == 0 || world[i][j] == 1 || world[i][j] == 2){
					System.out.print("[####] ");
				}
				//Monster and explored
				else if (world[i][j] == 4){
					System.out.print("[>__<] ");
				}
				//Dead Monster
				else if (world[i][j] == 7){
					System.out.print("[X__X] ");
				}
				//Dragon and Explored
				else if (world[i][j] == 5){
					System.out.print("[/^^\\] ");
				}
				//Home, always
				else if (world[i][j] == 3){
					System.out.print("[HOME] ");
				}
				//Empty and Explored
				else if (world[i][j] == 6){
					System.out.print("[    ] " );
				}
					
				if ((j+1) % dungeonColumns == 0){
					System.out.println("");
				}
			}
		}
	}
	
	public static void printMainMenu()throws FileNotFoundException{
			File intro = new File("Intro.txt");
			Scanner introScanner = new Scanner(intro);
			while (introScanner.hasNextLine())
				System.out.println(introScanner.nextLine());
			System.out.println("><><><><><><><><><><><><><><><>Select an option<><><><><><><><><><><><><><><><><");
			System.out.println("1. Start Game");
			System.out.println("2. Show Credits");
			System.out.println("3. Exit Game");
	}
	
	public static void printIntro()throws FileNotFoundException{
		File intro = new File("IntroScene.txt");
		Scanner introScanner = new Scanner(intro);
		while (introScanner.hasNextLine())
			System.out.println(introScanner.nextLine());
	}
	
	public static void printCredits()throws FileNotFoundException{
		File intro = new File("Credits.txt");
		Scanner introScanner = new Scanner(intro);
		while (introScanner.hasNextLine())
			System.out.println(introScanner.nextLine());
	}
	
	public static boolean isDragonDead(int[][] monsterArray){
		for (int i = 0; i < monsterArray.length; i++)
				if (monsterArray[i][4] == -1)
					return false;
		return true;
	}
	
	  //Your methods END HERE

	  /* ---------------------------------- Do not change anything below this comment ---------------------------------- */

	  /*
	   * The following method returns a 2-D array
	   * The first set of row,column is the location of the monster guarding the shield
	   * The second set of row,column is the location of the monster guarding the sword
	   * The third set of row,column is the location of the dragon
	   * The minimum size for the dungeon must be 4x4, this is set in the dungeonColumns and dungeonRows global variables
	   */
	private static int[][] getMonstersLocation(){
		if(dungeonColumns < 4 || dungeonRows < 4){
		  System.out.println("Minimum size for the dungeon must be 4x4");
		  return null;
		}

		int[][] monstersLocation = new int[lesserMonsters + dragon][2];
		Random rand = new Random();

		for(int i = 0; i < lesserMonsters + dragon; i++){
		  int row = rand.nextInt(dungeonRows);
		  int column = rand.nextInt(dungeonColumns);
		  if((row == 0 && column == 0) || (i != 0 && monsterLocationDuplicate(i + 1, row, column, monstersLocation))){
			int columnDuplicatedValue = column;
			while(column == columnDuplicatedValue || (i != 0 && monsterLocationDuplicate(i + 1, row, column, monstersLocation)))
			  column = rand.nextInt(dungeonColumns);
		  }
		  monstersLocation[i][0] = row;
		  monstersLocation[i][1] = column;
		}
		return monstersLocation;
	  }

	  /*
	   * Returns true if a monster is already placed in the current cell (row, column)
	   */
	private static boolean monsterLocationDuplicate(int monsters, int row, int column, int[][] monstersLocation){
		for(int i = 0; i < monsters; i++){
		  if(monstersLocation[i][0] == row && monstersLocation[i][1] == column)
			return true;
		}
		return false;
	  }
	}
