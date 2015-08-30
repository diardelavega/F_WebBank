package functions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.Coordinator;
import system.TellerQuery;
import utils.GeneralFunctions;
import utils.PushClientSide;
import comon.AccountType;
import comon.OCRequest;
import comon.Response;
import comon.StaticVars;
import entity.Account;
import entity.EmployeeAction;
import entity.Transaction;

/**
 * @author Administrator class teller functions represents the functionality of
 *         a teller. the open and close account require the collaboration of a
 *         manager.
 *
 */
public class TellerFunctions extends EmployeeFunctions {
	Logger logger = LoggerFactory.getLogger(TellerFunctions.class);
	private int empId;
	private Session wsSession;

	public TellerFunctions(int empId) {
		super();
		this.empId = empId;
	}

	public TellerFunctions() {
		super();
		this.empId = 000;
	}

	public void alert(String requestType, String response, String accNr,
			String note) {
		// TODO implement an alert for client side the operation was evaluated
		PushClientSide pcs = new PushClientSide();
		pcs.pushToClient(requestType, response, accNr, note);
		EmployeeAction ea;
		if (response.equals(StaticVars.REQ_APPROVE)) {
			if (requestType.equals(StaticVars.OPEN)) {
				logger.info("MENAGER \" {}. request to open account :{}.\" .",
						response, accNr);
				ea = new EmployeeAction(accNr, StaticVars.OPEN, "", empId);
				super.requestOpenAcc(ea);
			}
			if (requestType.equals(StaticVars.CLOSE)) {
				logger.info("MENAGER \" {}. request to close account :{}.\" .",
						response, accNr);
				ea = new EmployeeAction(accNr, StaticVars.CLOSE, "", empId);
				super.requestOpenAcc(ea);
			}
		} else if (response.equals(StaticVars.REQ_DENIED)) {
			logger.info("{} for the operation {} under the reason '{}'",
					response, requestType, note);
		} else if (response.equals(StaticVars.UNREG_USR)) {
			logger.info("{}  for the operation {}. ", response, requestType);
		} else {
			logger.info("note :{}  , {}. ", note, response);
		}
	}

	private void logHelper(String response, String operation, String accNr) {
		EmployeeAction ea = new EmployeeAction(accNr, operation, "", empId);
		super.requestOpenAcc(ea);
	}

	public void openAccount(List<String> personalIds, AccountType accType) {

		if (personalIds.size() > 4) {
			System.out.println("TOO MANY CO-OWNERS");
		} else {
			GeneralFunctions gf = new GeneralFunctions();
			if (gf.registrationCheck(personalIds).size() == 0) {
				Coordinator list = new Coordinator();
				OCRequest req;
				if (gf.accountsCountCheck(personalIds).size() == 0) {
					req = new OCRequest(empId, personalIds, StaticVars.OPEN,
							accType);
				} else {// accounts count check
					req = new OCRequest(empId, personalIds,
							StaticVars.PLUS_6_ACC, accType);
				}
				list.addOCR(req);
			} else {// registration check
				alert(StaticVars.OPEN, StaticVars.UNREG_USR, null,
						"unregistere users");
			}
		}
	}

	public void closeAccount(List<String> personalIds, String accNr) {
		Coordinator list = new Coordinator();
		TellerQuery tq = new TellerQuery();
		List<String> remaining = tq.clientAccountCompatibility(personalIds,
				accNr);
		GeneralFunctions gf;
		if (remaining != null) {
			System.out.println("Required confirmation from the following :");
			gf = new GeneralFunctions();
			for (String s : remaining) {
				gf.getCustomer(s).print();
			}
		} else {
			OCRequest req = new OCRequest(empId, personalIds, StaticVars.CLOSE,
					accNr);
			list.addOCR(req);
		}

	}

	public void register(String persId, String fname, String lname,
			String eMail, String bDate, String address, String Phone, String psw)
			throws ParseException {
		CustomerFunctions cf = new CustomerFunctions();
		cf.dataValidity(persId, fname, lname, eMail, bDate, address, Phone, psw);
		cf.register(persId, fname, lname, eMail, bDate, address, Phone, psw);
		EmployeeAction ea = new EmployeeAction(persId, StaticVars.REGISTER,
				empId);
		super.registerClient(ea);
	}

