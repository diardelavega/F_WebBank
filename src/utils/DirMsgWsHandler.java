package utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.Coordinator;
import system.DirectorQuery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.StaticVars;
import entity.Employee;
import entity.Transaction;
import functions.DirectorFunctions;
import functions.ManagerFunctions;

public class DirMsgWsHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(DirMsgWsHandler.class);
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	private static DirectorFunctions df;

	public static String switchit(JsonObject jobj, String head) {
		// JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		// String head = jobj.get("head").getAsString();
		// JsonObject jo = new JsonObject();

		int empId;
		try {
			empId = Integer.parseInt(jobj.get("empId").getAsString());
			df = Coordinator.getDirectorFunc(empId);
		} catch (Exception e) {
			logger.warn("EMP ID WAS NOt FOUND");
			df = new DirectorFunctions();
			e.printStackTrace();
		}

		switch (head) {
		case "new":
			return newEmployee(df, jobj);
			// break;
		case "info":
			return employeeInfo(df, jobj);
			// break;
		case "alter":
			return alterEmployee(df, jobj);
			// break;
		case "delete":
			return deleteEmployee(df, jobj);
			// break;
		case "balance":
			return getBalance(df, jobj);
			// break;
		case "transaction":
			return getTransactions(df, jobj);
			// break;
		case "empAct":
			break;
		case "logout":
			return logout(jobj);
			// case "coordinate":
			// coordinate(jobj);
			// break;
		default:
			logger.info("invalid switch criterias");
		}
		return null;
	}

	private static String logout(JsonObject jobj) {

		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "logoutReplay");
		jo.addProperty("response", "OK!");
		try {
			Coordinator.deleteDirector(jobj.get("empId").getAsInt());
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("response", "Something Whent Wrong");
		}
		return gson.toJson(jo);
	}

	private static String deleteEmployee(DirectorFunctions df, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		int id = -1;
		try {
			id = jobj.get("id").getAsInt();

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("incorect id passage from jsomObj");
			jo.addProperty("head", "error");
			jo.addProperty("msg", "Invalid Employee Id");
		}

		logger.info("into variable search");
		logger.info("DATA: fname={}  lname={},  email={}", jobj.get("fname")
				.getAsString(), jobj.get("lname").getAsString(),
				jobj.get("eMail").getAsString());
		long empId = df.deleteEmployee(id);

		if (empId == StaticVars.DOES_NOT_EXISTS) {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "EMPLOYEE DOES_NOT_EXISTS");
		} else if (empId == StaticVars.ERROR) {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "unable to update, comit problem");
		} else {
			jo.addProperty("head", "deleteEmployee");
			jo.addProperty("del", "Deletion Completed");
		}
		String jsonResp = gson.toJson(jo);
		logger.info("jsonResp ={}", jsonResp);
		return jsonResp;

	}

	private static String alterEmployee(DirectorFunctions df, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		int id = -1;
		try {
			id = jobj.get("id").getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("incorect id passage from jsomObj");
			jo.addProperty("head", "error");
			jo.addProperty("msg", "Invalid Employee Id");
		}

		logger.info("into variable search");
		logger.info("DATA: fname={}  lname={},  email={}", jobj.get("fname")
				.getAsString(), jobj.get("lname").getAsString(),
				jobj.get("eMail").getAsString());

		String fname = jobj.get("fname").getAsString();
		String lname = jobj.get("lname").getAsString();
		String address = jobj.get("address").getAsString();
		String possition = jobj.get("possition").getAsString();
		String eMail = jobj.get("eMail").getAsString();
		String password = jobj.get("password").getAsString();

		String ret = df.alterEmployee(id, fname, lname, address, possition,
				eMail, password);

		jo.addProperty("head", "alterEmployee");
		if (ret.equals("")) {
			jo.addProperty("alt", "Aleration Completed");
		} else {
			jo.addProperty("msg", ret);
		}

		String jsonResp = gson.toJson(jo);
		return jsonResp;

	}

	private static String newEmployee(DirectorFunctions df, JsonObject jobj) {
		// check and register a new Employee
		JsonObject jo = new JsonObject();

		String fname = jobj.get("fname").getAsString();
		String lname = jobj.get("lname").getAsString();
		String address = jobj.get("address").getAsString();
		String possition = jobj.get("possition").getAsString();
		String eMail = jobj.get("eMail").getAsString();
		String password = jobj.get("password").getAsString();

		String ret = df.createEmployee(fname, lname, address, possition, eMail,
				password);

		jo.addProperty("head", "newEmployee");
		if (ret.equals("")) {
			jo.addProperty("reg", "Registration Completed");
		} else {
			jo.addProperty("msg", ret);
		}
		String jsonResp = gson.toJson(jo);
		logger.info(jsonResp);

		return jsonResp;
	}

	private static String employeeInfo(DirectorFunctions df, JsonObject jobj) {
		//
		JsonObject jo = new JsonObject();
		List<Employee> emps = new ArrayList<Employee>();
		int id = -1; // id from form or other data from form
		String jsonEmps = null;
		try {
			id = jobj.get("id").getAsInt();
			logger.info(" int id = {} ", id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("incorect id passage from jsomObj");
			// jo.addProperty("head", "error");
			// jo.addProperty("msg", "Employee Id Not Provided");
		}

		if (id == -1) {
			String fname = jobj.get("fname").getAsString();
			String lname = jobj.get("lname").getAsString();
			String address = jobj.get("address").getAsString();
			String possition = jobj.get("possition").getAsString();
			String eMail = jobj.get("eMail").getAsString();
			String password = jobj.get("password").getAsString();

			emps = df.getEmployees(fname, lname, address, possition, eMail,
					password);
			if (emps.size() == 0) {
				jo.addProperty("head", "empList");
				jo.addProperty("msg", "No Employees With These Criterias");
			} else {
				jo.addProperty("head", "empList");
				jo.add("employees", gson.toJsonTree(emps));
			}
		} else {
			emps.add(df.getEmplyee(id));
			if (emps.get(0) == null) {
				jo.addProperty("head", "error");
				jo.addProperty("msg", "There Is No Emplyee With This Id");
			} else {
				jo.addProperty("head", "empList");
				jo.add("employees", gson.toJsonTree(emps));
			}
		}

		jsonEmps = gson.toJson(jo);
		logger.info("JSON FORM jsonEmps ={}", jsonEmps);
		return jsonEmps;

	}

	private static String getBalance(DirectorFunctions df, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		jo.addProperty("head", "dirBalance");
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);

		Date fromDate = null, toDate = null;
		try {

			java.util.Date d1 = format.parse(jobj.get("fdate").getAsString());
			fromDate = new java.sql.Date(d1.getTime());

			if (jobj.has("tdate")) {
				java.util.Date d2 = format.parse(jobj.get("tdate")
						.getAsString());
				toDate = new java.sql.Date(d2.getTime());
			}

		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "An Error Ocoured During Data Retreival");
			return gson.toJson(jo);
		}

		List<Object[]> list = df.getBalance(fromDate, toDate);
		jo.add("balist", gson.toJsonTree(list));
		String jsonResp = gson.toJson(jo);
		logger.info("jsonResp :" + jsonResp);
		return jsonResp;

	}

	private static String getTransactions(DirectorFunctions df, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		jo.addProperty("head", "dirTransaction");
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
		Date fromDate = null, toDate = null;
		try {
			java.util.Date d1 = format.parse(jobj.get("fdate").getAsString());
			fromDate = new java.sql.Date(d1.getTime());
			if (jobj.has("tdate")) {
				java.util.Date d2 = format.parse(jobj.get("tdate")
						.getAsString());
				toDate = new java.sql.Date(d2.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "An Error Ocoured During Data Retreival");
			return gson.toJson(jo);
		}

		List<Transaction> trl = df.getTransactions(fromDate, toDate);
		jo.addProperty("head", "dirTransaction");

		jo.add("translist", gson.toJsonTree(trl));
		String jsonResp = gson.toJson(jo);
		logger.info("--------------- >>>>>>>>>>" + jsonResp);
		return jsonResp;
	}

	public String coordRegister(int empId, Session ses) {
		logger.info("in coordRegister EMP id is--- {}", empId);
		// Coordinator co = new Coordinator();
		df = Coordinator.getDirectorFunc(empId);
		if (df == null) {
			return "The Manager Is Not Registerd";
		}
		df.setWsSession(ses);
		return null;
	}

}
