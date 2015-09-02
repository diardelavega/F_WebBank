package utils;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import entity.Account;
import entity.Customers;
import functions.TellerFunctions;

public class TelMsgHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(TelMsgHandler.class);

	// private Gson gson = new GsonBuilder().serializeNulls().create();
	// ---------client to server side
	public String switchit(String msg, JsonObject jobj, String head) {
		// JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		// String head = jobj.get("head").getAsString();
		TellerFunctions tf = new TellerFunctions();
		// JsonObject jo = new JsonObject();

		switch (head) {
		case "newClientReg":
			return newClientReg(tf, jobj);
		case "alterClient":
			return alterClient(tf, jobj);
		case "accountStatus":
			return accountStatus(tf, jobj);
		case "accountCoowners":
			return accountCoowners(tf, jobj);
		case "clientAccounts":
			return clientAccounts(tf, jobj);
		case "search":
			return clientSearch(tf, jobj);
		default:
			logger.info("invalid switch criterias");
		}
		return null;
	}

	private String alterClient(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		try {
			String persId = jobj.get("id").getAsString();
			String fname = jobj.get("fname").getAsString();
			String lname = jobj.get("lname").getAsString();
			String address = jobj.get("address").getAsString();
			String phone = jobj.get("phone").getAsString();
			String eMail = jobj.get("eMail").getAsString();
			String password = jobj.get("password").getAsString();
			String bdate = jobj.get("bdate").getAsString();

			String report = tf.alter(persId, fname, lname, eMail, bdate,
					address, phone, password);
			if (report == null) {
				jo.addProperty("head", "error");
				jo.addProperty("msg",
						"Some error ocoured and the registration was not completed");
			} else {
				if (report.equals("")) {
					jo.addProperty("head", "alterClientReply");
					jo.addProperty("response", "alteration completed");
				} else {
					jo.addProperty("head", "alterClientReply");
					jo.addProperty("response", report);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("head", "error");
			jo.addProperty("msg", "could not parse client data");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String newClientReg(TellerFunctions tf, JsonObject jobj) {

//		String persId = jobj.get("id").getAsString();
		String fname = jobj.get("fname").getAsString();
		String lname = jobj.get("lname").getAsString();
		String address = jobj.get("address").getAsString();
		String phone = jobj.get("phone").getAsString();
		String eMail = jobj.get("eMail").getAsString();
		String password = jobj.get("password").getAsString();
		String bdate = jobj.get("bdate").getAsString();

		String report = tf.register(fname, lname, eMail, bdate, address, phone, password);
		
		return null;
	}

	private String clientAccounts(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (jobj.has("personalId")) {
			String personalId = jobj.get("personalId").getAsString();
			jo.addProperty("head", "clientAccountsReply");

			List<Account> ids = tf.getClientAccounts(personalId);
			if (ids == null || ids.size() == 0) {
				jo.addProperty("msg", "this customer has no accounts");
			} else {
				jo.add("accList", gson.toJsonTree(ids));
			}
		} else {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "personal Id was not provided");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String accountStatus(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (jobj.has("accuntNr")) {
			String accId = jobj.get("accuntNr").getAsString();
			Account acc = tf.getAccount(accId);

			jo.addProperty("head", "accountStatusReply");
			if (acc == null) {
				jo.addProperty("msg", "the account doesn't exist");
			} else {
				jo.add("Account", gson.toJsonTree(acc));
			}
		} else {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "the accuntNr dwas not provided");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String accountCoowners(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (jobj.has("accuntNr")) {
			String accountId = jobj.get("accuntNr").getAsString();
			List<Object> coowners = tf.getAccountClients(accountId);
			if (coowners == null || coowners.size() == 0) {
				jo.addProperty("head", "accountCoownersReply");
				jo.addProperty("msg", "the Account has no owners");
			} else {
				jo.addProperty("head", "accountCoownersReply");
				jo.add("ownList", gson.toJsonTree(coowners));
			}
		} else {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "the account nr was not provided");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	public String coordRegister(String msg, int empId, Session ses) {
		// TODO search in Coordinator tellers list and match the empId
		// store web sockets sessionId and hold it on stand by
		return null;
	}

	private String clientSearch(TellerFunctions tf, JsonObject jobj) {
		// get the client search criteria and return a list of customers
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		List<Customers> customers = new ArrayList<>();
		String jsonEmps;
		String persId = null;
		try {
			persId = jobj.get("id").getAsString();
			if (!persId.equals("")) {
				customers.add(tf.getCustomer(persId));
				logger.info("----------> jobj id = {} ", jobj.get("id"));
			} else {
				logger.info("into variable search");
				logger.info("DATA: fname={}  lname={},  email={}",
						jobj.get("fname").getAsString(), jobj.get("lname")
								.getAsString(), jobj.get("eMail").getAsString());

				String fname = jobj.get("fname").getAsString();
				String lname = jobj.get("lname").getAsString();
				String address = jobj.get("address").getAsString();
				String phone = jobj.get("phone").getAsString();
				String eMail = jobj.get("eMail").getAsString();
				String password = jobj.get("password").getAsString();

				customers = tf.getClients(fname, lname, address, phone, eMail,
						password);
			}

		} catch (Exception e) {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "client personal id not provided");
			jsonEmps = gson.toJson(jo);
			logger.info("JSON FORM jsonEmps ={}", jsonEmps);
			return jsonEmps;
		}

		jo.addProperty("head", "searchReply");
		if (customers.size() > 0) {
			jo.add("customerList", gson.toJsonTree(customers));
		} else {
			jo.addProperty("msg", "no results from the search");
		}
		jsonEmps = gson.toJson(jo);
		logger.info("JSON FORM jsonEmps ={}", jsonEmps);
		return jsonEmps;

	}
}
