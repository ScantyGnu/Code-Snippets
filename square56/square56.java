import java.util.Scanner;

public class square56{
	public static void main(String [] args){
		Scanner user = new Scanner(System.in);
		int[] nums = {2,4,5,2,5,1,2};
		int length = 0;
		int i;
		for (i = 0; i < nums.length; i++){
			if (((((nums[i]*nums[i]) + 10) % 10) != 6) && ((((nums[i]*nums[i]) + 10) % 10) != 5)){
				length ++;
			}
		}
		int[] numSqrd = new int[length];
		int j = 0;
		for (i  = 0; i < nums.length; i++){
			if (((((nums[i]*nums[i]) + 10) % 10) != 6) && ((((nums[i]*nums[i]) + 10) % 10) != 5)){
				numSqrd[j] = ((nums[i]*nums[i]) + 10);
				j++;
			}
		}
		for (i  = 0; i < numSqrd.length; i++){
			System.out.print(numSqrd[i]+ " ");
		}
	}
}