import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Lab5_DelaRocha{
	//Main Method
	public static void main(String [] args) throws FileNotFoundException{
		Scanner user = new Scanner(System.in);
		
		System.out.println("-----Welcome to my lab-----");
		System.out.println("Please enter a word.");
		System.out.print(">");
		String word = user.nextLine();
		
		if (isPalindrome(word)){
			System.out.println("The word " + word + " is a palindrome.");
		}
		else{
			System.out.println("The word " + word + " is not a palindrome.");
		}
		
		System.out.println("Please enter the name of a file.");
		System.out.print(">");
		String fileName = user.nextLine();
		System.out.println("Please enter a character.");
		System.out.print(">");
		char character = user.next().charAt(0);
		
		System.out.println("There are " + lineCounter(fileName) + " Lines in the " + fileName + " file.");
		System.out.println("The character " + character + " appears " + numOfOccurences(character, fileName) + "times");
		
		System.out.println("Please provide the height of a cylinder.");
		System.out.print(">");
		double height = user.nextDouble();
		System.out.println("Please provide the diameter.");
		System.out.print(">");
		double diameter = user.nextDouble();
		
		System.out.println("The area of the cylinder is " + areaOfCylinder(height, diameter) + ".");
	}
	//isPalindrome
	public static boolean isPalindrome(String word){
		String reverseWord = "";
		for (int i = word.length() - 1; i >= 0; i--){
			reverseWord += word.charAt(i);
		}
		if (word.equals(reverseWord))
			return true;
		return false;
	}
	//lineCounter
	public static int lineCounter(String textFileName) throws FileNotFoundException{
		int numLines = 0;
		
		textFileName = addTxt(textFileName);
		File document = new File(textFileName);
		Scanner documentReader = new Scanner(document);
		while (documentReader.hasNextLine()){
			numLines ++;
			documentReader.nextLine();
		}
		documentReader.close();
		return numLines;
	}
	//numOfOccurences
	public static int numOfOccurences(char target, String textFileName) throws FileNotFoundException{
		int numChar = 0;
		String line;
		
		textFileName = addTxt(textFileName);
		File document = new File(textFileName);
		Scanner documentReader = new Scanner(document);
		while (documentReader.hasNextLine()){
			line = documentReader.nextLine();
			for (int i = 0; i < line.length(); i++){
				if (target == line.charAt(i)){
					numChar ++;
				}
			}
		}
		return numChar;
	}
	//areaOfCylinder
	public static double areaOfCylinder(double height, double diameter){
		double radius = diameter/2;
		double area = (2*Math.PI*radius*height)+(2*Math.PI*Math.pow(radius,2));
		return area;
	}
	//addTxt
	public static String addTxt(String word){
		if (!word.contains(".txt"))
			word += ".txt";
		return word;
	}
}