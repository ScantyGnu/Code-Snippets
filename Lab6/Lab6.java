import java.util.Scanner;

public class Lab6{
	public static void main(String [] args){
		Scanner user = new Scanner(System.in);
		
		System.out.println("Please enter a phrase");
		String userPhrase = user.nextLine();
		System.out.println("Please enter a character");
		char userChar = user.next().charAt(0);
		System.out.println("Please enter two numbers");
		int userStart = user.nextInt();
		int userEnd = user.nextInt();
		int temp;
		
		if (userStart > userEnd){
			temp = userStart;
			userStart = userEnd;
			userEnd = temp;
		}
		
		System.out.println("the sum of the odd numbers is " + oddSumRange(userStart, userEnd));
		
		if (isPalindrome(userPhrase,userPhrase.length()-1)){
			System.out.println(userPhrase + " is a palindrome");
		}
		else{
			System.out.println(userPhrase + " is not a palindrome");
		}
		
		System.out.println(userPhrase + " has " + vowelCounter(userPhrase, userPhrase.length()-1) + " vowels");
		System.out.println(userPhrase + " has " + frequency(userChar, userPhrase, userPhrase.length()-1) + " ocurrences of " + userChar);
		System.out.println(userPhrase + " backwards is " + reverse(userPhrase, userPhrase.length() - 1));
	}
	//Method for identifying palindromes
	public static boolean isPalindrome(String word, int length){
		boolean same = false;
		//Do this only for half of the word
		if (length != (length/2)){
			//Compare the characters at both ends and move inward on the phrase
			int position = (word.length()-1) - length;
			//Extra case to skip spaces
			if ((word.charAt(length) == word.charAt(position)) || (word.charAt(position) == ' ') || (word.charAt(length) == ' ')){
				same = true;
			}
			else
			return false;
			if (same && isPalindrome(word, length - 1)){
				return true;
			}
			else
				return false;
		}
		return true;
	}
	//Method to identify vowels
	public static boolean isVowel(char c){
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			return true;
		return false;
	}
	//Method to count vowels
	public static int vowelCounter(String phrase, int iterationNum){
		//Do this until we run out of characters in the phrase
		if (iterationNum < 0){
			return 0;
		}
		else{
			//if the character we are seeing is a vowel we add 1 to our sum and move to the next
			if (isVowel(phrase.charAt(iterationNum))){
				return 1 + vowelCounter(phrase, iterationNum - 1);
			}
			else
				return vowelCounter(phrase, iterationNum - 1);
		}
	}
	//Method to count targets
	public static int frequency(char target, String phrase, int iterationNum){
		//Do this until we run out of characters in the phrase
		if (iterationNum < 0){
			return 0;
		}
		else{
			//if the character we are seeing is the target we add 1 to our sum and move to the next
			if (phrase.charAt(iterationNum) == target){
				return 1 + frequency(target, phrase, iterationNum - 1);
			}
			else
				return frequency(target, phrase, iterationNum - 1);
		}
	}
	//Method to sum odd numbers
	public static int oddSumRange(int start, int end){
		if (start == end + 1)
			return 0;
		else{
			if (start % 2 == 1){
				return start + oddSumRange(start + 1, end);
			}
		}
		return oddSumRange(start + 1, end);
	}
	//Method to reverse Strings
	public static String reverse(String phrase, int iterationNum){
		if (iterationNum < 0)
			return "";
		return (phrase.charAt(iterationNum) + "") + reverse(phrase, iterationNum - 1);
	}
}
