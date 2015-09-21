package functions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.AccountsManagment;
import system.Coordinator;
import system.TellerQuery;
import utils.GeneralFunctions;
import utils.TelMsgPusher;

import comon.OCRequest;
import comon.StaticVars;

import entity.Account;
import entity.Customers;
import entity.EmployeeAction;

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

	public void alert(OCRequest req) throws IOException {
		/*
		 * if the requests are approved the request operation is executed and
		 * the teller in charge of commiting it is notifyed
		 */
		if (req.getResponse().equals(StaticVars.ACCEPT)) {
			EmployeeAction ea = new EmployeeAction();
			ea.setEmpId(empId);
			ea.setActionType(req.getReqType());
			if (req.getReqType().equals(StaticVars.OPEN)) {
				AccountsManagment am = new AccountsManagment();
				// open acc, +1 to the customers accounts number they poses
				String accNr = am.openAccount(req.getAccType(),
						req.getClientIdsList());
				ea.setAccountId1(accNr);
				ea.setCustomerId(req.getClientIdsList());
				super.requestOpenAcc(ea);
			}
			if (req.getReqType().equals(StaticVars.CLOSE)) {
				AccountsManagment am = new AccountsManagment();
				// close account and decrease accounts number customers poses
				am.closeAccount(req.getAccFromNr());
				ea.setAccountId1(req.getAccFromNr());
				ea.setCustomerId(req.getClientIdsList());
				super.requestCloseAcc(ea);
			}
			if (req.getReqType().equals(StaticVars.PLUS_1K_DEP)) {
				TellerQuery tq = new TellerQuery();
				long trnr = tq.deposite(req.getAccFromNr(), req.getAmount());
				if (trnr > 0) {
					ea.setEmpId(req.getTellerId());
					ea.setAccountId1(req.getAccFromNr());
					ea.setCustomerId(req.getClientIdsList());
					ea.setTrNr(trnr);
					ea.setAmount(req.getAmount());
					super.deposite(ea);
				}
			}
			if (req.getReqType().equals(StaticVars.PLUS_1K_WITH)) {
				TellerQuery tq = new TellerQuery();
				long trnr = tq.withdraw(req.getClientIdsList().get(0),
						req.getAccFromNr(), req.getAmount());
				if (trnr > 0) {
					ea.setEmpId(req.getTellerId());
					ea.setAccountId1(req.getAccFromNr());
					ea.setCustomerId(req.getClientIdsList());
					ea.setTrNr(trnr);
					ea.setAmount(req.getAmount());
					super.withdraw(ea);
				}
			}
			if (req.getReqType().equals(StaticVars.PLUS_1K_TRANS)) {
				TellerQuery tq = new TellerQuery();
				long trnr = tq.transfer(req.getClientIdsList().get(0),
						req.getAccFromNr(), req.getAccToNr(), req.getAmount(),
						't');
				if (trnr > 0) {
					ea.setEmpId(req.getTellerId());
					ea.setAccountId1(req.getAccFromNr());
					ea.setAccountId2(req.getAccToNr());
					ea.setCustomerId(req.getClientIdsList());
					ea.setTrNr(trnr);
					ea.setAmount(req.getAmount());
					super.transfer(ea);
				}
			}
			if (req.getReqType().equals(StaticVars.PLUS_6_ACC)) {
				AccountsManagment am = new AccountsManagment();
				String accNr = am.openAccount(req.getAccType(),
						req.getClientIdsList());
				ea.setEmpId(req.getTellerId());
				ea.setAccountId1(accNr);
				ea.setCustomerId(req.getClientIdsList());
				super.requestOpenAcc(ea);
			}
		}// if approved req
			// if( req is completed) remove ocr from coordinator list
		Coordinator.deleteOCR(req);
		TelMsgPusher tp = new TelMsgPusher();
		tp.returnResponse(req, wsSession);
		// send the request results to the tellers server side
	}

	public List<String> openAccountReq(List<String> personalIds, char accType) {
		logger.info("On teller function, looking to open account");

		GeneralFunctions gf = new GeneralFunctions();
		List<String> problematicClients = gf.registrationCheck(personalIds);
		if (problematicClients.size() == 0) {
			// Coordinator list = new Coordinator();
			OCRequest req;
			if (gf.accountsCountCheck(personalIds).size() == 0) {
				req = new OCRequest(empId, personalIds, StaticVars.OPEN,
						accType);
				logger.info("{} requires submited ", StaticVars.OPEN);
			} else {// accounts count check
				req = new OCRequest(empId, personalIds, StaticVars.PLUS_6_ACC,
						accType);
				logger.info("{} requires man confirmation",
						StaticVars.PLUS_6_ACC);
			}
			Coordinator.addOCR(req);
			return null;
		}
		return problematicClients;
	}

	public String accCloseAccCheck(String accNr) {
		// check if accountExist and is empty
		GeneralFunctions gf = new GeneralFunctions();
		Account acc = gf.getAccount(accNr);
		if (acc == null) {
			logger.info("ACCOUNT {} was not founr in db", accNr);
			return "ACCOUNT DOES NOT EXISTS";
		} else if (acc.getBalance() > 0.5) {
			logger.info("Account {} could not be closed");
			return "ACCOUNT IS NOT EMPTY, WITHDRAW REMAINING CREDIT";
		}
		assert (acc.getBalance() <= 0.5);
		return null;
	}

	public List<String> closeAccountReq(List<String> personalIds, String accNr) {
		// check if all client id are registered
		GeneralFunctions gf = new GeneralFunctions();
		List<String> problematicClients = gf.registrationCheck(personalIds);
		if (problematicClients.size() != 0) {
			logger.info("SOME CO-OWNERS SUBMITED RESULT NOT REGISTERED");
			return problematicClients;
		}
		// Coordinator list = new Coordinator();
		TellerQuery tq = new TellerQuery();
		// check if there are acc owners whose id was not submitted
		problematicClients = tq.clientAccountCompatibility(personalIds, accNr);
		if (problematicClients == null) {
			OCRequest req = new OCRequest(empId, personalIds, StaticVars.CLOSE,
					accNr);
			Coordinator.addOCR(req);
		} else if (problematicClients.size() > 0) {
			logger.info("REQUIRED CONFIRMATION FROM OTHER CO-OWNERS");
			return problematicClients;
		}

		return null;

	}

	/* transaction functions */
	public String deposite(String accNr, double amount, String note) {
		if (amount <= 0) {
			return "Deposition Ammount Is Very Low";
		}
		TellerQuery tq = new TellerQuery();
		String regCheck = tq.checkDepositeRegularity(accNr, amount);
		if (regCheck == null) {
			if (amount >= 1000) {// alert the manager to confirm
				// Coordinator cord = new Coordinator();
				OCRequest req = new OCRequest(empId, null,
						StaticVars.PLUS_1K_DEP, accNr, null, amount, note);
				Coordinator.addOCR(req);
				return "Transactions Over 1000, Require Manager Confirmation";
			} else {
				long trNr = tq.deposite(accNr, amount);
				if (trNr > 0) {
					EmployeeAction ea = new EmployeeAction(StaticVars.DEPOSITE,
							note, empId, trNr);
					ea.setAmount(amount);
					ea.setAccountId1(accNr);
					ea.setNote(note);
					super.deposite(ea);
					return "Transaction Completed, tr.# : " + trNr;
				} else {
					return "Transaction Not Completed, Some Error Ocoured";
				}

			}
		}
		// pracically if acc does not exist return response
		return regCheck;

	}

	public String withdraw(String personalId, String accNr, double amount) {
		if (amount <= 0) {
			return "Deposition Ammount Is Very Low";
		}

		TellerQuery tq = new TellerQuery();
		String regCheck = tq.checkWithdrawRegularity(personalId, accNr, amount);
		if (regCheck == null) {
			if (amount >= 1000) {// alert the manager to confirm
				ArrayList<String> ocrAl = new ArrayList<>();
				// Coordinator cord = new Coordinator();
				ocrAl.add(personalId);
				OCRequest req = new OCRequest(empId, ocrAl,
						StaticVars.PLUS_1K_WITH, accNr);
				Coordinator.addOCR(req);
				return "Transactions Over 1000, Require Manager Confirmation";
			} else {
				long trNr = tq.withdraw(personalId, accNr, amount);
				if (trNr > 0) {
					EmployeeAction ea = new EmployeeAction(StaticVars.WITHDRAW,
							"", empId, trNr);
					List<String> sls = new ArrayList<>();
					sls.add(personalId);
					ea.setCustomerId(sls);
					ea.setAmount(amount);
					ea.setAccountId1(accNr);
					super.withdraw(ea);
					return "Transaction Completed, tr.# : " + trNr;
				} else {
					return "Transaction Not Completed, Some Error Ocoured";
				}
			}
		}
		return regCheck;
	}

	public String transfer(String personalId, String accFrom, String accTo,
			double amount) {
		if (amount <= 0) {
			return "Deposition Ammount Is Very Low";
		}

		TellerQuery tq = new TellerQuery();
		String regCheck = tq.checkTransferRegularity(personalId, accFrom,
				accTo, amount, 't');
		if (regCheck == null) {
			if (amount >= 1000) {// alert the manager to confirm
				ArrayList<String> ocrAl = new ArrayList<>();
				// Coordinator cord = new Coordinator();
				ocrAl.add(personalId);
				OCRequest req = new OCRequest(empId, ocrAl,
						StaticVars.PLUS_1K_WITH, accFrom, accTo, amount, null);
				Coordinator.addOCR(req);
				return "Transactions Over 1000, Require Manager Confirmation";
			} else {
				long trNr = tq
						.transfer(personalId, accFrom, accTo, amount, 't');
				if (trNr > 0) {
					EmployeeAction ea = new EmployeeAction(StaticVars.TRANSFER,
							"", empId, trNr);
					List<String> sls = new ArrayList<>();
					sls.add(personalId);
					ea.setCustomerId(sls);
					ea.setAmount(amount);
					ea.setAccountId1(accFrom);
					ea.setAccountId2(accTo);
					super.requestOpenAcc(ea);
					return "Transaction Completed, tr.# : " + trNr;
				} else {
					return "Transaction Not Completed, Some Error Ocoured";
				}
			}
		}
		return regCheck;
	}

	/* teller main page functions */
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
		if (cids == null)
			return null;
		for (String s : cids) {
			acl.add(gf.getAccount(s));
		}
		return acl;
	}

	public Account getAccount(String accId) {
		TellerQuery tq = new TellerQuery();
		return tq.getAccount(accId);
	}

	/* end main page functions */
	public Session getWsSession() {
		return wsSession;
	}

	public void setWsSession(Session wsSession) {
		this.wsSession = wsSession;
	}

	public int getEmpId() {
		return empId;
	}

	public Customers getCustomer(String persId) {
		GeneralFunctions gf = new GeneralFunctions();
		return gf.getCustomer(persId);
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public List<Customers> getClients(String fname, String lname,
			String address, String phone, String eMail, String password) {
		TellerQuery tq = new TellerQuery();

		return tq.getAllClients(fname, lname, address, phone, eMail, password);
	}

	// TODO client personal data details

	public String deleteCustomer(String persId) {
		TellerQuery tq = new TellerQuery();
		String response = tq.deleteCustomer(persId);
		if (response == StaticVars.DONE) {
			List<String> persIdList = new ArrayList<>();
			persIdList.add(persId);
			EmployeeAction ea = new EmployeeAction();
			ea.setEmpId(empId);
			ea.setActionType(StaticVars.REG_USR);
			ea.setCustomerId(persIdList);
			super.registerClient(ea);
		}
		return response;
	}

	public String register(String persId, String fname, String lname,
			String eMail, String bdate, String address, String phone, String psw)
			throws ParseException {
		logger.info("IN teller functions");
		TellerQuery tq = new TellerQuery();
		String response = tq.registerCustomer(persId, fname, lname, eMail,
				bdate, address, phone, psw);

		if (response != null && response.length() == 0) {
			List<String> persIdList = new ArrayList<>();
			persIdList.add(persId);
			EmployeeAction ea = new EmployeeAction();
			ea.setEmpId(empId);
			ea.setActionType(StaticVars.REG_USR);
			ea.setCustomerId(persIdList);
			super.registerClient(ea);
		}
		return response;
	}

	public String alter(String persId, String fname, String lname,
			String eMail, String bdate, String address, String phone,
			String password) {

		TellerQuery tq = new TellerQuery();
		String response = tq.alter(persId, fname, lname, eMail, bdate, address,
				phone, password);

		if (response != null && response.length() == 0) {
			List<String> ls = new ArrayList<>();
			ls.add(persId);
			EmployeeAction ea = new EmployeeAction();
			ea.setCustomerId(ls);
			ea.setActionType(StaticVars.ALTER_USR);
			ea.setEmpId(empId);

			super.registerClient(ea);
		}
		return response;
	}

}
