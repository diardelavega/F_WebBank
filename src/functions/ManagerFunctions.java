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
import system.Coordinator;
import system.ManagerQuery;
import utils.GeneralFunctions;
import comon.OCRequest;
import comon.StaticVars;
import entity.EmployeeAction;
import entity.Transaction;

public class ManagerFunctions extends EmployeeFunctions {
	private final Logger logger = LoggerFactory
			.getLogger(ManagerFunctions.class);

	private Coordinator lists = new Coordinator();
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

	public void confirmOpen(String response,String note) {

		//the coordinator should delete OCR
		String accNr = "";
		String msgResponse = "";
		if (response.equals(StaticVars.ACCEPT)) {
			accNr = accm.openAccount(req.getAccType(), req.getClientIdsList());
			logger.info("ACCOUNT {}. is oppen", accNr);
			req.getTellersFunction().alert(StaticVars.OPEN,// alert the teller
															// of response
					StaticVars.REQ_APPROVE, accNr, note);
			req.setStatusComplete();
			lists.deleteOCR(req);
			/*
			 * alternative method*** change OCR to completed & the Coordinator
			 * (Lists) will alert the TellerFunction Object on the appropriate
			 * index of the appropriate list
			 */
			// ----------------------------
			EmployeeAction ea = new EmployeeAction(accNr, StaticVars.CNF_OPEN,
					"", empId);
			super.confirmOpenAcc(ea);
			// ----------------------------
		} else {
			logger.info("OPEN ACCOUNT REQEST WAS DENNIED");
			req.getTellersFunction().alert(StaticVars.OPEN,
					StaticVars.REQ_DENIED, null,note);
		}
	}

	public void confirmClose(String response,String note ) {
		if (response.equals(StaticVars.ACCEPT)) {
			accm.closeAccount(req.getAccFromNr());
			req.setStatusComplete();
			lists.deleteOCR(req);
			logger.info("CLOSE ACCOUNT {}. REQEST WAS APPROVED", req.getAccFromNr());
			// ----------------------------
			EmployeeAction ea = new EmployeeAction(req.getAccFromNr(),
					StaticVars.CNF_CLOSE, "", empId);
			super.confirmCloseAcc(ea);
			// ----------------------------
			req.getTellersFunction().alert(StaticVars.CLOSE,
					StaticVars.REQ_APPROVE, req.getAccFromNr(),note);
		} else {
			logger.info("CLOSE ACCOUNT {}. REQEST WAS DENNIED", req.getAccFromNr());
			req.getTellersFunction().alert(StaticVars.CLOSE,
					StaticVars.REQ_DENIED, null,note);
		}
	}

	public void returnRequest(){
		if(req!=null){
			req.unPin();
			req.setStatusInComplete();
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
			
			switch(req.getReqType() ){
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
				req.getTellersFunction().alert(null, "UNDEFINED ERROR OCOURED",
						null,"");
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

	public List<Transaction> getTransaction(Date t1, Date t2) {
		ManagerQuery mq = new ManagerQuery();
		if (t2 == null) {
			return mq.getTransaction(t1);
		} else {
			return mq.getTransaction(t1, t2);
		}

	}

}
