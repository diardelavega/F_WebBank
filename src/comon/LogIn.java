package comon;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.ManagerQuery;
import db.DBHandler;
import entity.Customers;
import entity.Employee;

public class LogIn implements Serializable {
	private static final long serialVersionUID = -5071035983285999429L;
	/**
	 * 
	 */

	private final Logger logger = LoggerFactory.getLogger(LogIn.class);
	private Session s = DBHandler.getSessionFactory().openSession();

	public Employee logInEmp(String usr, String psw) {
		logger.info("----------------------> logInEmp");
		Employee e = null;
		try {
			e = (Employee) s
					.createQuery(
							"FROM Employee WHERE eMail = :usr AND password = :psw ")
					.setParameter("usr", usr).setParameter("psw", psw).list()
					.get(0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return e;
	}

	public Customers logInCust(String usr, String psw) {
		logger.info("-------------------------------> logInCust");
		Customers c = null;
		try {
			c = (Customers) s
					.createQuery(
							"FROM Customers WHERE eMail = :usr AND password = :psw")
					.setParameter("usr", usr).setParameter("psw", psw).list()
					.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	public void closeSession() {
		try {
			s.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
