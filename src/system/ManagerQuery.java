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
import entity.Account;
import entity.Customers;
import entity.Transaction;

public class ManagerQuery {
	private final Logger logger = LoggerFactory.getLogger(ManagerQuery.class);
	private Session s = DBHandler.getSessionFactory().openSession();

	public void confirmOpen() {

	}

	public void confirmClose() {

	}

	public List<Object[]> getBalance(Date dateFrom, Date dateTo) {
		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trData BETWEEN '2015-06-16' AND '2015-08-23' "
								+ "WHERE trData BETWEEN :fromDate AND :toDate  "
								+ "group by date(trDate) order  by trdata asc")
				.setParameter("fromDate", dateFrom)
				.setParameter("toDate", dateTo);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		// logger.info("List Size is : {}", ol.size());

		// for (Object[] row : ol) {
		// System.out.println(row[0]);
		// System.out.println(row[1]);
		// System.out.println(row[2]);
		// }
		return ol;
	}

	public List<Object[]> getBalance(Date date) {
		int dayte = date.getDate();
		Date d2 = new Date(date.getTime());
		d2.setDate(dayte + 1);
		Query q = s
				.createQuery(
						"SELECT trDate,"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trData BETWEEN '2015-06-16' AND '2015-06-23' "
								+ "WHERE trData > :d1 and  trData < :d2 "
								+ "group by date(trDate) order  by trdata asc")
				.setParameter("d1", date).setParameter("d2", d2);

		logger.info("the query is : {}", q.getQueryString());
		List<Object[]> ol = q.list();
		// logger.info("List Size is : {}", ol.size());

		// for (Object[] row : ol) {
		// System.out.println(row[0]);
		// System.out.println(row[1]);
		// System.out.println(row[2]);
		// }
		return ol;
	}

	public List<Transaction> getTransaction(Date dateFrom, Date dateTo) {
		Query q = s
				.createQuery(
						"FROM Transaction WHERE trData BETWEEN :fromDate AND :toDate ")
				.setParameter("fromDate", dateFrom)
				.setParameter("toDate", dateTo);
		List<Transaction> tl = q.list();

		return tl;
	}

	public List<Transaction> getTransaction(Date date) {
		int dayte = date.getDate();
		Date d2 = new Date(date.getTime());
		d2.setDate(dayte + 1);

		Query q = s
				.createQuery(
						"FROM Transaction WHERE trData > :d1 and trData <:d2 ")
				.setParameter("d1", date).setParameter("d2", d2);
		List<Transaction> tl = q.list();

		// logger.info("------------> Transaction SERVED SUCAZZ");
		return tl;
	}

	public List<Transaction> getAccountInvolvedTrans(String accNr) {
		Query q = s
				.createQuery(
						"FROM Transaction WHERE account1 = :acc1 or account2 = :acc2")
				.setParameter("acc1", accNr).setParameter("acc2", accNr);
		List<Transaction> tl = q.list();
		return tl;
	}

	public List<Transaction> getClientInvolvedTransactionsPart(String persId) {
		Query q = s.createQuery("FROM Transaction WHERE clientId = :id")
				.setParameter("id", persId);
		List<Transaction> tl = q.list();
		return tl;
	}

	public List<Transaction> getClientInvolvedTransactionsAll(
			List<String> persId) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Transaction WHERE ");
		for (int i = 0; i < persId.size(); i++) {
			sb.append(" clientId = :id" + i);
			if (i < persId.size() - 1) {
				sb.append(" or ");
			}
		}
		logger.info("--------------->" + sb.toString());
		Query q = s.createQuery(sb.toString());
		for (int i = 0; i < persId.size(); i++) {
			q.setParameter("id" + i, persId.get(i));
		}
		logger.info("--------------->" + q.toString());
		List<Transaction> tl = q.list();
		if (tl.size() == 0)
			return null;
		
		return tl;
	}

	public Account getAccount(String accId) {
		GeneralFunctions gf = new GeneralFunctions();
		return gf.getAccount(accId);
	}

	public Customers getCustomer(String persId) {
		GeneralFunctions gf = new GeneralFunctions();
		return gf.getCustomer(persId);
	}

	public void upSession() {
		if (!s.isConnected() || !s.isOpen()) {
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
