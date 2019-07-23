package atmmachine;

import java.util.Scanner;
import atmmachine.*;

public class Receipt {

	public void receipt(int receipt_no, int category, String accno)
	{
		int count=0;
		//System.out.println("hello"+receipt_no);
		Scanner myObj = new Scanner(System.in);
		/*
		 * System.out.println("Enter Account Number:\n"); acc= myObj.nextInt();
		 */
		switch(category)
		{
			case 1:	System.out.println("Statement Printed Successfully");
					//for account records
					//Statement of previous 5 transaction
					//print time and date with receipt
					//logout();
					break;
			case 2:
					System.out.println("Withdrawal done Successfully");
					//for normal withdrawal
					//will include receipt number
					//amount withdrawn
					//current balance
					//update statement on db
					//print time and date with receipt
					//logout();
				break;
			case 3:
					System.out.println("Transfer done Successfully");
					//for money transfer with sender account number and receiver number
					//amount transferred
					//remaining balance
					//update statement on db
					//print time and date with receipt
					//logout();
					break;
			case 4:
					System.out.println("Money deposited Successfully");
					//for depositing money
					//no. of notes deposited
					//updated balance
					//update statement on db
					//print time and date with receipt
					//logout();
					break;
		/*
		 * default : System.out.println("Invalid Choice"); ATM_Machine a= new
		 * ATM_Machine(); a.main_menu(); break;
		 */
		
		}
		//System.out.println("Successful");
	}
}