	public void deposite(String accNr, double amount, String note) {
		TellerQuery tq = new TellerQuery();

		if (tq.checkDepositeRegularity(accNr, amount)) {
			if (amount >= 1000) {// alert the manager to confirm
				Coordinator cord = new Coordinator();
				OCRequest req = new OCRequest(empId, null,
						StaticVars.PLUS_1K_DEP, accNr, null, amount, note);
				cord.addOCR(req);
			} else {
				long trNr = tq.deposite(accNr, amount);
				if (trNr > 0) {
					EmployeeAction ea = new EmployeeAction(StaticVars.DEPOSITE,
							note, empId, trNr);
					ea.setAmount(amount);
					ea.setAccountId(accNr);
					super.deposite(ea);
				}
			}
		}

	}

	public void withdraw(String personalId, String accNr, double amount,
			String note) {
		TellerQuery tq = new TellerQuery();
		if (tq.checkWithdrawRegularity(personalId, accNr, amount)) {
			if (amount >= 1000) {// alert the manager to confirm
				ArrayList<String> ocrAl = new ArrayList<>();
				Coordinator cord = new Coordinator();
				ocrAl.add(personalId);
				OCRequest req = new OCRequest(empId, ocrAl,
						StaticVars.PLUS_1K_WITH, accNr);
				cord.addOCR(req);
			} else {

				long trNr = tq.withdraw(personalId, accNr, amount);
				if (trNr > 0) {
					EmployeeAction ea = new EmployeeAction(StaticVars.WITHDRAW,
							note, empId, trNr);
					ea.setCustomerId(personalId);
					ea.setAmount(amount);
					ea.setAccountId(accNr);
					super.withdraw(ea);
				}
			}
		}
	}

	public void transfer(String personalId, String accFrom, String accTo,
			double amount, String note) {
		// TODO check if amount >=1000; if yes
		// check transaction regularity; if yes
		// ask manager for permission; if confirmed
		// proceed to action;

		TellerQuery tq = new TellerQuery();
		if (tq.checkTransferRegularity(personalId, accFrom, accTo, amount, 't')) {
			if (amount >= 1000) {// alert the manager to confirm
				ArrayList<String> ocrAl = new ArrayList<>();
				Coordinator cord = new Coordinator();
				ocrAl.add(personalId);
				OCRequest req = new OCRequest(empId, ocrAl,
						StaticVars.PLUS_1K_WITH, accFrom, accTo, amount, null);
				cord.addOCR(req);
			} else {

				long trNr = tq
						.transfer(personalId, accFrom, accTo, amount, 't');
				if (trNr > 0) {
					EmployeeAction ea = new EmployeeAction(StaticVars.TRANSFER,
							note, empId, trNr);
					ea.setCustomerId(personalId);
					ea.setAmount(amount);
					ea.setAccountId(accFrom);
					super.transfer(ea);
				}
			}
		}
	}

	public List<Object> getAccountClients(String accountId) {
		GeneralFunctions gf = new GeneralFunctions();
		List<String> accIds = gf.accountsClients(accountId);

		List<Object> custs = null;
		if (accIds.size() != 0) {
			custs = new ArrayList<>();
			TellerQuery tq = new TellerQuery();
			for (String c : accIds) {
				Object[] a = tq.getCustomerDetails(c);
				custs.add(a);
			}
		}
		return custs;
	}

	public List<Account> getClientAccounts(String personalId) {
		GeneralFunctions gf = new GeneralFunctions();
		List<Account> acl = new ArrayList<>();
		List<String> cids = gf.clientsAccounts(personalId);
		for (String s : cids) {
			acl.add(gf.getAccount(s));
		}
		return acl;
	}

	public Account getAccount(String accId) {
		TellerQuery tq = new TellerQuery();
		return tq.getAccount(accId);
	}

	public int getEmpId() {
		return empId;
	}

	public Session getWsSession() {
		return wsSession;
	}

	public void setWsSession(Session wsSession) {
		this.wsSession = wsSession;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

}
