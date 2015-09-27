package comon;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
//import java.util.logging.Logger;

import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
//import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import cod.test.HQLTest;
import system.AccountsManagment;
import system.Coordinator;
import system.ManagerQuery;
import system.TellerQuery;
import utils.AccGenerator;
import utils.DirMsgWsHandler;
import utils.GeneralFunctions;
import utils.ManMsgHandler;
import utils.TelMsgHandler;
import db.DBHandler;
import entity.Account;
import entity.Customers;
import entity.CustomersAccount;
import entity.Employee;
import entity.EmployeeAction;
import entity.Transaction;
import fileLog.FileHandler;
import functions.CustomerFunctions;
import functions.DirectorFunctions;
import functions.ManagerFunctions;
import functions.TellerFunctions;

public class Run {
	private final static Logger logger = (Logger) LoggerFactory
			.getLogger(Run.class);

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

		// Session ses = DBHandler.getSessionFactory().openSession();

		// TellerFunctions tf = new TellerFunctions(1);
		// tf.register("1111111116", "ARMANDO", "Delavega", "delavega@that.com",
		// "12/01/1990", "under the bridge", "+355...", "321");

		// DirectorFunctions df = new DirectorFunctions(2);
		// for(String s:df.getEmployeeActionsDates()){
		// System.out.println(s);
		// }
		// System.out.println(df.getEmployeeActionData("13"));

		// df.alterEmployee(17, "JOHN", "CENNA", "", "TELLER", "", "");
		// Session s = DBHandler.getSessionFactory().openSession();
		// s.beginTransaction();
		// Query q = s
		// .createQuery("UPDATE Employee set fname = 'JOHN' , lname = 'CENNA', possition = 'TELLER' WHERE empId = 17");
		// System.out.println(q.executeUpdate());
		// s.getTransaction().commit();

		// List<Employee> empl = df.getEmployees("", "", "", "TELLER", "", "");
		// for (Employee e : empl) {
		// e.print();
		// }

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

//		 ManagerFunctions mf1 = new ManagerFunctions(5);
//		 Coordinator list = new Coordinator();
//		 list.addManagerFunc(mf1);
//		 TellerFunctions tf = new TellerFunctions(1);
//		 List<String> sl = new ArrayList<>();
//		 sl.add("1234567890");
//		 sl.add("1234567800");
//		 sl.add("1111111111");
//		 AccountsManagment am = new AccountsManagment ();
//		 am.openAccount(StaticVars.BASICS_CHECKING, sl);
//		 tf.openAccountReq(sl, StaticVars.BASICS_CHECKING);
		 
		 //.openAccount(sl, AccountType.BASIC_CHECKING);
		// tf.closeAccount(sl, "72418514LUCGU34");

		// GeneralFunctions gf = new GeneralFunctions();
		// gf.accountsCountCheck(sl);

		/* Transactions */
		// TellerFunctions tf = new TellerFunctions ();
		// tf.deposite("42218529RQQGP13", 150,"Mario Rista LUVs u");
		// tf.deposite("13105279LUKHI54", 300,"Mario Rista LUVs u 2");
		// tf.withdraw("1111111112", "42218529RQQGP13", 100,"Sa Sa Sa");
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

