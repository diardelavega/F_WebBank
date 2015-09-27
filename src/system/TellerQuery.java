package system;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.GeneralFunctions;
import utils.PushClientSide;

import comon.OCRequest;
import comon.StaticVars;

import db.DBHandler;
import entity.Account;
import entity.Customers;

public class TellerQuery {
	Logger logger = LoggerFactory.getLogger(TellerQuery.class);
	private Session s = DBHandler.getSessionFactory().openSession();

	public List<String> clientAccountCompatibility(List<String> personalIds,
			String accNr) {
		// find if account owners are missing
		GeneralFunctions gf = new GeneralFunctions();
		List<String> dbClients = gf.accountsClients(accNr);
		dbClients.removeAll(personalIds);
		if (dbClients.size() > 0) {
			return dbClients;
		} else {
			return null;
		}
	}

	public long transfer(String personalId, String accFrom, String accTo,
			double amount, char method) {

		Account fromAcc = (Account) s.get(Account.class, accFrom);
		Account toAcc = (Account) s.get(Account.class, accTo);

		long idTra = StaticVars.ERROR;
		// -------ALTER ACCOUNT BALANCES
		Transaction tr = s.beginTransaction();
		try {
			fromAcc.setBalance(fromAcc.getBalance() - amount);
			toAcc.setBalance(toAcc.getBalance() + amount);
			s.persist(fromAcc);
			s.persist(toAcc);
			s.flush();
			tr.commit();
			// ------- New Transaction --------------------------------
			Timestamp tstamp = new Timestamp(Calendar.getInstance().getTime()
					.getTime());
			entity.Transaction t = new entity.Transaction(tstamp, personalId,
					StaticVars.TRANSFER, accFrom, accTo, amount, method);
			tr = s.beginTransaction();
			try {
				idTra = (long) s.save(t);
				s.flush();
				tr.commit();
				// TODO Push to client side if customer is online
				/* alert client's account for the transaction if amount */
				// PushClientSide pcs = new PushClientSide();
				// pcs.pushTransToDir(t);
				/*************************/
			} catch (HibernateException e) {
				tr.rollback();
				e.printStackTrace();
			}
			// -------------------------------------------------
		} catch (HibernateException e) {
			System.out.println(" TRANSFER FAILED");
			tr.rollback();
			e.printStackTrace();
		}
		logger.info("TRANSFER COMPLETED");
		s.close();

		return idTra;
	}

	public long deposite(String accNr, double amount) {

		long idTra = StaticVars.ERROR;
		upSession();
		Account acc = (Account) s.get(Account.class, accNr);

		Timestamp tstamp = new java.sql.Timestamp(Calendar.getInstance()
				.getTime().getTime());
		entity.Transaction t = null;
		Transaction tr = s.beginTransaction();
		try {
			t = new entity.Transaction(tstamp, null, StaticVars.DEPOSITE,
					accNr, null, amount, 't');
			acc.setBalance(acc.getBalance() + amount);
			s.persist(acc);
			s.flush();

			idTra = (long) s.save(t);
			s.flush();
			tr.commit();
			logger.info("DEPOSITE OPERATION DONE");
			/* alert director for the transaction if amount >=500 */
			// TODO IF Clients account is online push changes to Client Side
			// if (amount >= 500) {
			// PushClientSide pcs = new PushClientSide();
			// pcs.pushTransToDir(t);
			// }
			/*************************/
		} catch (HibernateException e) {
			tr.rollback();
			e.printStackTrace();
		}
		s.close();

		return idTra;
	}

	public long withdraw(String personalId, String accNr, double amount) {

		upSession();
		Account acc = (Account) s.get(Account.class, accNr);
		double balance = acc.getBalance();
		long idTra = StaticVars.ERROR;

		// ---Set new balance in the accounts
		Transaction tr = s.beginTransaction();
		try {
			acc.setBalance(balance - amount);
			s.persist(acc);
			s.flush();
			tr.commit();

			// ------ Create A new Transaction
			Timestamp tstamp = new java.sql.Timestamp(Calendar.getInstance()
					.getTime().getTime());
			entity.Transaction t = new entity.Transaction(tstamp, personalId,
					StaticVars.WITHDRAW, accNr, null, amount, 't');
			tr = s.beginTransaction();
			try {
				idTra = (long) s.save(t);
				s.flush();
				tr.commit();

				/*
				 * TODO push to client side alert client's client side accounts
				 * for the transaction if amount >=500
				 */
				PushClientSide pcs = new PushClientSide();
				pcs.pushTransToDir(t);
				/*************************/
			} catch (Exception e) {
				tr.rollback();
				logger.info("problem with the db at transaction storage phase");
			}
		} catch (HibernateException e) {
			tr.rollback();
			e.printStackTrace();
		}
		System.out.println("WITHDRAW OPERATION DONE");
		s.close();

		return idTra;
	}

