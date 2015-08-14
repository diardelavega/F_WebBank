package functions;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.Lists;
import system.TellerQuery;
import utils.GeneralFunctions;
import comon.AccountType;
import comon.OCRequest;
import comon.Response;
import comon.StaticVars;
import entity.EmployeeAction;
import entity.Transaction;

public class TellerFunctions extends EmployeeFunctions {
	Logger logger = LoggerFactory.getLogger(TellerFunctions.class);
	private int empId;

	public TellerFunctions(int empId) {
		super();
		this.empId = empId;
	}

	public TellerFunctions() {
		super();
		this.empId = 000;
	}

	public void alert(String requestType, String response, String accNr) {
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
			logger.info("{}. for the operation {}.", response, requestType);
		} else {
			logger.info("{}. ", response);
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
				Lists list = new Lists();
				OCRequest req = new OCRequest(this, personalIds,
						StaticVars.OPEN, accType);
				list.addOCR(req);
			}// if
		}

	}

	public void closeAccount(List<String> personalIds, String accNr) {
		Lists list = new Lists();
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
			OCRequest req = new OCRequest(this, personalIds, StaticVars.CLOSE,
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
		long trNr = tq.deposite(accNr, amount);
		if (trNr > 0) {
			EmployeeAction ea = new EmployeeAction(StaticVars.DEPOSITE, note,
					empId, trNr);
			ea.setAmount(amount);
			ea.setAccountId(accNr);
			super.deposite(ea);
		}

	}

	public void withdraw(String personalId, String account, double amount,
			String note) {
		TellerQuery tq = new TellerQuery();
		long trNr = tq.withdraw(personalId, account, amount);
		if (trNr > 0) {
			EmployeeAction ea = new EmployeeAction(StaticVars.WITHDRAW, note,
					empId, trNr);
			ea.setCustomerId(personalId);
			ea.setAmount(amount);
			ea.setAccountId(account);
			super.withdraw(ea);
		}
	}

	public void transfer(String personalId, String accFrom, String accTo,
			double amount, String note) {
		TellerQuery tq = new TellerQuery();
		long trNr = tq.transfer(personalId, accFrom, accTo, amount, 't');
		if (trNr > 0) {
			EmployeeAction ea = new EmployeeAction(StaticVars.TRANSFER, note,
					empId, trNr);
			ea.setCustomerId(personalId);
			ea.setAmount(amount);
			ea.setAccountId(accFrom);
			super.transfer(ea);
		}
	}

	public void getAccountClients(String accountId) {
		GeneralFunctions gf = new GeneralFunctions();
		for (String c : gf.accountsClients(accountId)) {
			gf.getCustomer(c).print();
		}
	}

	public void getClientAccounts(String personalId) {
		GeneralFunctions gf = new GeneralFunctions();
		for (String s : gf.accountsClients(personalId)) {
			gf.getAccount(s).print();
		}
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

}
