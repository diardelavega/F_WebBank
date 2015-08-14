package functions;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import system.ClientQuery;
import system.TellerQuery;
import utils.GeneralFunctions;
import comon.Transfer;
import entity.Customers;

public class CustomerFunctions {

	private String personalId;
	
	public CustomerFunctions() {
		super();
		this.personalId = "0000000000";
	}
	
	public CustomerFunctions(String personalId) {
		super();
		this.personalId = personalId;
	}

	public int checkPeraonalId(String id) {
		ClientQuery cq = new ClientQuery();
		return cq.checkClientId(id);
	}

	public String dataValidity(String persId, String fname, String lname,
			String eMail, String bDate, String address, String Phone, String psw) {
		// TODO check name, surname id etc. conditions
		// for every irregularity append to a string the error msg
		return null;
	}

	public void register(String persId, String fname, String lname,
			String eMail, String bDate, String address, String phone, String psw)
			throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
		Date parsedDate = dateFormat.parse(bDate);
		Timestamp bdate = new java.sql.Timestamp(parsedDate.getTime());

		Customers c = new Customers(persId, 0, eMail, fname, lname, bdate,
				address, phone, psw, 0);
		ClientQuery cq = new ClientQuery();
		cq.register(c);
	}

	public void transfer(String personalId, String accFrom, String accTo,
			double amount, String note) {
		TellerQuery tq = new TellerQuery();
		tq.transfer(personalId, accFrom, accTo, amount, 'o');
		
	}

	public String info(String accountId) {
		GeneralFunctions gf = new GeneralFunctions ();
		gf.getAccount(accountId).print();
		return null;
	}

//	public String logIn(String usr, String psw) {
//		// TODO check data validity
//		ClientQuery cq = new ClientQuery();
//		cq.dbLogInCheck(usr, psw);
//
//		return cq.dbLogInCheck(usr, psw);
//
//	}

	public String dataValidity(Customers c) {
		return null;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
}
