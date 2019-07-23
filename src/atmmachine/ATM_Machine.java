/**
 * 
 */
/**
 * @author vparab
 *
 */
package atmmachine;

import atmmachine.*;
import java.util.*;

public class ATM_Machine {
	String account_number;//account number
	int count=0;            //counts number of time user has input invalid entries
	
	
	
	public void Validation()
	{
		String pin;
		System.out.println("Enter card number:");
		Scanner myObj = new Scanner(System.in);
		account_number= myObj.nextLine();
		System.out.println("Enter pin:");
		pin= myObj.nextLine();
		
		if(account_number.equals("0123456789012345") && pin.equals("0987"))//verification using database 
		{
			if(account_number.length()==16)
			{
				System.out.println("OK its working");
				menu();
			}
			else
			{
				System.out.println("Invalid card.. Please retry");
				Validation();
			}
				
		}
		else
		{
				System.out.println("Sorry invalid entry");
				Validation();

		}
		
	}
	
	public void menu()
	{
		int choice;
		Receipt R = new Receipt();
		System.out.println("1: Statement \n2: Withdraw \n3: Transfer\n4: Deposit \n5: Log Out");
		Scanner myObj = new Scanner(System.in);
		choice= myObj.nextInt();
		switch(choice)
		{
			case 1: //Statement
					R.receipt(1234, 1, account_number);
					break;
			case 2: //withdraw
				R.receipt(1234, 1, account_number);
				break;
			case 3: //Transfer
				R.receipt(1234, 1, account_number);
				break;
			case 4: //Deposit
				R.receipt(1234, 1, account_number);
				break;
			case 5: Log_Out l= new Log_Out();
				l.logout();
				//R.receipt(1234, 1, account_number);
				break;
			default:
				System.out.println("Invalid Entry");
				count++;
				if(count<3)
				{
					menu();	
				}
				else 
				{
					Log_Out l1= new Log_Out();
					//System.out.println("Logged Out");
					l1.logout();
				}
		
		}
	}
	
	
	
	public static void main(String[] jjjjjj) {
		ATM_Machine a1=new ATM_Machine();
		withdrawal w = new withdrawal();
		Receipt R = new Receipt();
		//R.receipt(0,7,2);
		//w.test("hellollll");
		a1.Validation();
		//a1.main_menu();
		// TODO Auto-generated method stub

	}

}
