package comon;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
//import java.util.logging.Logger;




import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import com.google.gson.JsonObject;
import com.sun.media.jfxmedia.logging.Logger;

import cod.test.HQLTest;
import system.Lists;
import system.ManagerQuery;
import utils.AccGenerator;
import utils.DirMsgWsHandler;
import utils.GeneralFunctions;
import db.DBHandler;
import entity.Account;
import entity.Customers;
import entity.CustomersAccount;
import entity.Employee;
import entity.EmployeeAction;
import fileLog.FileHandler;
import functions.CustomerFunctions;
import functions.DirectorFunctions;
import functions.ManagerFunctions;
import functions.TellerFunctions;

public class Run {
	// private static final Logger logger = Logger.getLogger(Run.class);

	public static void main(String[] args) throws ParseException, IOException {

		// CustomerFunctions cf = new CustomerFunctions();
		//
		// cf.register("1234567890", "Mario", "Rista", "this@that.com",
		// "12/01/1990", "under the bridge", "+355...", "123");
		//
		// cf.register("1234567899", "Mariano", "Rista", "this2@that.com",
		// "12/01/1990", "under the bridge", "+355...", "321");
		//
		// cf.register("1234567800", "Marius", "Rista", "this3@that.com",
		// "12/01/1990", "under the bridge", "+355...", "321");

		// LogIn log = new LogIn();
		// Employee e = log.logInEmp("diego5@webank.com", "12345");
		// e.print();
		// log.closeSession();
		// logger.info(resp);
		// // Session ses = DBHandler.getSessionFactory().openSession();

		// TellerFunctions tf = new TellerFunctions(1);
		// tf.register("1111111116", "ARMANDO", "Delavega", "delavega@that.com",
		// "12/01/1990", "under the bridge", "+355...", "321");

//		DirectorFunctions df = new DirectorFunctions(2);
//		for(String s:df.getEmployeeActionsDates()){
//			System.out.println(s);
//		}
//		System.out.println(df.getEmployeeActionData("13"));
		
		// df.alterEmployee(17, "JOHN", "CENNA", "", "TELLER", "", "");
//		Session s = DBHandler.getSessionFactory().openSession();
//		s.beginTransaction();
//		Query q = s
//				.createQuery("UPDATE Employee set fname = 'JOHN' , lname = 'CENNA', possition = 'TELLER' WHERE empId = 17");
//		System.out.println(q.executeUpdate());
//		s.getTransaction().commit();

//		List<Employee> empl = df.getEmployees("", "", "", "TELLER", "", "");
//		for (Employee e : empl) {
//			e.print();
//		}

		// System.out.println(df.createEmployee(12, "Diego", "Delavega",
		// "vasil bozo st.", "TELLER", "diego@webank.com", "1234"));

		// df.createEmployee( "SPILO", "HUKK",
		// "vasil bozo st.", "MENAGER", "shk@webank.com", "12345");

		// for(Employee e:df.getEmployees()){
		// e.print();
		// }

		// System.out.println(df.deleteEmployee(13));
		/* open and close accounts + analogous accNr change */
		// FileHandler fh= new FileHandler ();
		// fh.init();
		// fh.filePlacementControl();

		// ManagerFunctions mf1 = new ManagerFunctions(5);
		// Lists list = new Lists();
		// list.addMenager(mf1);
		// TellerFunctions tf = new TellerFunctions(1);
		// List<String> sl = new ArrayList<>();
		// sl.add("1234567890");
		// sl.add("1234567800");
		// sl.add("1111111112");
		// tf.openAccount(sl, AccountType.BASIC_CHECKING);
		// tf.closeAccount(sl, "72418514LUCGU34");

		/* Transactions */
		// TellerFunctions tf = new TellerFunctions ();
		// tf.deposite("42218529RQQGP13", 100,"Maracaibo");
		// tf.withdraw("1111111112", "42218529RQQGP13", 75,"Sa Sa Sa");
		// tf.transfer("1111111111", "42218529RQQGP13", "70913919GVVTP98",
		// 10.5,"AAAAA");

		// CustomerFunctions cf =new CustomerFunctions();
		// cf.info("42218529RQQGP13");

		// HQLTest hq = new HQLTest();
		// hq.upSession();

		// GregorianCalendar gc = new GregorianCalendar();
		// // GregorianCalendar gc2=new GregorianCalendar ();
		// String s1, s2;
		// s1 = format(gc);
		// // System.out.println(format(gc));
		// gc.add(Calendar.DATE, -6);
		// // System.out.println(format(gc));
		// s2 = format(gc);

		 Date d1 = new Date();
		 Date d2 = new Date();
		 d1.setDate(18);
		 d1.setMonth(5);
		 System.out.println("d1 : "+d1);
		 
		 java.sql.Date sq1 = new java.sql.Date(d1.getTime());
		 java.sql.Date sq2 = new java.sql.Date(d2.getTime());
		 System.out.println("sq1 : "+sq1);
		//
//		 ManagerQuery mq = new ManagerQuery();
//		 mq.getBalance(sq1,sq2);
//		 mq.getBalance(sq1);
//		 mq.getTransaction(sq1);
		 
		 JsonObject jo = new JsonObject ();
		 jo.addProperty("head", "balance");
		 jo.addProperty("date1", sq1.toString());
		 jo.addProperty("date2", sq2.toString());
		 //jo.addProperty("head", "transaction");
		 
		 DirMsgWsHandler.switchit(jo.toString());
		 

		DBHandler.closeSessionFactory();

		// logger.debug("WTF");
		// FileHandler fh = new FileHandler();
		//
		// fh.init();
		// fh.filePlacementControl();
		// fh.append("MOFO noo boo");

		// URL location =
		// Run.class.getProtectionDomain().getCodeSource().getLocation();
		// System.out.println(location.getFile());

		// EmployeeAction ea = new EmployeeAction();
		// System.out.println(ea.idGenerator());

		// FIX HIBERNATE ANNOTATIONS, it does not insert into customers account;
		// TODO tie loos ends

		/*
		 * String v1 = StaticVars.ACCEPT; if (v1 == StaticVars.ACCEPT) {
		 * System.out.println("YES1"); } if (v1.equals(StaticVars.ACCEPT)) {
		 * System.out.println("YES2"); }
		 */

	}
	// public static String format(GregorianCalendar calendar) {
	// SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	// // fmt.setCalendar(calendar);
	// String dateFormatted = fmt.format(calendar.getTime());
	// return dateFormatted;
	// }
}
