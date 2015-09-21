package functions;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.AccountsManagment;
import system.Coordinator;
import system.ManagerQuery;
import system.TellerQuery;
import utils.GeneralFunctions;
import utils.ManMsgHandler;
import utils.ManMsgPusher;
import comon.OCRequest;
import comon.StaticVars;
import entity.Account;
import entity.Customers;
import entity.EmployeeAction;
import entity.Transaction;

public class ManagerFunctions extends EmployeeFunctions {
	private final Logger logger = LoggerFactory
			.getLogger(ManagerFunctions.class);

	// private Coordinator lists = new Coordinator();
	private OCRequest req;
	private int empId;
	private Session wsSession;

	// private AccountsManagment accm = new AccountsManagment();

	// private List<String> clientsPersonalIds;

	public ManagerFunctions(int empId) {
		super();
		this.empId = empId;
	}

	public ManagerFunctions() {
		super();
		this.empId = 000;
	}

	public void confirmOpen(String response, String note) {
		// the coordinator should delete OCR
		String accNr = "";
		// String msgResponse = "";
		if (response.equals(StaticVars.ACCEPT)) {
			// accNr = accm.openAccount(req.getAccType(),
			// req.getClientIdsList());
			logger.info("ACCOUNT {}. is oppen", accNr);
			req.setResponse(StaticVars.ACCEPT);
			req.setNote(note);
			req.setStatusComplete();
			req.unPin();
			// ----------------------------
			EmployeeAction ea = new EmployeeAction(accNr, StaticVars.CNF_OPEN,
					"", empId);
			super.confirmOpenAcc(ea);
			// ----------------------------
		} else {
			logger.info("OPEN ACCOUNT REQEST WAS DENNIED");
			req.setResponse(StaticVars.DENIE);
			req.setNote(note);
			req.setStatusComplete();
			req.unPin();

		}
		try {
			Coordinator.reviewedOCR(req);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void confirmClose(String response, String note) {
		if (response.equals(StaticVars.ACCEPT)) {
			logger.info("CLOSE ACCOUNT {}. REQEST WAS APPROVED",
					req.getAccFromNr());
			// accm.closeAccount(req.getAccFromNr());
			req.setResponse(StaticVars.ACCEPT);
			req.setNote(note);
			req.setStatusComplete();
			// ----------------------------
			EmployeeAction ea = new EmployeeAction(req.getAccFromNr(),
					StaticVars.CNF_CLOSE, "", empId);
			super.confirmCloseAcc(ea);
			// ----------------------------
		} else {
			logger.info("CLOSE ACCOUNT {}. REQEST WAS DENNIED",
					req.getAccFromNr());
			req.setResponse(StaticVars.DENIE);
			req.setNote(note);
			req.setStatusComplete();
		}
		try {
			Coordinator.reviewedOCR(req);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void confirmTransaction(String response, String note)
			throws IOException {
		if (response.equals(StaticVars.ACCEPT)) {
			logger.info("TRANSACTION {} WAS ACCEPTED", req.getReqType());
			req.setResponse(StaticVars.ACCEPT);
		} else {
			logger.info("TRANSACTION {} WAS DENNIED", req.getReqType());
			req.setResponse(StaticVars.DENIE);
		}
		req.setNote(note);
		req.setStatusComplete();
		Coordinator.reviewedOCR(req);
	}

	// ------------------
	public void returnRequest() {
		if (req != null) {
			req.unPin();
			req.setStatusInComplete();
		}
	}

	public void alert(String reqType, String d2, String ed) {
		// to be summoned in every ocr addition
		logger.info("MANAGER !!! A NEW REQUEST ARIVED IN LINE");
		// System.out.println("MANAGER !!! A NEW REQUEST ARIVED IN LINE");
		// TODO send an alert msg to the client side manager
		// AI choice
		// artificialChoise();
		ManMsgPusher mmp = new ManMsgPusher();
		mmp.requestNotifyer(wsSession, reqType, d2, ed);
	}

	public void getNextOCR() {
		req = Coordinator.getNextOCR(empId);

		if (req == null) {
			logger.info("NO NEW REQUEST AVAILABLE");
		} else {
			req.setLastManagerToConsiderIt(empId);
			req.pin();
		}
		// TODO send OCR data

	}

	public void getForceOCR() {
		req = Coordinator.getForceNextOCR();
		req.setLastManagerToConsiderIt(empId);
		req.pin();
	}

	public void getOCR() {
		if (req != null) {
			req.unPin();
		}
		getNextOCR();
		if (req == null) {
			getForceOCR();
		}
		if (req == null) {
			logger.warn("NO OCRS AQUIRED, PROBABLY THERE ISNT ONE");
		} else {
			logger.info("Manager empId is : {}",empId);
			req.setLastManagerToConsiderIt(empId);
		}
	}

	public void checkClients() {
		GeneralFunctions gf = new GeneralFunctions();
		for (String s : req.getClientIdsList()) {
			System.out.println(s);
			// gf.getCustomer(s).print();
			if (gf.getCustomer(s).getAccountsNr() >= 6) {
				System.out.println("ACCOUNTS MORE THAN ALLOWED");
			}
		}
	}

	public void checkAccount(String accountNr) {
		GeneralFunctions gf = new GeneralFunctions();
		gf.getAccount(accountNr).print();
	}

	public void artificialChoise() {
		// the simulation of a managers decisions
		getNextOCR();
		if (req != null) {
			System.out.println("Examining");
			// Random rand = new Random();
			int r = 0;// rand.nextInt(1);
			// try {
			// TimeUnit.SECONDS.sleep(2);
			// // Thread.sleep(2000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }

			// **********************************
			// checkClients();
			// **********************************
			String response;
			if (r == 0) {
				response = StaticVars.ACCEPT;
			} else {
				response = StaticVars.DENIE;
			}

			switch (req.getReqType()) {
			case StaticVars.OPEN:
				confirmOpen(response, response);
				break;
			case StaticVars.CLOSE:
				confirmClose(response, response);
				break;
			case StaticVars.PLUS_1K_DEP:
				confirmClose(response, response);
				break;
			case StaticVars.PLUS_1K_TRANS:
				confirmClose(response, response);
				break;
			case StaticVars.PLUS_1K_WITH:
				confirmClose(response, response);
				break;
			case StaticVars.PLUS_6_ACC:
				confirmClose(response, response);
				break;
			}

			if (req.getReqType() == (StaticVars.OPEN)) {
				confirmOpen(response, req.getNote());
			} else if (req.getReqType() == (StaticVars.CLOSE)) {
				confirmClose(response, req.getNote());
			} else {
				// lists.getTellerFunc(req.getTellerId()).alert(null,
				// "UNDEFINED ERROR OCOURED", null, "");
			}
		}

	}

	/* MANAGER client side possible requests */

	public List<Transaction> clientInvolvedTransactionsParts(List<String> ids) {
		// for every client id find the transactions in witch they were
		// involved. for every set of client transactions send a response to the
		// Manager client side
		ManagerQuery mq = new ManagerQuery();
		// ManMsgHandler mmh = new ManMsgHandler();
		String clientId;
		List<Transaction> trList = null;
		for (int i = 0; i < ids.size(); i++) {
			clientId = ids.get(i);
			trList = mq.getClientInvolvedTransactionsPart(clientId);
			// mm/h.transReplyClientPart(i, trList, wsSession);
		}
		return trList;
	}

	public List<Transaction> clientInvolvedTransactionsAll(List<String> ids) {
		// for every client id find the transactions in witch they were
		// involved and send them all togather
		ManagerQuery mq = new ManagerQuery();
		// ManMsgHandler mmh = new ManMsgHandler();
		List<Transaction> trList = mq.getClientInvolvedTransactionsAll(ids);
		// mmh.transReplyClientAll(trList, wsSession);
		return trList;
	}

	public List<Transaction> accountInvolvedTransactions(String accNr) {
		// return the transactions where the account is involved
		ManagerQuery mq = new ManagerQuery();
		// ManMsgHandler mmh = new ManMsgHandler();
		List<Transaction> trList = mq.getAccountInvolvedTrans(accNr);
		// mmh.transReplyAccount(trList, wsSession);
		return trList;
	}

	public List<Transaction> getTransaction(Date t1, Date t2) {
		ManagerQuery mq = new ManagerQuery();
		if (t2 == null) {
			return mq.getTransaction(t1);
		} else {
			return mq.getTransaction(t1, t2);
		}
	}

	public List<Object[]> getBalance(Date t1, Date t2) {
		ManagerQuery mq = new ManagerQuery();

		if (t2 == null) {
			return mq.getBalance(t1);
		} else {
			return mq.getBalance(t1, t2);
		}
	}

	// -------------------------

	public List<Customers> getAccountClients(String accountId) {
		GeneralFunctions gf = new GeneralFunctions();
		List<String> accIds = gf.accountsClients(accountId);

		List<Customers> custs = null;
		if (accIds.size() != 0) {
			custs = new ArrayList<>();
			for (String c : accIds) {
				Customers a = gf.getCustomer(c);
				custs.add(a);
			}
		}
		return custs;
	}

	public List<Account> getClientAccounts(String personalId) {
		GeneralFunctions gf = new GeneralFunctions();
		List<Account> acl = null;
		List<String> cids = gf.clientsAccounts(personalId);
		if (cids != null) {
			acl = new ArrayList<>();
			for (String s : cids) {
				acl.add(gf.getAccount(s));
			}
		}
		return acl;
	}

	public Account getAccountStatus(String accId) {
		ManagerQuery tq = new ManagerQuery();
		return tq.getAccount(accId);
	}

	/* END */

	public int getEmpId() {
		return empId;
	}

	public OCRequest getReq() {
		return req;
	}

	public void setReq(OCRequest req) {
		this.req = req;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public Session getWsSession() {
		return wsSession;
	}

	public void setWsSession(Session wsSession) {
		this.wsSession = wsSession;
	}

	public void sendCS() {

	}

}
