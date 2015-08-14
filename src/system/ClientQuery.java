package system;

import org.hibernate.Query;
import org.hibernate.Session;

import db.DBHandler;
import entity.Customers;

/**
 * @author Administrator
 *
 *         check log in sessions and keep track of them. clients have their own
 *         log in e-mail employees have "@bank.com". Track the sessions of
 *         logged in clients, tellers and managers
 *
 */
public class ClientQuery {
	// DBHandler dbh = new DBHandler();
	Session s = DBHandler.getSessionFactory().openSession();

	// private void init() {
	// dbh.getSessionFactory();
	// s = dbh.openSession();
	// }

	// private void closeSession() {
	// if (s != null)
	// s.close();
	// }

	// check if user exist and return primary key
	public String dbLogInCheck(String usr, String psw) {
		//DEPRICATED
		// usr=e-mail -> if "@webbank.com" -> employee
		String[] temp = usr.split("@");
		String table;

		String query;

		if (temp[1].equals("webank")) {
			table = "Customers";
			query = "SELECT T.empId FROM" + table + " T WHERE eMail = '" + usr
					+ "' AND password  '" + psw + "'";
		} else {
			table = "customer";
			query = "SELECT T.personalId FROM " + table + " T WHERE eMail = '"
					+ usr + "' AND pasasword = '" + psw + "'";
		}
		// connect to db;

		Query q = s.createQuery(query);
		s.close();
//		flushNclear();

		return q.getQueryString();
	}

	public String getBalance(String key, String acc) {
		// TODO go to
		String query = "FROM Account WHERE accountId= '" + acc + "'";
		Query q = s.createQuery(query);
		s.clear();
//		flushNclear();
		// query.
		// String query =
		// " SELECT A.integerBalance, A.decimalBalance FROM Account A"
		// +
		// "WHERE A.accountId in (SELECT CA.accountId FROM CustomersAccount CA WHERE CA.clienId= '"
		// + key + "' AND CA.accountId = '" + acc + "' )";
		// + "(CA.accountId = A.accountId)WHERE ";
		return q.toString();
	}

	public Object allBalance(String key) {
		// TODO get customer_accounts & for each acc get balance
		// return list <acc-balance>
		return null;
	}

	public Object personalData(String key) {
		// TODO get all personal data, in case they want to change them ???
		// maby a later function
		return null;
	}

	public void register(Customers c) {
		s.beginTransaction();
		s.save(c);
		s.getTransaction().commit();
		s.close();
		// flushNclear();

		// TODO check validity acording to db fields
		// store & comit
	}

	public void transfer(String key, String accFrom, String accTo, double amount) {
		// TODO check if accFrom belongs to key
		// if balance >=amount
		// if accTo belongs to the bank
		// transfer & comit

	}

	public void deposite(String acc, double amount) {
		// TODO check if acc belongs to the bank
		// add amount & comit
	}

	public void withdraw(String key, String acc, double amount) {
		// TODO check if acc belongs to key
		// check if amount<=balance
		// retract amount & comit
	}

	public int checkClientId(String persId) {
		Query q = s
				.createQuery("SELECT customerStatus FROM Customers WHERE personalId= '"
						+ persId + "'");
		// TODO catch non existing persId
		return q.getFirstResult();
	}

	private void flushNclear() {
		s.flush();
		s.clear();
	}

}
