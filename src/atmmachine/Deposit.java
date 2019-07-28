package atmmachine;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import atmmachine.Receipt;
import atmmachine.ATM_Machine;

public class Deposit {
	
	
public void deposit1(String number)
{
	
    Long acc_number=Long.parseLong(number) ;
    //long a=1111222233334444l;
    int acc_balance=0;
    int new_acc_balance=0;
	try{  
		//step1 load the driver class  
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		  
		//step2 create  the connection object  
		Connection con=DriverManager.getConnection(  
		"jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:TRAIN1","amdias","password");  
		  
		//step3 create the statement object  
		Statement stmt=con.createStatement(); 
		Statement stmt2=con.createStatement(); 
		
		//step4 execute query retrieve 
ResultSet rs=stmt.executeQuery("select account_balance from account,atm_card where account.card_no=ATM_CARD.card_no and atm_card.card_no='"+acc_number+"'");  
		while(rs.next()) {	
			//System.out.println(rs.getInt("account_balance"));	
		acc_balance = rs.getInt("account_balance");
		
		}
		
		System.out.println("Please enter the amount to deposit");
		Scanner s=new Scanner(System.in);
		int amt=s.nextInt();
		acc_balance = acc_balance + amt;
		String query2 = " update account set account_balance ="+acc_balance+" where card_no='"+acc_number+"'" ;
		//SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
		//FROM Orders
		//INNER JOIN Customers
		//ON Orders.CustomerID=Customers.CustomerID;
		//select balance from account,atm_card where account.card_no=atm_card.card_no and atm_card.card_no=acc_number;
		stmt2.executeUpdate(query2);
		System.out.print("The Transaction is processed successfully\n");
		System.out.print("The new balance is "+acc_balance);
		Receipt r1 = new Receipt();
		ATM_Machine a1 = new ATM_Machine();
		int temporary;
		temporary = a1.Receipt_no + 1;
		a1.Receipt_no=temporary;
		r1.receipt(temporary, 4, a1.Account_no);
		
	}
	catch(Exception e)
	{
		System.out.println("Error");
	}
	
	
}

}
