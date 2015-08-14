package functions;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DBHandler;
import entity.EmployeeAction;
import fileLog.FileHandler;

public class EmployeeFunctions {
	private Logger logger = LoggerFactory.getLogger(EmployeeFunctions.class);
	private Session s = DBHandler.getSessionFactory().openSession();
	private String wsSession; 

	protected void requestOpenAcc(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void requestCloseAcc(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void confirmOpenAcc(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void confirmCloseAcc(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void registerClient(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void registerEmployee(EmployeeAction ea) {
		dbStore(ea);
	}
	protected void alterEmployee(EmployeeAction ea) {
		dbStore(ea);
	}
	protected void deleteEmployee(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void deposite(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void withdraw(EmployeeAction ea) {
		dbStore(ea);
	}

	protected void transfer(EmployeeAction ea) {
		dbStore(ea);
	}

	public String getWsSession() {
		return wsSession;
	}

	public void setWsSession(String wsSession) {
		this.wsSession = wsSession;
	}

	protected void info() {
	}

	private void dbStore(EmployeeAction ea) {
		FileHandler fh = new FileHandler();
		try {
			fh.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fh.append(ea.prinatable());
		// upSession();
		// Transaction tr = s.beginTransaction();
		// s.save(ea);
		// s.flush();
		// tr.commit();
		// s.close();
		logger.info("Suppose something was Done");
	}

	protected void upSession() {
		if (!s.isConnected() || !s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
	}

	protected void closeSession() {
		try {
			s.flush();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
