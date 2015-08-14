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

		// gf.dtfan(accNr);
		List<String> customersIds = gf.accountsClients(accNr);
		if (customersIds != null) {
			for (String ss : customersIds) {
				gf.accountNrDecreas(ss);
			}
		}
		Session s = DBHandler.getSessionFactory().openSession();
		s.beginTransaction();
		s.createQuery("DELETE Account WHERE accountId=:accNr")
				.setParameter("accNr", accNr).executeUpdate();
		s.getTransaction().commit();
		s.close();
	}

	public String openAccount(char accType, List<String> clientIdsList) {
		AccGenerator accGen = new AccGenerator();
		GeneralFunctions gf = new GeneralFunctions();
		String accNr = accGen.getAccountNr();
		Timestamp t = new java.sql.Timestamp(Calendar.getInstance().getTime()
				.getTime());
		Account acc = new Account(accNr, t, 0, accType, StaticVars.ACTIVE);

		for (String ss : clientIdsList) {
			// hibernate specific, add a tuple to the normalization table
			Customers c = gf.getCustomersAddAccountNr(ss);
			acc.getCustomers().add(c);
		}
		Session s = DBHandler.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(acc);
		s.getTransaction().commit();
		s.close();
		return accNr;
	}
}
