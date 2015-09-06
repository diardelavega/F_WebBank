package system;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import db.DBHandler;
import entity.Account;
import entity.Customers;
import utils.AccGenerator;
import utils.GeneralFunctions;

import org.hibernate.Session;

import comon.StaticVars;

public class AccountsManagment {

	public void closeAccount(String accNr) {
		GeneralFunctions gf = new GeneralFunctions();
		List<String> customersIds = gf.accountsClients(accNr);

		Session s = DBHandler.getSessionFactory().openSession();
		try {
			s.beginTransaction();
			s.createQuery("DELETE Account WHERE accountId=:accNr")
					.setParameter("accNr", accNr).executeUpdate();
			// Account a = (Account) s.load(Account.class, accNr);
			// s.delete(a);
			s.getTransaction().commit();
			for (String ss : customersIds) {
				gf.accountNrDecreas(ss);
			}
		} catch (Exception e) {
			s.getTransaction().rollback();
			e.printStackTrace();
		}
		s.close();
	}

	public String openAccount(char accType, List<String> clientIdsList) {
		AccGenerator accGen = new AccGenerator();
		GeneralFunctions gf = new GeneralFunctions();
		String accNr = accGen.getAccountNr();
		Timestamp t = new java.sql.Timestamp(Calendar.getInstance().getTime()
				.getTime());
		Account acc = new Account(accNr, t, 0, accType, StaticVars.ACTIVE);

		Session s = DBHandler.getSessionFactory().openSession();
		try {
			for (String ss : clientIdsList) {
				Customers c = gf.getCustomersAddAccountNr(ss);
				acc.getCustomers().add(c);
			}
			s.beginTransaction();
			s.save(acc);
			s.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.close();
		return accNr;
	}
}
