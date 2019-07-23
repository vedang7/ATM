package atmmachine;

import atmmachine.ATM_Machine;

public class Log_Out {
	public void logout()
	{
		System.out.println("Logged Out");
		ATM_Machine a1= new ATM_Machine();
		a1.account_number="Null";
		a1.Validation();
	}

}
