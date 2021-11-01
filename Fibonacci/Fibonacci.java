import java.util.Scanner;

public class Fibonacci{
	public static void main(String [] args){
		Scanner User = new Scanner(System.in);
		
		System.out.println("Enter two consecutive numbers of the fibonacci sequence from the first 20");
		int m = User.nextInt();
		int n = User.nextInt();
		int f1 = 1;
		int f2 = 1;
		boolean valid = false;
		int i = 0;
		
		//We switch so that m is always bigger than n
		if (n > m) {
			int p = n;
			n = m;
			m = p;
		}
		
		//we compute the sequence and compare our numbers
		//while we haven't found our numbers we keep going
		//we compute 2 numbers per loop so we do it 10 times
		while ((!valid) && (i < 10)){
			if ((n == f1) && (m == f2))
				valid = true;
			f1 += f2;
			if ((n == f2) && (m == f1))
				valid = true;
			i++;
		}
		//if we found it compute the first 20 numbers in the sequence
		if (valid){
			System.out.println(" ");
			//we compute 2 per loop so 10 loops
			for (i = 0; i < 10; i++){
				System.out.print(n + " " + m + " ");
				n += m;
				m += n;
			}
		}
		//if we did not find them we print an error
		else
			System.out.println("These are not consecutive numbers from the fibonacci sequence");
	}
}	