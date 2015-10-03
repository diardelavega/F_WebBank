package functions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import system.ClientQuery;
import system.Coordinator;
import system.TellerQuery;
import utils.CliMsgPusher;
import utils.GeneralFunctions;
import comon.OCRequest;
import comon.StaticVars;
import entity.Account;
import entity.Transaction;

public class CustomerFunctions {

	private String personalId;
	private Session session;
	private List<String> accountNrs;

	public CustomerFunctions() {
		super();
		this.personalId = "0000000000";
	}

	public CustomerFunctions(String personalId) {
		super();
		this.personalId = personalId;
	}

	public String transfer(String persId, String accFrom, String accTo,
			double amount) {
		if (amount <= 0) {
			return "Deposition Ammount Is Very Low";
		}

		TellerQuery tq = new TellerQuery();
		String regCheck = tq.checkTransferRegularity(persId, accFrom, accTo,
				amount);
		if (regCheck == null) {
			if (amount >= 1000) {
				System.out.println("+++++1000");
				List<String> ocrAl = new ArrayList<>();
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
				long trNr = tq.transfer(persId, accFrom, accTo, amount, 'c');
				if (trNr > 0) {
					System.out.println("complete!!");
					return "Successful Completion tr nr: " + trNr;

				} else {
					System.out
							.println("Problem During Execution, Transaction Not Completed!!");
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

		if (t1 == null || t1.equals("")) {
			return cq.getTransactions(accounts);
		} else {
			if (t2 == null || t2.equals("")) {
				return cq.getTransactions(accounts, t1);
			} else {
				return cq.getTransactions(accounts, t1, t2);
			}
		}
	}

	public List<Transaction> getTransactions(String searchPersId, Date t1,
			Date t2) {
		ClientQuery cq = new ClientQuery();
		if (t1 == null || t1.equals("")) {
			return cq.getTransactions(searchPersId);
		} else {
			if (t2 == null || t2.equals("")) {
				return cq.getTransactions(searchPersId, t1);
			} else {
				return cq.getTransactions(searchPersId, t1, t2);
			}
		}
	}

	public List<Object[]> getBalance(String searchPersId, Date t1, Date t2) {
		ClientQuery cq = new ClientQuery();

		if (t1 == null || t1.equals("")) {
			return cq.getBalance(searchPersId);
		} else {
			if (t2 == null || t2.equals("")) {
				return cq.getBalance(searchPersId, t1);
			} else {
				return cq.getBalance(searchPersId, t1, t2);
			}
		}
	}

	public List<Object[]> getBalance(List<String> sl, Date t1, Date t2) {
		if (sl.size() == 0) {
			return null;
		}
		ClientQuery cq = new ClientQuery();
		if (t1 == null || t1.equals("")) {
			return cq.getBalance(sl);
		} else {
			if (t2 == null || t2.equals("")) {
				return cq.getBalance(sl, t1);
			} else {
				return cq.getBalance(sl, t1, t2);
			}
		}
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

	public List<String> getAccountNrs() {
		return accountNrs;
	}

	public void setAccountNrs(List<String> accountNrs) {
		this.accountNrs = accountNrs;
	}

	public List<Account> getCustomersAccounts(String persId) {
		GeneralFunctions gf = new GeneralFunctions();
		List<Account> acl = null;
		accountNrs = gf.clientsAccounts(personalId);
		if (accountNrs != null) {
			acl = new ArrayList<>();
			for (String s : accountNrs) {
				acl.add(gf.getAccount(s));
			}
			pushAccounts(acl);
			return acl;
		}
		return null;
	}

	public void addOneAcc(String accNr) {
		accountNrs.add(accNr);
	}

	public void delOneAcc(String accNr) {
		accountNrs.remove(accNr);
	}

	public void fillAllAccounts() {
		// Fill coordinator accounts
		for (String s : accountNrs)
			Coordinator.addAccount(s, personalId);
	}

	public void emptyAllAccounts() {
		for (String s : accountNrs)
			Coordinator.deleteAccount(s);
	}

	public void pushAccounts(List<Account> accl) {
		CliMsgPusher cmp = new CliMsgPusher();
		cmp.pushAccounts(session, accl);
	}
}
