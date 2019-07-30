package atmmachine;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import atmmachine.Receipt;
import atmmachine.ATM_Machine;

public class Deposit {
	
	Receipt r1 = new Receipt();
	ATM_Machine a1 = new ATM_Machine();
	int temporary;
    int acc_balance=0;
    int new_acc_balance=0;
public void deposit1(int number)
{
	
    int acc_number=number;
	try{  
		//step1 load the driver class  
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		  
		//step2 create  the connection object  
		Connection con=DriverManager.getConnection(  
		"jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:TRAIN1","amdias","password");  
		  
		//step3 create the statement object  
		Statement stmt=con.createStatement(); 
		//Statement stmt2=con.createStatement(); 
		
		//step4 execute query retrieve 
        ResultSet rs=stmt.executeQuery("select account_balance from account where account_no='"+acc_number+"'");  
        while(rs.next()) {	
		//System.out.println(rs.getInt("account_balance"));	
		acc_balance = rs.getInt("account_balance");
		
        }
        System.out.println("Your balance is "+acc_balance);	
		System.out.println("Please enter the amount to deposit");
		Scanner s=new Scanner(System.in);
		int amt=s.nextInt();
		if(amt<=0 || amt>20000)
		{
	        System.out.println("cannot deposit "+amt);	
            deposit1(acc_number); 
		}
		else {
		
		new_acc_balance = acc_balance + amt;
		String query2 = " update account set account_balance ="+new_acc_balance+" where account_no='"+acc_number+"'" ;
		stmt.executeUpdate(query2);
		System.out.print("The Transaction is processed successfully\n");
		System.out.println("The new balance is "+new_acc_balance);
		
		String sql1 = "INSERT INTO TRANSACTION(TRANSACTION_TYPE,ACCOUNT_NO,TRANSACTION_AMT) VALUES('DEPOSIT',"+acc_number+","+amt+")";
		stmt.executeUpdate(sql1);
		
		String sql7 ="select max(transaction_id) from transaction where account_no='"+acc_number+"'"; 
		ResultSet r=stmt.executeQuery(sql7); 
		r.next();
		this.temporary=r.getInt(1);
		r1.receipt(temporary, 4, acc_number);
		
		}
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	
	
}
}