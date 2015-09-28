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
		// Session s = DBHandler.getSessionFactory().openSession();
		upSession();
		String[] temp = usr.split("@");
		String query;

		if (temp[1].equals("webank")) {
			query = "FROM Employee WHERE eMail= :email AND password= :pas";
		} else {
			query = "FROM Customers WHERE eMail= :email AND password= :pas";
		}
		// Session s = DBHandler.getSessionFactory().openSession();
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
		return c;
	}

	public List<String> accountsClients(String accNr) {
		upSession();
		List<String> clientIds = s
				.createQuery(
						"SELECT personalId FROM CustomersAccount WHERE accountId = :accNr")
				.setParameter("accNr", accNr).list();
		System.out.println("------------------------->" + clientIds.size());
		// s.close();
		// if (clientIds == null)
		// return null;
		return clientIds;
	}

	public List<String> clientsAccounts(String personalId) {
		upSession();
		List<String> accounts = s
				.createQuery(
						"SELECT accountId FROM CustomersAccount WHERE personalId=:personalId")
				.setParameter("personalId", personalId).list();
		// s.close();
		if (accounts.size() == 0)
			return null;
		return accounts;
	}

	public Account getAccount(String accNr) {
		Account acc = null;
		upSession();
		try {
//			List<Account> accl = s
//					.createQuery(
//							"SELECT accountId, accStatus, accType,balance,openDate From Account WHERE accountId=:accId")
//					.setParameter("accId", accNr).list();
//			if (accl.size() == 0)
//				return null;
//			else {
//				acc = (Account) accl.get(0);
//				 acc= new Account(accl.get(0)[0].,accl.get(0)[4],accl.get(0)[3],accl.get(0)[2],accl.get(0)[1]);
//			}
			 acc = (Account) s.get(Account.class, accNr);
			 acc.setCustomers(null);//we don't need the customers
		} catch (Exception e) {
			e.printStackTrace();
		}
		s.close();
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
		for (String ss : pId) {
			if (getCustomer(ss) == null) {
				unreg.add(ss);
			}
		}
		return unreg;
	}

	public List<String> accountsCountCheck(List<String> pId) {
		// find clients with more than 6 accounts
		upSession();
		List<String> pluss6AccLst = new ArrayList<String>();
		Query q = s
				.createQuery("SELECT count(*) FROM CustomersAccount WHERE personalId=:pid");
		long acs;
		for (String ss : pId) {
			acs = (long) q.setParameter("pid", ss).list().get(0);
			if (acs >= 6) {
				pluss6AccLst.add(ss);
				// System.out.println("--- "+((long)q.setParameter("pid",
				// ss).list().get(0)));
				// System.out.println("--- "+(acs));
			}
		}
		return pluss6AccLst;
	}

	public void registrationCheck(String pId) {
		upSession();
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
		// if (!s.isConnected() || !s.isOpen()) {
		s = DBHandler.getSessionFactory().openSession();
		// }
	}

	public void closeSession() {
		try {
			s.flush();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String valEMail(String mail) {
		String error = "";
		if (mail.length() > 40) {
			error = " email too long, max 40 char! ";
		} else if (mail.length() < 5) {
			error = " invalid email! ";
		} else {
			String tab[] = mail.split("[@.]");
			for (int i = 0; i < 3; i++) {
				if (tab[i].equals("") || tab[i] == null) {
					error = " invalid email! ";
				}
			}
		}
		return error;
	}

	public String valDubleMail(String mail) {
		if (isCustomerEmail(mail))
			return " e-mail already registered! ";
		return "";
	}

	public String valName(String name) {
		String error = "";
		if (name.length() > 30) {
			error = " name too long, max 30 char! ";
		} else if (name.length() < 5) {
			error = " name too short, min 5 char! ";
		}
		return error;
	}

	public String valPsw(String psw) {
		String error = "";
		if (psw.length() > 24) {
			error = " psw too long, max 24 char! ";
		}
		if (psw.length() < 5) {
			error = " psw too short, min 5 char! ";
		}
		return error;
	}

	public String valAddress(String address) {
		String error = "";
		if (address.length() > 50) {
			error = " address too long, max 50 char! ";
		}
		if (address.length() < 6) {
			error = " address too short ";
		}
		return error;
	}

	public String valPhone(String phone) {
		String error = "";
		if (phone.length() > 10) {
			error = " phone too long, max 10 char! ";
		} else if (phone.length() < 10) {
			error = " incorrect phone number ";
		} else {
			for (int i = 0; i < phone.length(); i++) {
				if (!Character.isDigit(phone.charAt(i))) {
					error = "not valid phone number! ";
					return error;
				}
			}
		}
		return error;
	}

	public String valAmmount(String amount) {
		String error = null;
		String[] temp = null;
		boolean flag = false;

		if (amount.length() == 0) {
			error = "Not a valid amount";
		} else if (amount.contains("-")) {
			error = "Not a valid amount";
		} else if (amount.contains(".")) {
			flag = true;
			temp = amount.split("\\.");
		}

		if (temp[0].length() > 5) {
			error = " ammount value is too big, integer part max 5 digits! ";
		}
		for (int i = 0; i < temp[0].length(); i++) {
			if (!Character.isDigit(temp[0].charAt(i))) {
				return "not valid ammount entry!, try float point number ";
			}
		}
		if (flag) {
			if (temp[1].length() > 7) {
				error = " ammount precision is exciding the limit, max 7 digits! ";
			}
			for (int i = 0; i < temp[1].length(); i++) {
				if (!Character.isDigit(temp[1].charAt(i))) {
					return "not valid ammount entry!, try float point number ";
				}
			}
		}
		System.out.println("ammount " + amount);
		return error;
	}

	public String valNote(String note) {
		if (note.length() > 20)
			return " ammount value is too big, max 5 digits! ";
		return "";
	}

	public boolean isCustomerEmail(String mail) {
		upSession();
		Query q = s.createQuery(
				"SELECT eMail FROM Customers WHERE eMail like :em")
				.setParameter("em", mail);
		if (q.list().size() != 0)
			return true;
		return false;
	}

}
