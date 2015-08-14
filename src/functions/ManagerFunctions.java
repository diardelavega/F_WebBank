package functions;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.AccountsManagment;
import system.Lists;
import system.ManagerQuery;
import utils.GeneralFunctions;
import comon.OCRequest;
import comon.StaticVars;
import entity.EmployeeAction;
import entity.Transaction;

public class ManagerFunctions extends EmployeeFunctions {
	private final Logger logger = LoggerFactory
			.getLogger(ManagerFunctions.class);

	private Lists lists = new Lists();
	private OCRequest req;
	// private List<String> clientsPersonalIds;
	private int empId;
	private AccountsManagment accm = new AccountsManagment();

	public ManagerFunctions(int empId) {
		super();
		this.empId = empId;
	}

	public ManagerFunctions() {
		super();
		this.empId = 000;
	}

	public void confirmOpen(String response) {
		lists.deleteOCR(req);
		String accNr = "";
		String msgResponse = "";
		if (response.equals(StaticVars.ACCEPT)) {
			accNr = accm.openAccount(req.getAccType(), req.getClientIdsList());
			logger.info("ACCOUNT {}. is oppen", accNr);
			req.getTellersFunction().alert(StaticVars.OPEN,
					StaticVars.REQ_APPROVE, accNr);
			// ----------------------------
			EmployeeAction ea = new EmployeeAction(accNr, StaticVars.CNF_OPEN,
					"", empId);
			super.confirmOpenAcc(ea);
			// ----------------------------
		} else {
			logger.info("OPEN ACCOUNT REQEST WAS DENNIED");
			req.getTellersFunction().alert(StaticVars.OPEN,
					StaticVars.REQ_DENIED, null);
		}
	}

	public void confirmClose(String response) {
		lists.deleteOCR(req);
		if (response.equals(StaticVars.ACCEPT)) {
			accm.closeAccount(req.getAccNr());
			logger.info("CLOSE ACCOUNT {}. REQEST WAS APPROVED", req.getAccNr());
			// ----------------------------
			EmployeeAction ea = new EmployeeAction(req.getAccNr(),
					StaticVars.CNF_CLOSE, "", empId);
			super.confirmCloseAcc(ea);
			// ----------------------------
			req.getTellersFunction().alert(StaticVars.CLOSE,
					StaticVars.REQ_APPROVE, req.getAccNr());
		} else {
			logger.info("CLOSE ACCOUNT {}. REQEST WAS DENNIED", req.getAccNr());
			req.getTellersFunction().alert(StaticVars.CLOSE,
					StaticVars.REQ_DENIED, null);
		}
	}

	public void alert() {
		// to be summoned in every ocr addition
		System.out.println("A NEW REQUEST ARIVED");

		// AI choice
		artificialChoise();
	}

	public void getOCR() {
		req = lists.getNextOCR();
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
		getOCR();
		if (req != null) {
			System.out.println("Examining");
			Random rand = new Random();
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

			if (req.getReqType() == (StaticVars.OPEN)) {
				confirmOpen(response);
			} else if (req.getReqType() == (StaticVars.CLOSE)) {
				confirmClose(response);
			} else {
				req.getTellersFunction().alert(null, "UNDEFINED ERROR OCOURED",
						null);
			}
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

	public List<Transaction> getTransaction(Date t1, Date t2){
		ManagerQuery mq = new ManagerQuery();
		if (t2 == null) {
			return mq.getTransaction(t1);
		} else {
			return mq.getTransaction(t1, t2);
		}
		
	}
	
}
