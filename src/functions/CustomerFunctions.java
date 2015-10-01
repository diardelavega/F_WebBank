package functions;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import system.ClientQuery;
import system.Coordinator;
import system.TellerQuery;
import utils.GeneralFunctions;
import comon.OCRequest;
import comon.StaticVars;
import comon.Transfer;
import entity.Customers;
import entity.Transaction;

public class CustomerFunctions {

	private String personalId;
	private Session session;

	public CustomerFunctions() {
		super();
		this.personalId = "0000000000";
	}

	public CustomerFunctions(String personalId) {
		super();
		this.personalId = personalId;
	}

	
	
	public String transfer(String accFrom, String accTo, String persId,
			Double amount) {
		if (amount <= 0) {
			return "Deposition Ammount Is Very Low";
		}

		TellerQuery tq = new TellerQuery();
		String regCheck = tq.checkTransferRegularity(persId, accFrom, accTo,
				amount, 'c');

		if (regCheck == null) {
			if (amount >= 1000) {// alert the manager to confirm
				ArrayList<String> ocrAl = new ArrayList<>();
				// Coordinator cord = new Coordinator();
				ocrAl.add(persId);
				OCRequest req = new OCRequest();
				req.setClientId(persId);
				req.setReqType(StaticVars.PLUS_1K_WITH);
				req.setAccFromNr(accFrom);
				req.setAccToNr(accTo);
				req.setAmount(amount);
				Coordinator.addOCR(req);
				return "Transactions Over 1000, Require Manager Confirmation";
			} else {
				long trNr = tq
						.transfer(personalId, accFrom, accTo, amount, 't');
				if (trNr > 0) {
					return "Successful completion";
				} else {
					return "Problem During Execution, Transaction Not Completed";
				}
			}
		}
		return regCheck;

	}

	public List<Transaction> getTransactions(List<String> accounts, Date t1,
			Date t2) {
		if (accounts.size() == 0) {
			return null;
		}
		ClientQuery cq = new ClientQuery();
		List<Transaction> trl;
		if (t2.equals("")) {
			if (t1.equals("")) {
				// get all transactions ever
				return cq.getTransactions(accounts);
			}
		} else {
			// get transactions for one day only
			return cq.getTransactions(accounts, t1);
		}
		return cq.getTransactions(accounts, t1, t2);

	}

	public List<Transaction> getTransactions(String searchPersId, Date t1,
			Date t2) {
		ClientQuery cq = new ClientQuery();
		if (t2.equals("")) {
			if (t1.equals("")) {
				// get all transactions ever
				return cq.getTransactions(searchPersId);
			}
		} else {
			// get transactions for one day only
			return cq.getTransactions(searchPersId, t1);
		}
		return cq.getTransactions(searchPersId, t1, t2);
		// return null;
	}

	public List<Object[]> getBalance(String searchPersId, Date t1, Date t2) {
		ClientQuery cq = new ClientQuery();
		if (t2.equals("")) {
			if (t1.equals("")) {
				// get all transactions ever
				return cq.getBalance(searchPersId);
			}
		} else {
			// get transactions for one day only
			return cq.getBalance(searchPersId, t1);
		}
		return cq.getBalance(searchPersId, t1, t2);
		// return null;

	}

	public List<Object[]> getBalance(List<String> sl, Date t1, Date t2) {
		ClientQuery cq = new ClientQuery();
		if (t2.equals("")) {
			if (t1.equals("")) {
				// get all transactions ever
				return cq.getBalance(sl);
			}
		} else {
			// get transactions for one day only
			return cq.getBalance(sl, t1);
		}
		return cq.getBalance(sl, t1, t2);
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
