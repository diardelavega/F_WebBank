package utils;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.Coordinator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import entity.Account;
import entity.Transaction;
import functions.CustomerFunctions;
import functions.ManagerFunctions;

public class CliMsgHandler {
	private final Logger logger = LoggerFactory.getLogger(CliMsgHandler.class);

	private CustomerFunctions cf;

	public String switchit(JsonObject jobj, String head) {
		logger.info(jobj.toString());

		String persId;
		try {
			persId = jobj.get("persId").getAsString();
			cf = Coordinator.getCustomerFunctions(persId);
		} catch (Exception e) {
			logger.warn("EMP ID WAS NOt FOUND, WE OUT");
			e.printStackTrace();
		}

		switch (head) {
		case "transfer":
			return transfer(jobj);
		case "transactions":
			return getTransactions(jobj);
		case "balance":
			return getBalance(jobj);
		case "accounts":
			return getAccounts(jobj);
		case "logout":
			return logout(jobj);
		}

		return null;
	}

	private String logout(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "logoutReplay");

		String persId = jobj.get("persId").getAsString();
		try {
			cf = Coordinator.getCustomerFunctions(persId);
			// delete accounts of that customer from accounts on
			cf.emptyAllAccounts();
			Coordinator.deleteCustomer(persId);
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "Something Whent Wrong");
		}
		jo.addProperty("response", "OK");
		return gson.toJson(jo);
	}

	private String getAccounts(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "accountsReply");
		String persId = jobj.get("persId").getAsString();
		CustomerFunctions cf = new CustomerFunctions();
		List<Account> ret = cf.getCustomersAccounts(persId);

		if (ret == null) {
			jo.addProperty("msg", "No Accounts Belong To This Id");
		} else {
			jo.add("accounts", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String transfer(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "transferReply");

		String persId = jobj.get("persId").getAsString();
		String accFrom = jobj.get("accFrom").getAsString();
		String accTo = jobj.get("accTo").getAsString();
		double amount;
		try {
			amount = jobj.get("amount").getAsDouble();
		} catch (Exception e) {
			logger.info("Couldn't Parse Ammount");
			jo.addProperty("msg", "Invalid Amount Value");
			return gson.toJson(jo);
		}
		if (accFrom.equals("") || accTo.equals("")) {
			logger.info("Couldn't Parse Account");
			jo.addProperty("msg", "Invalid Account Id");
			return gson.toJson(jo);
		}

		CustomerFunctions cf = new CustomerFunctions();
		String ret = cf.transfer(persId, accFrom, accTo, amount);
		if (ret == null) {
			jo.addProperty("msg", "Error During Transfering");
		} else {
			jo.addProperty("tra", ret);
		}
		return gson.toJson(jo);
	}

	private String getBalance(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "balanceReply");

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
		Date fromDate = null, toDate = null;
		// define the range between dates
		try {
			String dat1 = jobj.get("fdate").getAsString();
			if (!dat1.equals("")) {
				java.util.Date d1 = format.parse(dat1);
				fromDate = new java.sql.Date(d1.getTime());
				if (jobj.has("tdate")) {
					java.util.Date d2 = format.parse(jobj.get("tdate")
							.getAsString());
					toDate = new java.sql.Date(d2.getTime());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "An Error Ocoured During Data Retreival");
			return gson.toJson(jo);
		}

		// get the list of account to search for
		Type listType = new TypeToken<List<String>>() {
		}.getType();
		List<String> sl = gson.fromJson(jobj.get("accList"), listType);

		List<Object[]> ret;
		if (sl == null || sl.size() == 0) {
			String searchPersId = jobj.get("searchId").getAsString();
			if (searchPersId == null) {
				jo.addProperty("msg", "No Search Criteria Specifyed");
				return gson.toJson(jo);
			} else {
				ret = cf.getBalance(searchPersId, fromDate, toDate);
			}
		} else {// if we have accounts
			ret = cf.getBalance(sl, fromDate, toDate);
		}
		if (ret == null) {
			jo.addProperty("msg", "Personal Id Unconsistent With Clients");
		} else if (ret.size() == 0) {
			jo.addProperty("msg", "No Transactions Where Found");
		} else {
			jo.add("balanceData", gson.toJsonTree(ret));
		}
		// TODO return to cliInit
		return gson.toJson(jo);
	}

	public String getTransactions(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "transactionReply");

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
		Date fromDate = null, toDate = null;
		try {
			String dat1 = jobj.get("fdate").getAsString();
			if (!dat1.equals("")) {
				java.util.Date d1 = format.parse(dat1);
				fromDate = new java.sql.Date(d1.getTime());
				if (jobj.has("tdate")) {
					java.util.Date d2 = format.parse(jobj.get("tdate")
							.getAsString());
					toDate = new java.sql.Date(d2.getTime());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "An Error Ocoured During Data Retreival");
			return gson.toJson(jo);
		}
		// ------------- parse array from json to java
		Type listType = new TypeToken<List<String>>() {
		}.getType();
		List<String> sl = gson.fromJson(jobj.get("accList"), listType);

		List<Transaction> ret;
		if (sl == null || sl.size() == 0) {
			String searchPersId = jobj.get("searchId").getAsString();
			if (searchPersId == null) {
				jo.addProperty("msg", "No Search Criteria Specifyed");
				return gson.toJson(jo);
			} else {
				ret = cf.getTransactions(searchPersId, fromDate, toDate);
			}
		} else {
			ret = cf.getTransactions(sl, fromDate, toDate);
		}
		if (ret == null) {
			jo.addProperty("msg", "Personal Id Unconsistent With Clients");
		} else if (ret.size() == 0) {
			jo.addProperty("msg", "No Transactions Where Found");
		} else {
			jo.add("transactionList", gson.toJsonTree(ret));
		}
		// TODO return to cliInit
		return gson.toJson(jo);
	}

	public String coordRegister(String persId, Session ses) {
		logger.info("in coordRegister EMP id is--- {}", persId);

		cf = Coordinator.getCustomerFunctions(persId);
		cf.setSession(ses);
		logger.info("cf = {}", cf);
		// get accounts, fill acclist, put accounts on coordinator accounts
		cf.getCustomersAccounts(persId);
		cf.fillAllAccounts();

		if (cf == null) {
			logger.info("The Client Is Not Registerdaaaaaaaaaaaaaaa");
			return "The Client Is Not Registerd";
		}
		logger.info("all should be good " + ses);

		return null;
	}
}
