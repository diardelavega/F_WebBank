package system;

import java.sql.Timestamp;
import java.util.ArrayList;
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
			System.out.println("Closing accoutn accNr: "+accNr);
		} catch (Exception e) {
			System.out.println("Problems Closing accoutn accNr: "+accNr);
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
			List<Customers>cl = new ArrayList<>();
			for (String ss : clientIdsList) {
				Customers c = gf.getCustomersAddAccountNr(ss);
				cl.add(c);
//				acc.getCustomers().add(c);
			}
			acc.setCustomers(cl);
			s.beginTransaction();
			s.save(acc);
			s.flush();
			s.getTransaction().commit();
			System.out.println("Account opened, accNr: " + accNr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			s.getTransaction().rollback();
			e.printStackTrace();
			System.out
					.println("Account NOT opened, Problems Finalizing accNr: "
							+ accNr);
		}
		s.close();
		return accNr;
	}
}
