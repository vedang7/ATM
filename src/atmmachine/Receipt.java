
package atmmachine;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import atmmachine.*;

public class Receipt {
	static final String DB_URL = "jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:TRAIN1";
	static final String USER = "amdias";
	static final String PASS = "password";

	public void receipt(int receipt_no, int category, int accno) {

		switch (category) {
		case 1:
			System.out.println("Statement Printed Successfully");
			try {

// Connection Object
				Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

// create the statement object
				PreparedStatement p = con
						.prepareStatement("SELECT account_no, account_balance FROM account where account_no = ?");

				p.setInt(1, accno);

// execute query
				ResultSet rs = p.executeQuery();
				while (rs.next()) {

// Retrieve by column name
					int id = rs.getInt("account_no");
					int amount = rs.getInt("account_balance");

// Display
					
					 
					 Date date= new Date();
					 
					 long time = date.getTime();
					     System.out.println("Time in Milliseconds: " + time);
					 
					 Timestamp ts = new Timestamp(time);
					System.out.print("Receipt#: " + receipt_no);
					System.out.println(" Timestamp:"+ts);

					System.out.println("Account#: " + id);
					System.out.println("Current Balance: ₹ " + amount);

				}
			} catch (Exception e) {
				System.out.println(e);

			}
			finally {
				Log_Out l1= new Log_Out();
				l1.logout();
			}
			break;

		case 2:
			System.out.println("Withdrawal done Successfully");
			try {

// Connection Object
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:TRAIN1", "amdias", "password");
//SQL QUERY
				String query = "select TRANSACTION_ID ,\n" + "TRANSACTION_TYPE ,\n" + "transaction.ACCOUNT_NO ,\n"
						+ "TIMESTAMPS ,\n" + "TRANSACTION_AMT,account.account_balance as cur from transaction \n"
						+ "join account\n" + "on account.account_no=transaction.account_no\n"
						+ "where transaction.account_no = ? and \n"
						+ "timestamps = (select max(timestamps) from transaction\n"
						+ "where transaction.account_no = ?)\n" + "";

// create the statement object
				PreparedStatement p = con.prepareStatement(query);
				p.setInt(1, accno);
				p.setInt(2, accno);

// execute query
				ResultSet rs = p.executeQuery();
				while (rs.next()) {

// Retrieve by column name
					int id = rs.getInt("account_no");
					String trans_type = rs.getString("Transaction_type");
					String time = rs.getString("timestamps");
					int trans_amount = rs.getInt("transaction_amt");
					int current_balance = rs.getInt("cur");

//DISPLAY
					System.out.println("Receipt#: " + receipt_no);
					get_timestamp(time);

					System.out.println("Account#: " + id);
					System.out.println("Transaction_type: " + trans_type);
					System.out.println("Transaction_amount: ₹ " + trans_amount);
					System.out.println("Current Balance: ₹ " + current_balance);

// System.out.print(", First: " + first);
// System.out.println(", Last: " + last);

				}
			} catch (Exception e) {
				System.out.println(e);
			}
			break;
		case 3:
			System.out.println("Transfer done Successfully");

			try {

// Connection Object
				Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:TRAIN1", "amdias", "password");
//SQL QUERY
				String query = "select transaction.TRANSACTION_ID ,\n" + "TRANSACTION_TYPE ,\n"
						+ "transaction.ACCOUNT_NO ,\n" + "TIMESTAMPS ,\n"
						+ "TRANSACTION_AMT,transfer.beneficiary_acc,account.account_balance as cur from transaction \n"
						+ "join account\n" + "on account.account_no=transaction.account_no\n" + "inner join transfer\n"
						+ "on transfer.transaction_id=transaction.transaction_id\n"
						+ "where transaction.account_no= ? and \n"
						+ "timestamps = (select max(timestamps) from transaction\n"
						+ "where transaction.account_no= ?)";

// create the statement object
				PreparedStatement p = con.prepareStatement(query);

				p.setInt(1, accno);
				p.setInt(2, accno);
				ResultSet rs = p.executeQuery();
				while (rs.next()) {

// Retrieve by column name
					int id = rs.getInt("account_no");
					String trans_type = rs.getString("Transaction_type");
					String time = rs.getString("timestamps");
					int beneficiary = rs.getInt("Beneficiary_acc");
					int trans_amount = rs.getInt("transaction_amt");
					int current_balance = rs.getInt("cur");

// Display
					System.out.println("Receipt#: " + receipt_no);
					get_timestamp(time);
					System.out.println("Account#: " + id);
					System.out.println("Transaction_type: " + trans_type);
					System.out.println("Transferred To: " + beneficiary);
					System.out.println("Transfer_amount: ₹ " + trans_amount);
					System.out.println("Current Balance: ₹ " + current_balance);

				}
			} catch (Exception e) {
				System.out.println(e);

			}
			break;
		case 4:
			System.out.println("Money deposited Successfully");
			try {

// Connection Object
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(
						"jdbc:oracle:thin:@OSCTrain1DB01.oneshield.com:1521:TRAIN1", "amdias", "password");

				String query = "select TRANSACTION_ID ,\n" + "TRANSACTION_TYPE ,\n" + "transaction.ACCOUNT_NO ,\n"
						+ "TIMESTAMPS ,\n" + "TRANSACTION_AMT,account.account_balance as cur from transaction \n"
						+ "join account\n" + "on account.account_no=transaction.account_no\n"
						+ "where transaction.account_no = ? and \n"
						+ "timestamps = (select max(timestamps) from transaction\n"
						+ "where transaction.account_no = ?)\n" + "";

				PreparedStatement p = con.prepareStatement(query);

				p.setInt(1, accno);
				p.setInt(2, accno);
				ResultSet rs = p.executeQuery();
				while (rs.next()) {

// Retrieve by column name
					int id = rs.getInt("account_no");
					String trans_type = rs.getString("Transaction_type");
					String time = rs.getString("timestamps");
					int trans_amount = rs.getInt("transaction_amt");
					int current_balance = rs.getInt("cur");

					System.out.println("Receipt#: " + receipt_no);
					get_timestamp(time);

					System.out.println("Account#: " + id);
					System.out.println("Transaction_type: " + trans_type);
					System.out.println("Transaction_amount: ₹ " + trans_amount);
					System.out.println("Current Balance: ₹ " + current_balance);

				}
			} catch (Exception e) {
				System.out.println(e);

			}
			break;

		}

	}

	static void get_timestamp(String timestamp) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date fechaNueva = format.parse(timestamp);

			System.out.println("Timestamp: " + format.format(fechaNueva));
		} catch (Exception e) {
			System.out.println("Error " + e);
		}

	}
}
