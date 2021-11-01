import java.util.Scanner;

public class ConsecutiveNumbers{
	public static void main(String [] args){
		Scanner user = new Scanner(System.in);
		int [] numList = {100, 1, 200, 300, 230, 200};
		int [] numList2 = {100, 1, 200, 100, 100};
		
		System.out.println(consecutive100(numList, numList.length-1));
		System.out.println(consecutive100(numList2, numList2.length-1));
	}
	//This method will return true if there are two instances of  100 in a row in an array
	//It takes as parameters the array and the length
	public static boolean consecutive100(int[] numList, int iterationNum){
		//If there are less than 2 items in the array there is no chance of a double 100
		if (iterationNum < 2)
			return false;
		//If our number and the next are 100 we return true
		else if ((numList[iterationNum] == 100) && (numList[iterationNum - 1] == 100))
			return true;
		else //Go to the next number in the array
			return consecutive100(numList, iterationNum -1);
	}
}