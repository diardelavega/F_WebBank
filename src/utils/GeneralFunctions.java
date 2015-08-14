package utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.jboss.weld.exceptions.UnsupportedOperationException;

import com.sun.xml.internal.ws.api.client.ThrowableInPacketCompletionFeature;

import db.DBHandler;
import entity.Account;
import entity.Customers;
import entity.EmployeeAction;

public class GeneralFunctions {
	private Session s;// = DBHandler.getSessionFactory().openSession();

	public String logIn(String usr, String psw) {
//		Session s = DBHandler.getSessionFactory().openSession();
		upSession();
		String[] temp = usr.split("@");
		String query;

		if (temp[1].equals("webank")) {
			query = "FROM Employee WHERE eMail= :email AND password= :pas";
		} else {
			query = "FROM Customers WHERE eMail= :email AND password= :pas";
		}
//		Session s = DBHandler.getSessionFactory().openSession();
		upSession();
		Query q = s.createQuery(query).setParameter("email", usr)
				.setParameter("pas", psw);
		Customers c = (Customers) q.list().get(0);
		s.close();

		return c.getPersonalId();
	}

	public Customers getCustomer(String personalId) {
		upSession();
		// Customers c = (Customers) s
		// .createQuery("FROM Customers WHERE personalId = :personalId")
		// .setParameter("personalId", personalId).list().get(0);
		// s.beginTransaction().commit();
		// s.close();
		Customers c = (Customers) s.get(Customers.class, personalId);
		if (c == null)
			return null;
		return c;
	}

	public Customers getCustomersAddAccountNr(String personalId) {
		upSession();
		Customers c = null;
		// c = (Customers) s
		// .createQuery(
		// "FROM Customers WHERE personalId = :personalId")
		// .setParameter("personalId", personalId).list().get(0);
		Transaction tr = s.beginTransaction();
		try {
			c = (Customers) s.get(Customers.class, personalId);
			c.setAccountsNr(c.getAccountsNr() + 1);
			s.persist(c);
			s.flush();
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		s.close();
		if (c == null)
			return null;
		return c;
	}

	public List<String> accountsClients(String accNr) {
		upSession();
		List<String> clientIds = s
				.createQuery(
						"SELECT personalId FROM CustomersAccount WHERE accountId = :accNr")
				.setParameter("accNr", accNr).list();
		// s.close();
		if (clientIds == null)
			return null;
		return clientIds;
	}

	public List<String> clientsAccounts(String personalId) {
		upSession();
		List<String> accounts = s
				.createQuery(
						"SELECT accountId FROM CustomersAccount WHERE personalId=:personalId")
				.setParameter("personalId", personalId).list();
		// s.close();
		if (accounts == null)
			return null;
		return accounts;
	}

	public Account getAccount(String accNr) {
		Account acc = null;
		upSession();
		try {
			acc = (Account) s.get(Account.class, accNr);
		} catch (Exception e) {
			e.printStackTrace();
			s.close();
		}
		return acc;
	}

	public boolean existsAccount(String accNr) {
		upSession();
		if (s.createQuery(
				"SELECT accountId FROM Account WHERE accountId=:accId")
				.setParameter("accId", accNr).list().size() > 0) {
			s.close();
			return true;
		}
		s.close();
		return false;
	}

	public void accountNrDecreas(String personalId) {
		upSession();
		Transaction tx = s.beginTransaction();
		try {
			Customers c = (Customers) s.load(Customers.class, personalId);
			c.setAccountsNr(c.getAccountsNr() - 1);
			s.persist(c);
			s.flush();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		}
		s.close();
	}

	public List<String> registrationCheck(List<String> pId) {
		// check for unregistered clients
		List<String> unreg = new ArrayList<String>();
		Query q = s
				.createQuery("SELECT personalId FROM Customers WHERE personalId=:pid");
		for (String ss : pId) {
			if (q.setParameter("pid", ss).list().size() == 0) {
				unreg.add(ss);
			}
		}
		return unreg;
	}

	public void registrationCheck(String pId) {
		String query = "SELECT personalId FROM Customers WHERE personalId=:pid";
		Query q = s.createQuery(query).setParameter("pid", pId);
		String ss = null;
		if (q.list().size() > 0) {
			ss = (String) q.list().get(0);
		}
		System.out.println(ss);
		// Criteria criter =
		// s.createCriteria(Customers.class).setProjection(Projections.projectionList());
		// for (String ss : pId) {
		// q.setParameter("pid", ss).list().get(0);
		//
		// }
	}

	public void employeeActionRecord(EmployeeAction ea) {
		upSession();
		Transaction tr = s.beginTransaction();
		s.save(ea);
		s.flush();
		tr.commit();
		s.close();
	}

	public void upSession() {
	//	if (!s.isConnected() || !s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		//}
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
