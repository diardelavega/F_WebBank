package system;

import java.security.InvalidParameterException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comon.Response;
import comon.StaticVars;
import db.DBHandler;
import entity.Employee;

public class DirectorQuery {
	private final Logger logger = LoggerFactory.getLogger(DirectorQuery.class);

	private Session s = DBHandler.getSessionFactory().openSession();

	public Employee getEmp(int empId) {
		try {
			Employee e = (Employee) s.get(Employee.class, empId);
			return e;
		} catch (Exception e) {
			s.close();
			return null;
		}
	}

	public boolean existsEmail(String mail) {
		if (!s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
		List q;
		q = s.createQuery(
				"SELECT eMail FROM Employee WHERE eMail like '" + mail + "' ")
				.list();
		if (q.size() > 0) {
			logger.info(q.get(0).toString());
			return true;
		}

		return false;
	}

	public long register(Employee e) {
		logger.info("on register");
		if (existsEmail(e.geteMail())) {
			logger.info("NON_UNIQUE_EMAIL");
			return StaticVars.NON_UNIQUE_EMAIL;

		}

		// if (getEmp(e.getEmpId()) == null) {
		if (!s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}

		long empId = StaticVars.ERROR;
		logger.info("emp id is ERROR");
		try {
			s.beginTransaction();
			empId = (int) s.save(e);
			s.getTransaction().commit();
		} catch (Exception e1) {
			s.getTransaction().rollback();
			e1.printStackTrace();
			logger.info(
					"registration failed from some error in persistance= {}",
					empId);
		}
		s.close();
		logger.info("registration is completed empId= {}", empId);
		return empId;
		// }
		// return StaticVars.DUPLICATE_ENTRY;
	}

	public long delete(Employee e) {
		if (getEmp(e.getEmpId()) == null) {
			System.out.println(Response.DOES_NOT_EXISTS);
			logger.info("Employee {}", Response.DOES_NOT_EXISTS);
		}
		int empId = e.getEmpId();
		if (!s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
		s.beginTransaction();
		try {
			s.delete(e);
			s.getTransaction().commit();
			s.close();
			System.out.println(Response.DONE);
			logger.info("Employee {}", Response.DOES_NOT_EXISTS);
			return empId;
		} catch (HibernateException e1) {
			e1.printStackTrace();
		}
		return StaticVars.ERROR;
	}

	public long delete(int empId) {
		Employee e = getEmp(empId);
		if (e == null) {
			System.out.println(Response.DOES_NOT_EXISTS);
			return StaticVars.DOES_NOT_EXISTS;
		}
		if (!s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
		try {
			s.beginTransaction();
			s.delete(e);
			s.getTransaction().commit();

			System.out.println(Response.DONE);
			return empId;
		} catch (HibernateException e1) {
			e1.printStackTrace();
			s.getTransaction().rollback();
			s.close();
			return StaticVars.ERROR;
		}

	}

	public List<Employee> getAllEmployees() {
		Query q = s.createQuery("FROM Employee");
		List<Employee> el = q.list();
		s.close();
		return el;
	}

	public List<Employee> getAllEmployees(String fname, String lname,
			String address, String possition, String eMail, String password) {

		logger.info("in DQ ");
		String condition = empQueryCondition(fname, lname, address, possition,
				eMail, password);
		if (condition != null) {
			condition = " WHERE " + condition;
		} else {
			condition = "";
		}
		logger.info("condition = {} ", condition);

		Query q = s.createQuery("FROM Employee " + condition);
		List<Employee> el = q.list();
		// logger.info("emps size={}",el.size());
		s.close();
		// logger.info("emps size={}",el.size());
		return el;
	}

	public long alterEmp(int empId, String fname, String lname, String address,
			String possition, String eMail, String password) {
		logger.info("in DQ ");
		String condition = "";
		condition = empAlterFields(fname, lname, address, possition, eMail,
				password);
		if (condition != null) {
			logger.info("condition = {} ", condition);
			s.beginTransaction();
			try {
				s.createQuery(
						"UPDATE Employee  set " + condition + " WHERE empId= "
								+ empId).executeUpdate();
				s.getTransaction().commit();
				s.close();
				logger.info("Employee with id ={}, changed {} ", empId,
						condition);
			} catch (HibernateException e) {
				e.printStackTrace();
				s.getTransaction().rollback();
				return StaticVars.ERROR;
			}
		} else {
			logger.info("condition = {} ", condition);
			logger.info("Nothing to update ");
			throw new InvalidParameterException("Nothing to update ??");
		}
		return empId;

	}

	private String empQueryCondition(String fname, String lname,
			String address, String possition, String eMail, String password) {

		boolean flag = false;
		String condition = " ";
		if (!fname.equals("")) {
			condition += "fname like '" + fname + "%' ";
			flag = true;
		}
		if (!lname.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "lname like '" + lname + "%' ";
			flag = true;
		}
		if (!address.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "address like '" + address + "%' ";
			flag = true;
		}
		if (!possition.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "possition like '" + possition + "%' ";
			flag = true;
		}
		if (!eMail.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "eMail like '" + eMail + "%' ";
			flag = true;
		}
		if (!password.equals("")) {
			if (flag) {
				condition += " AND ";
			}
			condition += "password like '" + password + "%' ";
		}
		if (flag) {
			return condition;
		} else {
			return null;
		}
	}

	private String empAlterFields(String fname, String lname, String address,
			String possition, String eMail, String password) {

		boolean flag = false;
		String condition = "";
		if (!fname.equals("")) {
			condition += " fname  = '" + fname + "' ";
			flag = true;
		}
		if (!lname.equals("")) {
			if (flag) {
				condition += ',';
			}
			condition += " lname = '" + lname + "' ";
			flag = true;
		}
		if (!address.equals("")) {
			if (flag) {
				condition += ',';
			}
			condition += " address = '" + address + "' ";
			flag = true;
		}
		if (!possition.equals("")) {
			if (flag) {
				condition += ',';
			}
			condition += " possition = '" + possition + "' ";
			flag = true;
		}
		if (!eMail.equals("")) {
			if (flag) {
				condition += ',';
			}
			condition += " eMail = '" + eMail + "' ";
			flag = true;
		}
		if (!password.equals("")) {
			if (flag) {
				condition += ',';
			}
			condition += " password = '" + password + "' ";
		}
		if (flag) {
			return condition;
		} else {
			return null;
		}
	}
}
