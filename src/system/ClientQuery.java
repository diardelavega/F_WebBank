package system;

//import java.util.Date;
import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.GeneralFunctions;
import db.DBHandler;
import entity.Customers;
import entity.Transaction;

/**
 * @author Administrator
 *
 *         check log in sessions and keep track of them. clients have their own
 *         log in e-mail employees have "@bank.com". Track the sessions of
 *         logged in clients, tellers and managers
 *
 */
public class ClientQuery {

	public static Logger logger = LoggerFactory.getLogger(ClientQuery.class);
	// DBHandler dbh = new DBHandler();
	Session s = DBHandler.getSessionFactory().openSession();

	public List<Transaction> getTransactions(List<String> accounts, Date t1,
			Date t2) {
		Query q = s
				.createQuery(
						"FROM Transaction WHERE( trDate BETWEEN :d1 AND :d2)"
								+ " AND (acc1 in (:accounts) OR acc2 in (:accounts))")
				.setParameter("d1", t1).setParameter("d2", t2)
				.setParameterList("accounts", accounts);
		List<Transaction> tl = q.list();
		return tl;
	}

	public List<Transaction> getTransactions(List<String> accounts) {
		Query q = s
				.createQuery(
						"FROM Transaction WHERE acc1 in (:accounts) OR acc2 in (:accounts)")
				.setParameterList("accounts", accounts);
		List<Transaction> tl = q.list();
		return tl;
	}

	public List<Transaction> getTransactions(List<String> accounts,
			java.sql.Date t1) {
		int dayte = t1.getDate();
		Date d2 = new Date(t1.getTime());
		d2.setDate(dayte + 1);

		Query q = s
				.createQuery(
						"FROM Transaction WHERE trDate >= :d1 and trDate <=:d2"
								+ " AND (acc1 in (:accounts) OR acc2 in (:accounts) )")
				.setParameter("d1", t1).setParameter("d2", d2)
				.setParameterList("accounts", accounts);
		List<Transaction> tl = q.list();
		return tl;
	}

	public List<Transaction> getTransactions(String searchPersId) {
		upSession();
		Customers c = (Customers) s.load(Customers.class, searchPersId);
		if (c == null) {
			return null;
		}
		Query q = s.createQuery("FROM Transaction WHERE personalId = :pid")
				.setParameter("pid", searchPersId);
		return q.list();
	}

	public List<Transaction> getTransactions(String searchPersId, Date t1) {
		upSession();
		Customers c = (Customers) s.load(Customers.class, searchPersId);
		if (c == null) {
			return null;
		}
		int dayte = t1.getDate();
		Date d2 = new Date(t1.getTime());
		d2.setDate(dayte + 1);

		Query q = s
				.createQuery(
						"FROM Transaction WHERE personalId = :pid AND  (trDate > :d1 and trDate <:d2)")
				.setParameter("pid", searchPersId).setParameter("d1", t1)
				.setParameter("d2", d2);
		return q.list();
	}

	public List<Transaction> getTransactions(String searchPersId, Date t1,
			Date t2) {
		upSession();
		Customers c = (Customers) s.load(Customers.class, searchPersId);
		if (c == null) {
			return null;
		}

		Query q = s
				.createQuery(
						"FROM Transaction WHERE personalId = :pid AND ( trDate BETWEEN :d1 AND :d2)")
				.setParameter("pid", searchPersId).setParameter("d1", t1)
				.setParameter("d2", t2);
		return q.list();
	}

	public List<Object[]> getBalance(String searchPersId) {
		upSession();
		Customers c = (Customers) s.load(Customers.class, searchPersId);
		if (c == null) {
			logger.info("Customer is null value");
			return null;
		}

		Query q = s
				.createQuery(
						"SELECT trDate ,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trDate BETWEEN '2015-06-16' AND '2015-08-23' "
								+ "WHERE personalId = :pid "
								+ "group by date(trDate) order  by trDate asc")
				.setParameter("pid", searchPersId);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		logger.info("RETURN Query List SIze = {}", ol.size());
		return ol;
	}

	public List<Object[]> getBalance(String searchPersId, Date t1) {
		upSession();
		Customers c = (Customers) s.load(Customers.class, searchPersId);
		if (c == null) {
			return null;
		}
		int dayte = t1.getDate();
		Date d2 = new Date(t1.getTime());
		d2.setDate(dayte + 1);
		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trDate BETWEEN '2015-06-16' AND '2015-06-23' "
								+ "WHERE (trDate > :d1 and  trDate < :d2) AND personalId = :pid "
								+ "group by date(trDate) order  by trDate asc")
				.setParameter("d1", t1).setParameter("d2", d2)
				.setParameter("pid", searchPersId);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		return ol;
	}

	public List<Object[]> getBalance(String searchPersId, Date t1, Date t2) {
		upSession();
		Customers c = (Customers) s.load(Customers.class, searchPersId);
		if (c == null) {
			return null;
		}

		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trDate BETWEEN '2015-06-16' AND '2015-08-23' "
								+ "WHERE (trDate BETWEEN :fromDate AND :toDate)  AND personalId = :pid "
								+ "group by date(trDate) order  by trDate asc")
				.setParameter("fromDate", t1).setParameter("toDate", t2)
				.setParameter("pid", searchPersId);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		return ol;
	}

	public List<Object[]> getBalance(List<String> accounts) {
		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trDate BETWEEN '2015-06-16' AND '2015-08-23' "
								+ "WHERE acc1 in (:accounts) OR acc2 in (:accounts) "
								+ "group by date(trDate) order  by trDate asc ")
				.setParameterList("accounts", accounts);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		return ol;
	}

	public List<Object[]> getBalance(List<String> accounts, Date t1) {
		int dayte = t1.getDate();
		Date d2 = new Date(t1.getTime());
		d2.setDate(dayte + 1);
		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trDate BETWEEN '2015-06-16' AND '2015-06-23' "
								+ "WHERE (trDate > :d1 and  trDate < :d2) AND (acc1 in (:accounts) OR acc2 in (:accounts)) "
								+ "group by date(trDate) order  by trDate asc ")
				.setParameter("d1", t1).setParameter("d2", d2)
				.setParameterList("accounts", accounts);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		return ol;
	}

	public List<Object[]> getBalance(List<String> accounts, Date t1, Date t2) {
		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trDate BETWEEN '2015-06-16' AND '2015-08-23' "
								+ "WHERE (trDate BETWEEN :fromDate AND :toDate)  AND (acc1 in (:accounts) OR acc2 in (:accounts)) "
								+ "group by date(trDate) order  by trDate asc ")
				.setParameter("fromDate", t1).setParameter("toDate", t2)
				.setParameterList("accounts", accounts);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		return ol;
	}

	public void upSession() {
		if (!s.isOpen() || !s.isConnected()) {
			s = DBHandler.getSessionFactory().openSession();
		}
	}

	public void closeSession() {
		try {
			s.flush();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
