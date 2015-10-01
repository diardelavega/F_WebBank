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
			logger.warn("EMP ID WAS NOt FOUND");
			cf = new CustomerFunctions();
			e.printStackTrace();
		}

		switch (head) {
		case "transactions":
			return getTransactions(jobj);
		case "balance":
			return getBalance(jobj);
		}

		return null;
	}

	private String getBalance(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "balanceReply");
		
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
		
		
		Type listType = new TypeToken<List<String>>() {}.getType();
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
		} else {
			ret = cf.getBalance(sl, fromDate, toDate);
		}
		if(ret==null){
			jo.addProperty("msg", "Personal Id Unconsistent With Clients");
		}
		else if (ret.size() == 0) {
			jo.addProperty("msg", "No Transactions Where Found");
		} else {
			jo.add("transactionList", gson.toJsonTree(ret));
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
		if(ret==null){
			jo.addProperty("msg", "Personal Id Unconsistent With Clients");
		}
		else if (ret.size() == 0) {
			jo.addProperty("msg", "No Transactions Where Found");
		} else {
			jo.add("transactionList", gson.toJsonTree(ret));
		}
		// TODO return to cliInit
		return gson.toJson(jo);
	}

	public String coordRegister(int persId, Session ses) {
		// TODO Auto-generated method stub
		return null;
	}
}
