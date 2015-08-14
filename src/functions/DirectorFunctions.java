package functions;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;

import comon.StaticVars;
import system.DirectorQuery;
import entity.Employee;
import entity.EmployeeAction;
import entity.Transaction;
import fileLog.FileHandler;

public class DirectorFunctions extends EmployeeFunctions {

	private int empId;

	public DirectorFunctions(int empId) {
		super();
		this.empId = empId;
	}

	public DirectorFunctions() {
		super();
		this.empId = 000;
	}

	public long createEmployee(String fname, String lname, String address,
			String possition, String eMail, String password) {
		Employee e = new Employee(fname, lname, address, possition, eMail,
				password);
		DirectorQuery dq = new DirectorQuery();
		long regEmpId = dq.register(e);
		if (regEmpId != StaticVars.ERROR
				&& regEmpId != StaticVars.NON_UNIQUE_EMAIL) {
			EmployeeAction ea = new EmployeeAction(regEmpId + "",
					StaticVars.REG_EMPLOYEE, empId);
			super.registerEmployee(ea);
		}
		return regEmpId;
	}

	public long alterEmployee(int empId, String fname, String lname,
			String address, String possition, String eMail, String password) {
		DirectorQuery dq = new DirectorQuery();
		long altEmpId = dq.alterEmp(empId, fname, lname, address, possition,
				eMail, password);
		if (altEmpId != StaticVars.ERROR) {
			EmployeeAction ea = new EmployeeAction(empId + "",
					StaticVars.DEL_EMPLOYEE, empId);
			super.alterEmployee(ea);
		}
		return altEmpId;
	}

	public long deleteEmployee(int empId) {
		DirectorQuery dq = new DirectorQuery();
		long delEmpId = dq.delete(empId);
		if (delEmpId != StaticVars.ERROR
				&& delEmpId != StaticVars.DOES_NOT_EXISTS) {
			EmployeeAction ea = new EmployeeAction(empId + "",
					StaticVars.DEL_EMPLOYEE, empId);
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

	public List<Object[]> getBalance(Date t1,Date t2){
		ManagerFunctions mf= new ManagerFunctions ();
		return mf.getBalance(t1, t2);
	}
	
	public List<Transaction> getTransactions(Date t1,Date t2){
		ManagerFunctions mf= new ManagerFunctions ();
		return mf.getTransaction(t1, t2);
	}
	
	
	
	
}
