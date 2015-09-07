package system;

import java.io.Serializable;
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

import comon.OCRequest;
import comon.StaticVars;
import utils.GeneralFunctions;
import utils.PushClientSide;
import db.DBHandler;
import entity.Account;
import entity.Customers;
import entity.EmployeeAction;
import functions.EmployeeFunctions;

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

		// TODO check if transfer amount is greater than 1000 (alert manager)
		GeneralFunctions gf = new GeneralFunctions();
		List<String> dbClients = gf.accountsClients(accFrom);
		if (!dbClients.contains(personalId)) {
			logger.warn("NOT THIS ACCOUNT : {}. OWNER", accFrom);
		} else if (!gf.existsAccount(accTo)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accTo);
		} else {
			upSession();
			Account fromAcc = (Account) s.get(Account.class, accFrom);
			if (fromAcc.getBalance() < amount) {
				logger.info("UNSUFICIENT FOUNDS IN YOUR ACCOUNT");
			} else {

				Account toAcc = (Account) s.get(Account.class, accTo);
				Transaction tr = s.beginTransaction();
				try {
					fromAcc.setBalance(fromAcc.getBalance() - amount);
					toAcc.setBalance(toAcc.getBalance() + amount);
					s.persist(fromAcc);
					s.persist(toAcc);
					s.flush();
					tr.commit();
				} catch (HibernateException e) {
					System.out.println(" TRANSFER FAILED");
					tr.rollback();
					e.printStackTrace();
				}
				logger.info("TRANSFER COMPLETED");

				// --------DB transaction & employeeAction----------
				Timestamp tstamp = new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime());
				entity.Transaction t = new entity.Transaction(tstamp,
						personalId, StaticVars.TRANSFER, accFrom, accTo,
						amount, method);
				tr = s.beginTransaction();
				long idTra = StaticVars.ERROR;
				try {
					idTra = (long) s.save(t);
					s.flush();
					tr.commit();
					/* alert director for the transaction if amount >=500 */
					if (amount >= 500) {
						PushClientSide pcs = new PushClientSide();
						pcs.pushTransToDir(t);
					}
					/*************************/
				} catch (HibernateException e) {
					tr.rollback();
					e.printStackTrace();
				}

				// EmployeeAction ea = new EmployeeAction(StaticVars.TRANSFER,
				// note, empId, idTra);
				// tr = s.beginTransaction();
				// s.save(ea);
				// s.flush();
				// tr.commit();
				s.close();
				return idTra;
				// ----------------------------------------
				// s.close();
				// TODO enter transaction & employeeAction
			}
		}
		return StaticVars.ERROR;
	}

	public long deposite(String accNr, double amount) {

		long idTra = StaticVars.ERROR;
		Account acc = (Account) s.get(Account.class, accNr);
		if (acc == null) {
			logger.warn("ACCOUNT DOES NOT EXIST");
			idTra = StaticVars.DOES_NOT_EXISTS;
		} else {
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
				if (amount >= 500) {
					PushClientSide pcs = new PushClientSide();
					pcs.pushTransToDir(t);
				}
				/*************************/
			} catch (HibernateException e) {
				tr.rollback();
				e.printStackTrace();
			}
			s.close();
		}
		return idTra;
	}

	public long withdraw(String personalId, String accNr, double amount) {
		GeneralFunctions gf = new GeneralFunctions();
		List<String> dbClients = gf.accountsClients(accNr);
		if (!dbClients.contains(personalId) || dbClients == null) {
			System.out.println("NOT THIS ACCOUNT :" + accNr + " OWNER");
		} else {
			upSession();
			Account acc = (Account) s.get(Account.class, accNr);
			double balance = acc.getBalance();
			if (balance < amount) {
				System.out.println("UNSUFICIENT FOUNDS IN YOUR ACCOUNT");
				s.close();
			} else {
				Transaction tr = s.beginTransaction();
				try {
					acc.setBalance(balance - amount);
					s.persist(acc);
					s.flush();
					tr.commit();
				} catch (HibernateException e) {
					tr.rollback();
					e.printStackTrace();
				}
				// s.close();
				System.out.println("WITHDRAW OPERATION DONE");

				// --------DB transaction & employeeAction----------
				Timestamp tstamp = new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime());
				entity.Transaction t = new entity.Transaction(tstamp,
						personalId, StaticVars.WITHDRAW, accNr, null, amount,
						't');
				// upSession();
				tr = s.beginTransaction();
				long idTra = StaticVars.ERROR;// invalid data for the
												// initialisation
				try {
					idTra = (long) s.save(t);
					s.flush();
					tr.commit();
					/* alert director for the transaction if amount >=500 */
					if (amount >= 500) {
						PushClientSide pcs = new PushClientSide();
						pcs.pushTransToDir(t);
					}
					/*************************/
				} catch (Exception e) {
					tr.rollback();
					logger.info("problem with the db at transaction storage phase");
				}
				s.close();
				// TODO alert everyone interested in new transactions

				return idTra;
				// EmployeeAction ea = new EmployeeAction(StaticVars.WITHDRAW,
				// note, empId, idTra);
				// tr = s.beginTransaction();
				// s.save(ea);
				// s.flush();
				// tr.commit();
				// s.close();
				// ----------------------------------------
			}
		}
		return StaticVars.ERROR;
	}

	public String checkTransferRegularity(String personalId, String accFrom,
			String accTo, double amount, char method) {
		String regular = null;
		GeneralFunctions gf = new GeneralFunctions();

		if (!gf.existsAccount(accFrom)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accFrom);
			regular = "ACCOUNT_FROM DOES NOT EXIST";
			return regular;
		} else if (!gf.existsAccount(accTo)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accTo);
			regular = "ACCOUNT_TO DOES NOT EXIST";
			return regular;
		} else {
			List<String> dbClients = gf.accountsClients(accFrom);
			if (!dbClients.contains(personalId)) {
				logger.warn("NOT THIS ACCOUNT : {}. OWNER", accFrom);
				regular = "NOT ACCOUNTS OWNER";
				return regular;
			}
			upSession();
			Account fromAcc = (Account) s.get(Account.class, accFrom);
			if (fromAcc.getBalance() < amount) {
				logger.info("UNSUFICIENT FOUNDS IN YOUR ACCOUNT");
				regular = "UNSUFICIENT FOUNDS IN YOUR ACCOUNT";
				return regular;
			}
		}
		return regular;
	}

	public String checkWithdrawRegularity(String personalId, String accNr,
			double amount) {
		String regular = null;
		GeneralFunctions gf = new GeneralFunctions();

		if (!gf.existsAccount(accNr)) {
			logger.warn("THIS ACCOUNT :{}. DOES NOT EXISTS", accNr);
			regular = "ACCOUNT DOES NOT EXIST";
			return regular;
		} else {
			List<String> dbClients = gf.accountsClients(accNr);
			if (!dbClients.contains(personalId)) {
				logger.warn("NOT THIS ACCOUNT : {}. OWNER", accNr);
				regular = "NOT ACCOUNTS OWNER";
				return regular;
			}
			upSession();
			Account acc = (Account) s.get(Account.class, accNr);
			if (acc.getBalance() < amount) {
				logger.info("UNSUFICIENT FOUNDS IN YOUR ACCOUNT");
				regular = "UNSUFICIENT FOUNDS IN YOUR ACCOUNT";
				return regular;
			}
		}
		return regular;
	}

	public String checkDepositeRegularity(String accNr, double amount) {
		String regular = null;
		Account acc = (Account) s.get(Account.class, accNr);
		if (acc == null) {
			logger.warn("ACCOUNT DOES NOT EXIST");
			regular = "ACCOUNT DOES NOT EXIST";
		}
		return regular;
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
		s.close();
		return custList;
	}

	private String empQueryCondition(String fname, String lname,
			String address, String phone, String eMail, String password) {
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

	public String registerCustomer(String persId,String fname, String lname, String eMail,
			String bdate, String address, String phone, String password) {
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

			Customers c = gf.getCustomer(persId);
			c.setFname(fname);
			c.setLname(lname);
			c.setAddress(address);
			c.setBdata(tdate);
			c.seteMail(eMail);
			c.setPhone(phone);
			c.setPassword(password);

			upSession();
			try {
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
