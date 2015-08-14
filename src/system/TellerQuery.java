package system;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comon.StaticVars;
import utils.GeneralFunctions;
import db.DBHandler;
import entity.Account;
import entity.Customers;
import entity.EmployeeAction;

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

	public long transfer(String key, String accFrom, String accTo, double amount,char method) {
		GeneralFunctions gf = new GeneralFunctions();
		List<String> dbClients = gf.accountsClients(accFrom);
		if (!dbClients.contains(key)) {
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
				entity.Transaction t = new entity.Transaction(tstamp, key,
						StaticVars.TRANSFER, accFrom, accTo, amount, method);
				tr = s.beginTransaction();
				long idTra = (long) s.save(t);
				s.flush();
				tr.commit();

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

		long idTra = 0;
		Account acc = (Account) s.get(Account.class, accNr);
		if (acc == null) {
			logger.warn("ACCOUNT DOES NOT EXIST");
			return StaticVars.ERROR;
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
			} catch (HibernateException e) {
				tr.rollback();
				e.printStackTrace();
			}
			return idTra;
		}

	}

	public long withdraw(String key, String accNr, double amount) {
		GeneralFunctions gf = new GeneralFunctions();
		List<String> dbClients = gf.accountsClients(accNr);
		if (!dbClients.contains(key) || dbClients == null) {
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
				entity.Transaction t = new entity.Transaction(tstamp, key,
						StaticVars.WITHDRAW, accNr, null, amount, 't');
				// upSession();
				tr = s.beginTransaction();
				long idTra = (long) s.save(t);
				s.flush();
				tr.commit();
				s.close();
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

	public void upSession() {
		if (!s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
	}
}