		// Date d1 = new Date();
		// Date d2 = new Date();
		// d1.setDate(18);
		// d1.setMonth(7);
		// System.out.println("d1 : " + d1);
		//
		// java.sql.Date sq1 = new java.sql.Date(d1.getTime());
		// java.sql.Date sq2 = new java.sql.Date(d2.getTime());
		// System.out.println("sq1 : " + sq1);
		// //
		//
		// Query q = ses
		// .createQuery(
		// "FROM Transaction WHERE trData > :d1 and trData <:d2 ")
		// .setParameter("d1", sq1).setParameter("d2", d2);
		// // List<Transaction> tl =q.getNamedParameters();
		// // q.list();
		// Iterator a = q.iterate();
		//
		// while (a.hasNext()) {
		// Transaction b = (Transaction) a.next();
		// // for (String c : b.) {
		// System.out
		// .println("--------***********------------" + b.getAccount2());
		// // }
		// }
		/*ManagerFunctions mf = new ManagerFunctions(31);
		Coordinator.addManagerFunc(mf);
		TellerFunctions tf = new TellerFunctions(2);
		Coordinator.addTellerFunc(tf);
		List<String> sl = new ArrayList<>();
		// sl.add("1111111111");
		// sl.add("1111111112");
		sl.add("1122334455");
		// tf.openAccountReq(sl, 'b');
		// tf.closeAccountReq(sl, "68427666EXELD92");
		Scanner sc = new Scanner(System.in);

		ManMsgHandler mmh = new ManMsgHandler();

		int x = 1;
		while (x > 0) {
			if (x == 1) {
				OCRequest ocr = new OCRequest(2, sl, StaticVars.OPEN, 'b');
				Coordinator.addOCR(ocr);
			} else if (x == 2) {
				OCRequest ocr = new OCRequest(2, sl, StaticVars.CLOSE,
						"68427666EXELD92");
				Coordinator.addOCR(ocr);
			} else if (x == 3) {
				sl.add("1111111112");
				OCRequest ocr = new OCRequest(2, sl, StaticVars.PLUS_1K_TRANS,
						"70913919GVVTP98", "68427666EXELD92", 1200, "Just A try");
				// String accNr1, String accNr2, double amount, String note2
				Coordinator.addOCR(ocr);
			} else if (x == 4) {
				mf.getOCR();
			} else if (x == 5) {
				mf.leaveOCR();
			} else if (x == 6) {
				mf.decision("ACCEPTED", "YEsss");
			} else if (x == 7) {
				mf.decision("DENNIED", "Noooo");
			}
			System.out.println("-----> " + x);
			System.out.println("ocrs " + Coordinator.ocrListSize());
			System.out.println("ocrs " + Coordinator.getReqCounter());

			x = sc.nextInt();
		}*/
		
		
		//-----------------------------------------------------------------------
		
		TellerFunctions tf= new TellerFunctions ();
		tf.getClientAccounts("1122334455");
		//-----------------------------------------------------------------------
		
		
		
		
		
		
		// ocr.print();

		// TellerQuery tq = new TellerQuery();
		// tq.checkDepositeRegularity("1233dsdd3", 123.44);

		// sl = new ArrayList<>();
		// sl.add("1111111111");
		// OCRequest ocr2 = new OCRequest(2, sl, StaticVars.OPEN, 's');
		// Coordinator.addOCR(ocr2);
		// ocr.print();

		// --------------------

		// logger.info("Coordinator  OCR size={} ",Coordinator.ocrListSize());
		// Coordinator.printAll();
		//
		// logger.info("Manager get a OCR and  print ");
		// mf.getOCR();
		// Coordinator.printAll();
		//
		// mf.confirmOpen(StaticVars.DENIE,
		// "Shure why not, OPS, Found an Error!");
		// Coordinator.printAll();
		//
		// logger.info("Coordinator  OCR size={}",Coordinator.ocrListSize());
		//
		// mf.getReq().print();
		// mf.getOCR();
		// logger.info(mf.getReq().getReqType());
		// mf.returnRequest();
		// Coordinator.printAll();

		// ManagerFunctions mf1 = new ManagerFunctions(32);
		// List<String> sl = new ArrayList<>();
		// sl.add("1111111111");
		// sl.add("1111111112");
		// List<Transaction> ret = mf1.clientInvolvedTransactionsAll(sl);
		// for (Transaction t : ret) {
		// System.out.println(t.getTransactionNr() + " " + t.getTrDate() + " "
		// + t.getAccount1() + " " + t.getAccount2() + " "
		// + t.getAcction() + " " + t.getAmount());
		// }

		// ManagerQuery mq = new ManagerQuery();
		// mq.getBalance(sq1,sq2);
		// mq.getBalance(sq1);
		// mq.getTransaction(sq1);

		// JsonObject jo = new JsonObject ();
		// jo.addProperty("head", "balance");
		// jo.addProperty("date1", sq1.toString());
		// jo.addProperty("date2", sq2.toString());
		// //jo.addProperty("head", "transaction");
		//
		// DirMsgWsHandler.switchit(jo.toString());

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
