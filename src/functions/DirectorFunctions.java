package functions;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comon.StaticVars;
import system.DirectorQuery;
import utils.GeneralFunctions;
import entity.Employee;
import entity.EmployeeAction;
import entity.Transaction;
import fileLog.FileHandler;

public class DirectorFunctions extends EmployeeFunctions {
	public static Logger logger = LoggerFactory
			.getLogger(DirectorFunctions.class);
	private int empId;
	private Session wsSession;

	public DirectorFunctions(int empId) {
		super();
		this.empId = empId;
	}

	public DirectorFunctions() {
		super();
		this.empId = 000;
	}

	public String createEmployee(String fname, String lname, String address,
			String possition, String eMail, String password) {

		GeneralFunctions gf = new GeneralFunctions();
		logger.info("IN Director QUERY");
		StringBuilder sb = new StringBuilder();// the validation error msgs
		sb.append(gf.valName(fname));
		sb.append(gf.valName(lname));
		sb.append(gf.valAddress(address));
		sb.append(gf.valEMail(eMail));
		sb.append(gf.valDubleMail(eMail));
		sb.append(gf.valPsw(password));
		if (sb.toString().length() > 0) {
			return sb.toString();
		}

		Employee e = new Employee(fname, lname, address, possition, eMail,
				password);
		DirectorQuery dq = new DirectorQuery();
		long regEmpId = dq.register(e);
		if (regEmpId != StaticVars.ERROR) {
			EmployeeAction ea = new EmployeeAction();
			ea.setActionType(StaticVars.REG_EMPLOYEE);
			ea.setEmpId(empId);
			List<String> ls = new ArrayList<>();
			ls.add(regEmpId + "");
			ea.setCustomerId(ls);
			super.registerEmployee(ea);
			return "";
		} else {
			return "An Error Ocoured, Registration Not Completed";
		}

	}

	public String alterEmployee(int empId, String fname, String lname,
			String address, String possition, String eMail, String password) {
		GeneralFunctions gf = new GeneralFunctions();
		logger.info("IN Director QUERY");
		StringBuilder sb = new StringBuilder();// the validation error msgs
		sb.append(gf.valName(fname));
		sb.append(gf.valName(lname));
		sb.append(gf.valAddress(address));
		sb.append(gf.valEMail(eMail));
		sb.append(gf.valDubleMail(eMail));
		sb.append(gf.valPsw(password));
		if (sb.toString().length() > 0) {
			return sb.toString();
		}
		DirectorQuery dq = new DirectorQuery();
		long altEmpId = dq.alterEmp(empId, fname, lname, address, possition,
				eMail, password);

		logger.info("altEmpId = {}", altEmpId);
		if (altEmpId != StaticVars.ERROR) {
			EmployeeAction ea = new EmployeeAction();
			ea.setActionType(StaticVars.ALTER_EMPLOYEE);
			ea.setEmpId(empId);
			List<String> ls = new ArrayList<>();
			ls.add(altEmpId + "");
			ea.setCustomerId(ls);
			super.alterEmployee(ea);
			return "";
		} else {
			return "An Error Ocoured, Registration Not Completed";
		}
	}

	public long deleteEmployee(int empId) {
		DirectorQuery dq = new DirectorQuery();
		long delEmpId = dq.delete(empId);

		if (delEmpId != StaticVars.ERROR
				&& delEmpId != StaticVars.DOES_NOT_EXISTS) {
			EmployeeAction ea = new EmployeeAction();
			ea.setActionType(StaticVars.DEL_EMPLOYEE);
			ea.setEmpId(empId);
			List<String> ls = new ArrayList<>();
			ls.add(delEmpId + "");
			ea.setCustomerId(ls);
			super.deleteEmployee(ea);
		}
		return delEmpId;
	}

	public Employee getEmplyee(int empId) {
		DirectorQuery dq = new DirectorQuery();
		return dq.getEmp(empId);
	}

	public List<Employee> getEmployees() {
		DirectorQuery dq = new DirectorQuery();
		return dq.getAllEmployees();
	}

	public List<Employee> getEmployees(String fname, String lname,
			String address, String possition, String eMail, String password) {
		DirectorQuery dq = new DirectorQuery();
		return dq.getAllEmployees(fname, lname, address, possition, eMail,
				password);
	}

	public List<String> getEmployeeActionsDates() {
		// TODO read data from FileHandler
		// get the name of the files available
		FileHandler fh = new FileHandler();
		return fh.EAFileNames();
	}

	public String getEmployeeActionData(String name) throws IOException {
		FileHandler fh = new FileHandler();
		return fh.EAData(name);
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public Session getWsSession() {
		return wsSession;
	}

	public void setWsSession(Session wsSession) {
		this.wsSession = wsSession;
	}

	public List<Object[]> getBalance(Date t1, Date t2) {
		ManagerFunctions mf = new ManagerFunctions();
		return mf.getBalance(t1, t2);
	}

	public List<Transaction> getTransactions(Date t1, Date t2) {
		ManagerFunctions mf = new ManagerFunctions();
		return mf.getTransaction(t1, t2);
	}

}