	public String checkTransferRegularity(String personalId, String accFrom,
			String accTo, double amount, char method) {
		GeneralFunctions gf = new GeneralFunctions();

		String ret = gf.valAmmount(amount + "");
		if (ret != null) {
			return ret;
		}

		if (!gf.existsAccount(accFrom)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accFrom);
			return "ACCOUNT_FROM DOES NOT EXIST";
		} else if (!gf.existsAccount(accTo)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accTo);
			return "ACCOUNT_TO DOES NOT EXIST";
		} else {
			List<String> dbClients = gf.accountsClients(accFrom);
			if (!dbClients.contains(personalId)) {
				logger.warn("NOT THIS ACCOUNT : {}. OWNER", accFrom);
				return "NOT ACCOUNTS OWNER";
			}
			upSession();
			Account fromAcc = gf.getAccount(accFrom);
			if (fromAcc.getBalance() < amount) {
				logger.info("UNSUFICIENT FOUNDS IN YOUR ACCOUNT");
				return "UNSUFICIENT FOUNDS IN YOUR ACCOUNT";
			}
		}
		return null;
	}

	public String checkWithdrawRegularity(String personalId, String accNr,
			double amount) {
		GeneralFunctions gf = new GeneralFunctions();
		String ret = gf.valAmmount(amount + "");
		if (ret != null) {
			return ret;
		}

		if (!gf.existsAccount(accNr)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accNr);
			return "ACCOUNT DOES NOT EXIST";
		} else {
			List<String> dbClients = gf.accountsClients(accNr);
			if (!dbClients.contains(personalId)) {
				logger.warn("NOT THIS ACCOUNT : {}. OWNER", accNr);
				return "NOT ACCOUNTS OWNER";
			}
			Account acc = gf.getAccount(accNr);
			if (acc.getBalance() < amount) {
				logger.info("UNSUFICIENT FOUNDS IN YOUR ACCOUNT");
				return "UNSUFICIENT FOUNDS IN YOUR ACCOUNT";
			}
		}
		return null;
	}

	public String checkDepositeRegularity(String accNr, double amount) {
		GeneralFunctions gf = new GeneralFunctions();
		String ret = gf.valAmmount(String.valueOf(amount));
		if (ret != null) {
			return ret;
		}

		Account acc = gf.getAccount(accNr);
		if (acc == null) {
			logger.warn("ACCOUNT DOES NOT EXIST");
			return "ACCOUNT DOES NOT EXIST";
		}
		return null;
	}

	public void upSession() {
		if (!s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
	}

	public Object[] getCustomerDetails(String clientId) {
		Query q = s
				.createQuery(
						"SELECT personalId,fname,lname,bdata,address,eMail FROM Customers where personalId like :pId ")
				.setParameter("pId", clientId);
		return (Object[]) q.list().get(0);
	}

	public Account getAccount(String accId) {
		GeneralFunctions gf = new GeneralFunctions();
		return gf.getAccount(accId);
	}

	public List<Customers> getAllClients(String fname, String lname,
			String address, String phone, String eMail, String password) {
		// search the db for clients. select based on the criteria from the form
		String condition = empQueryCondition(fname, lname, address, phone,
				eMail, password);
		if (condition != null) {
			condition = " WHERE " + condition;
		} else {
			condition = "";
		}

		logger.info("----------> condition = {} ", condition);

		upSession();
		Query q = s.createQuery("FROM Customers " + condition);
		List<Customers> custList = q.list();
		// s.close();
		return custList;
	}

	private String empQueryCondition(String fname, String lname,
			String address, String phone, String eMail, String password) {
		// prepare a string for the bd query
		boolean flag = false;
		String condition = " ";
		if (!fname.equals("")) {
			condition += "fname like '" + fname + "%' ";
			flag = true;
		}
		if (!lname.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "lname like '" + lname + "%' ";
			flag = true;
		}
		if (!address.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "address like '" + address + "%' ";
			flag = true;
		}
		if (!phone.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "possition like '" + phone + "%' ";
			flag = true;
		}
		if (!eMail.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "eMail like '" + eMail + "%' ";
			flag = true;
		}
		if (!password.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "password like '" + password + "%' ";
		}
		if (flag) {
			return condition;
		} else {
			return null;
		}
	}

	// --------------------reg alter delete

	public String registerCustomer(String persId, String fname, String lname,
			String eMail, String bdate, String address, String phone,
			String password) {
		GeneralFunctions gf = new GeneralFunctions();
		logger.info("IN TELLER QUERY");
		StringBuilder sb = new StringBuilder();// the validation error msgs
		sb.append(gf.valName(fname));
		sb.append(gf.valName(lname));
		sb.append(gf.valAddress(address));
		sb.append(gf.valEMail(eMail));
		sb.append(gf.valDubleMail(eMail));
		sb.append(gf.valPhone(phone));
		sb.append(gf.valPsw(password));
		if (sb.length() == 0) {
			Timestamp tdate;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date parsedDate = dateFormat.parse(bdate);
				tdate = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}

			Customers c = new Customers();
			c.setPersonalId(persId);
			c.setFname(fname);
			c.setLname(lname);
			c.setAddress(address);
			c.setBdata(tdate);
			c.seteMail(eMail);
			c.setPhone(phone);
			c.setPassword(password);

			logger.info("IN TELLER QUERY, READY TO SAVE CLIENT");
			upSession();
			try {
				s.beginTransaction();
				s.save(c);
				s.getTransaction().commit();
			} catch (Exception e) {
				s.getTransaction().rollback();
			}
			s.close();
		}
		return sb.toString();
	}

	public String alter(String persId, String fname, String lname,
			String eMail, String bdate, String address, String phone,
			String password) {

		StringBuilder sb = new StringBuilder();
		GeneralFunctions gf = new GeneralFunctions();
		sb.append(gf.valName(fname));
		sb.append(gf.valName(lname));
		sb.append(gf.valAddress(address));
		sb.append(gf.valEMail(eMail));
		sb.append(gf.valPhone(phone));
		sb.append(gf.valPsw(password));

		if (sb.length() == 0) {
			Timestamp tdate;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date parsedDate = dateFormat.parse(bdate);
				tdate = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
			logger.info(sb.toString());
			logger.info(fname);
			upSession();
			try {
				Customers c = gf.getCustomer(persId);
				c.setFname(fname);
				c.setLname(lname);
				c.setAddress(address);
				c.setBdata(tdate);
				c.seteMail(eMail);
				c.setPhone(phone);
				c.setPassword(password);

				s.beginTransaction();
				s.update(c);
				s.flush();
				s.getTransaction().commit();
			} catch (Exception e) {
				s.getTransaction().rollback();
			}
			s.close();
		}
		return sb.toString();
	}

	public String deleteCustomer(String persId) {
		GeneralFunctions gf = new GeneralFunctions();
		Customers c = gf.getCustomer(persId);
		if (c == null)
			return StaticVars.UNREG_USR;

		// search for open accounts
		if (c.getAccountsNr() > 0)
			return "Could Not Delete,Customer Has Running Accounts";

		upSession();
		try {
			s.beginTransaction();
			s.delete(c);
			s.getTransaction().commit();
		} catch (Exception e) {
			s.getTransaction().rollback();
			return null;
		}
		s.close();
		return StaticVars.DONE;
	}

	// ------------------------------ open close

	public void logEmployeeAction(OCRequest req) {

		// if (req.getResponse().equals(StaticVars.REQ_APPROVE)) {
		// EmployeeAction ea = new EmployeeAction();
		// EmployeeFunctions ef = new EmployeeFunctions();
		// ea.setEmpId(req.getTellerId());
		// ea.setActionType(req.getReqType());
		// if (req.getReqType().equals(StaticVars.OPEN)) {
		// AccountsManagment am = new AccountsManagment();
		// // open account and +1 to the customers accounts number they
		// // poses
		// String accNr = am.openAccount(req.getAccType(),
		// req.getClientIdsList());
		// ea.setAccountId1(accNr);
		// ea.setCustomerId(req.getClientIdsList());
		// ef.requestOpenAcc(ea);
		// }
		// if (req.getReqType().equals(StaticVars.CLOSE)) {
		// AccountsManagment am = new AccountsManagment();
		// // close account and decrease accounts number customers poses
		// am.closeAccount(req.getAccFromNr());
		// ea.setAccountId1(req.getAccFromNr());
		// ea.setCustomerId(req.getClientIdsList());
		// super.requestCloseAcc(ea);
		// }
		// if (req.getReqType().equals(StaticVars.PLUS_1K_DEP)) {
		// TellerQuery tq = new TellerQuery();
		// long trnr = tq.deposite(req.getAccFromNr(), req.getAmount());
		// ea.setAccountId1(req.getAccFromNr());
		// ea.setCustomerId(req.getClientIdsList());
		// ea.setTrNr(trnr);
		// ea.setAmount(req.getAmount());
		// super.deposite(ea);
		// }
		// if (req.getReqType().equals(StaticVars.PLUS_1K_WITH)) {
		// TellerQuery tq = new TellerQuery();
		// long trnr = tq.withdraw(req.getClientIdsList().get(0),
		// req.getAccFromNr(), req.getAmount());
		// ea.setAccountId1(req.getAccFromNr());
		// ea.setCustomerId(req.getClientIdsList());
		// ea.setTrNr(trnr);
		// ea.setAmount(req.getAmount());
		// super.withdraw(ea);
		// }
		// if (req.getReqType().equals(StaticVars.PLUS_1K_TRANS)) {
		// TellerQuery tq = new TellerQuery();
		// long trnr = tq.transfer(req.getClientIdsList().get(0),
		// req.getAccFromNr(), req.getAccToNr(), req.getAmount(),
		// 't');
		// ea.setAccountId1(req.getAccFromNr());
		// ea.setAccountId2(req.getAccToNr());
		// ea.setCustomerId(req.getClientIdsList());
		// ea.setTrNr(trnr);
		// ea.setAmount(req.getAmount());
		// super.transfer(ea);
		// }
		// if (req.getReqType().equals(StaticVars.PLUS_6_ACC)) {
		// AccountsManagment am = new AccountsManagment();
		// String accNr = am.openAccount(req.getAccType(),
		// req.getClientIdsList());
		// ea.setAccountId1(accNr);
		// ea.setCustomerId(req.getClientIdsList());
		// super.requestOpenAcc(ea);
		// }
		// }

	}

}
