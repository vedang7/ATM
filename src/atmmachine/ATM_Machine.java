/**
 * 
 */
/**
 * @author vparab
 *
 */
package atmmachine;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class ATM_Machine {
	String Card_number;//Card number
	int validcard=0;
	String tocheckcount;
	int Receipt_no=1000;
	String tempcardnum;
	String StringAccount_no;
	Integer Account_no;
	final int countvalue=3;
	Date todayDate =  new Date();
	String pin;//pin entered
	String encryptednumber; //encrypted number
	int[] acno_array; //used for conversion of integer into digits
	Integer count=0;            //counts number of time user has input invalid entries
	
	String url = "jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:Train1";
	String user = "amdias";
	String pass = "password";
		
	public static boolean validateJavaDate(String strDate)
	   {
        //System.out.println("Retrieved from database1: "+strDate);
		System.out.println("\n\n\tWELCOME");


		    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");

		    try
		    {
		        Date javaDate = sdfrmt.parse(strDate);
		        System.out.println("Retrieved from database: "+javaDate);
		        Date todayDate = sdfrmt.parse(sdfrmt.format(new Date()));
		        System.out.println("Todays date: "+todayDate);
		        if(javaDate.compareTo(todayDate)>=0)
		        {
		        	return true;
		        }
		        else
		        	return false;
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        System.out.println(strDate+" is Invalid Date format");
		        return false;
		    }
		    
		}
	
	
	public void Encryption()
	{
	    Long cardnum;
	    Integer pinofcard;
	    int key=3;
	    int cardarray[] = new int[Card_number.length()], pinarray[] = new int[pin.length()];
	    int temp,i=0;
	    Long tempp=(long) 0;
        cardnum = Long.parseLong(Card_number);
	    while(cardnum!=0)
	    {
	    	temp=(int) (cardnum%10);
	    	cardnum/=10;
	    	temp+=key;
	    	temp%=10;
	    	cardarray[i]=temp;
	    	i++;
	    }
	    for(int k=Card_number.length()-1;k>=0;k--)
	    {
	    	tempp = tempp* 10 + cardarray[k];
	    }
	    tempcardnum= Long.toString(tempp);
	    i=0;
	    pinofcard = Integer.parseInt(pin);
	    while(pinofcard!=0)
	    {
	    	temp=pinofcard%10;
	    	pinofcard/=10;
	    	temp+=key;
	    	temp%=10;
	    	pinarray[i]=temp;
	    	i++;
	    }
	    tempp = (long) 0;
	    for(int k=pin.length()-1;k>=0;k--)
	    {
	    	tempp = tempp* 10 + pinarray[k];
	    }
	    
	    pin= Long.toString(tempp);
	}
	public void Validation()
	{
		Connection con=null;
		try
		{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(url,user,pass);
		Statement st = con.createStatement();
		System.out.println("-------------------------------------");
		System.out.print("Enter card number:  ");
		Scanner myObj = new Scanner(System.in);
		Card_number= myObj.nextLine();
		System.out.println("--------------------------------------");
		System.out.print("\nEnter pin:  ");
		pin= myObj.nextLine();
		System.out.println("---------------------------------------\n");
		String sql2 = "select * from atm_card where CARD_NO="+Card_number;
		Encryption();
		String sql = "select * from card_pin where CARD_PIN_E="+tempcardnum;
		ResultSet m= st.executeQuery(sql);
		m.next();
		if(tempcardnum.equals(m.getString(2)) && pin.equals(m.getString(4)))//verification using database 
		{
			ResultSet n= st.executeQuery(sql2);
			n.next();
			
			if(validateJavaDate(n.getString(5)))
			{
				if(n.getString(6).equals("1"))
				{
					String sql5="update card_pin set INVALID_ENTRIES ="+0+" where CARD_NO="+this.Card_number;
					ResultSet p= st.executeQuery(sql5);
					String sql6 ="select account_no from account where card_no="+this.Card_number; 
					ResultSet q=st.executeQuery(sql6); q.next();
					StringAccount_no=q.getString(1);
				    Account_no = Integer.parseInt(StringAccount_no);
					this.count=0;
					menu();
				}
				else
				{
					System.out.println("Card Blocked");
					Validation();
				}
			}
			else
			{
				System.out.println("Expired Card");
			}
				
		}
		else
		{
			if(Card_number.equals(m.getString(1)))
			{
				int temp;
				tocheckcount=m.getString(3);
				this.count = Integer.parseInt(tocheckcount);
				this.count++;
				temp=countvalue-this.count;
				if(this.count<=3)
				{
					System.out.println("Left chances :"+ temp);
					String sql3="update card_pin set INVALID_ENTRIES ="+this.count+" where CARD_NO="+this.Card_number;
					ResultSet o= st.executeQuery(sql3);
					Validation();
				}
				else
				{
					System.out.println("Your card has been blocked..Please contact your bank");
					String sql4="update atm_card set CARD_STATUS = "+ 0 +" where CARD_NO="+this.Card_number;
					ResultSet p = st.executeQuery(sql4);
					Validation();
				}
			}
		}
		}
		catch(Exception x)
		{
			System.out.println("\nInvalid card");
			Validation();
		}
		
		
	}
	
	public void menu()
	{
		int choice;
		Receipt R = new Receipt();
		Deposit d = new Deposit();
		MoneyTransfer m=new MoneyTransfer();
		System.out.println("---------------------");
		System.out.println("\tMenu");
		System.out.println("---------------------");
		System.out.println("1: Statement \n2: Withdraw \n3: Transfer\n4: Deposit \n5: Log Out");
		System.out.println("---------------------\n");
		Scanner myObj = new Scanner(System.in);
		choice= myObj.nextInt();
		switch(choice)
		{
			case 1: //Statement
					Receipt_no++;
					R.receipt(Receipt_no, 1, Account_no);
					break;
			case 2: //withdraw
				//R.receipt(1234, 1, Card_number);
				break;
			case 3: 
				m.transfer(Account_no);
				break;
			case 4: 
				d.deposit1(Card_number);
				break;
			case 5: Log_Out l= new Log_Out();
				l.logout();
				break;
			default:
				System.out.println("Invalid Entry");
				menu();
		}
	}
	
	
	
	public static void main(String[] jjjjjj) {
		ATM_Machine a1=new ATM_Machine();
		//withdrawal w = new withdrawal();
		//Receipt R = new Receipt();
		
		//R.receipt(0,7,2);
		//w.test("hellollll");
		a1.Validation();
		//a1.main_menu();
		// TODO Auto-generated method stub

	}

}
