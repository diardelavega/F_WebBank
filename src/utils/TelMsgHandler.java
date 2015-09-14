package utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.Coordinator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.StaticVars;
import entity.Account;
import entity.Customers;
import functions.TellerFunctions;

public class TelMsgHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(TelMsgHandler.class);

	// private Gson gson = new GsonBuilder().serializeNulls().create();
	// ---------client to server side
	private TellerFunctions tf = new TellerFunctions();

	public String switchit(JsonObject jobj, String head) throws ParseException {
		logger.info("SWITCHING IT");
		// JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		// String head = jobj.get("head").getAsString();

		// JsonObject jo = new JsonObject();

		switch (head) {
		case "newClientReg":
			return newClientReg(jobj);
		case "alterClient":
			return alterClient(jobj);
		case "deleteClient":
			return deleteClient(jobj);
		case "accountStatus":
			return accountStatus(jobj);
		case "accountCoowners":
			return accountCoowners(jobj);
		case "clientAccounts":
			return clientAccounts(jobj);
		case "search":
			return clientSearch(jobj);
		case "deposite":
			return deposite(jobj);
		case "withdraw":
			return withdraw(jobj);
		case "transfer":
			return transfer(jobj);
		case "closeAccountRequest":
			return closeAccountReq(jobj);
		case "openAccountRequest":
			return openAccountReq(jobj);
		case "closeAccountDefinite":
			return closeAccountDef(jobj);
		case "openAccountDefinite":
			return openAccountDef(jobj);

		default:
			logger.info("invalid switch criterias");
		}
		return null;
	}

	/**
	 * After the manager confirms the closure, is the teller that executes the
	 * final action. Open account definitive and close account definitive.
	 * Maybe the closure should be done automatically in case of approval
	 * */
	private String openAccountDef(JsonObject jobj) {
		// TODO Auto-generated method stub
		return null;
	}

	private String closeAccountDef(JsonObject jobj) {
		// TODO Auto-generated method stub
		return null;
	}

	private String openAccountReq(JsonObject jobj) {

		ArrayList<String> pidList = new ArrayList<String>();
		if (jobj.get("pId1").getAsString().length() != 0) {
			pidList.add(jobj.get("pId1").getAsString());
		}
		if (jobj.get("pId2").getAsString().length() != 0) {
			pidList.add(jobj.get("pId2").getAsString());
		}
		if (jobj.get("pId3").getAsString().length() != 0) {
			pidList.add(jobj.get("pId3").getAsString());
		}
		if (jobj.get("pId4").getAsString().length() != 0) {
			pidList.add(jobj.get("pId4").getAsString());
		}
		char accType = jobj.get("accType").getAsCharacter();
		logger.info("OPENING Account with {}, {}, {}, {}, {}", pidList.get(0),
				accType);
		List<String> problemIds = tf.openAccountReq(pidList, accType);

		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "requestReply");
		if (problemIds != null) {
			jo.add("problematicId", gson.toJsonTree(problemIds));
		} else {
			jo.addProperty("msg", "Request Was Filed For Confirmation");
		}
		return gson.toJson(jo);
	}

	private String closeAccountReq(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "requestReply");

		String accNr = jobj.get("accNr").getAsString();
		String resp = tf.accCloseAccCheck(accNr);
		if (resp != null) {
			jo.addProperty("msg", resp);
			return gson.toJson(jo);
		}
		ArrayList<String> pidList = new ArrayList<String>();
		if (jobj.get("pId1").getAsString().length() != 0) {
			pidList.add(jobj.get("pId1").getAsString());
		}
		if (jobj.get("pId2").getAsString().length() != 0) {
			pidList.add(jobj.get("pId2").getAsString());
		}
		if (jobj.get("pId3").getAsString().length() != 0) {
			pidList.add(jobj.get("pId3").getAsString());
		}
		if (jobj.get("pId4").getAsString().length() != 0) {
			pidList.add(jobj.get("pId4").getAsString());
		}
		List<String> probList = tf.closeAccountReq(pidList, accNr);
		if (probList != null) {
			jo.add("problematicId", gson.toJsonTree(probList));
		}
		jo.addProperty("msg", "Request Was Filed For Confirmation");
		return gson.toJson(jo);
	}

	private String transfer(JsonObject jobj) {
		String accountFrom = jobj.get("accFrom").getAsString();
		String accountTo = jobj.get("accTo").getAsString();
		double amount = jobj.get("amount").getAsDouble();
		String persId = jobj.get("persId").getAsString();

		String resp = tf.transfer(persId, accountFrom, accountTo, amount);
		if (resp != null) {
			JsonObject jo = new JsonObject();
			Gson gson = new GsonBuilder().serializeNulls().create();
			jo.addProperty("head", "transferReply");
			jo.addProperty("msg", resp);
			return (gson.toJson(jo));
		}
		return null;
	}

	private String withdraw(JsonObject jobj) {
		String account = jobj.get("accNr").getAsString();
		double amount = jobj.get("amount").getAsDouble();
		String persId = jobj.get("persId").getAsString();

		String resp = tf.withdraw(persId, account, amount);
		if (resp != null) {
			JsonObject jo = new JsonObject();
			Gson gson = new GsonBuilder().serializeNulls().create();
			jo.addProperty("head", "withdrawReply");
			jo.addProperty("msg", resp);
			return (gson.toJson(jo));
		}
		return null;
	}

	private String deposite(JsonObject jobj) {
		String account = jobj.get("accNr").getAsString();
		double amount = jobj.get("amount").getAsDouble();
		String note = jobj.get("note").getAsString();

		String resp = tf.deposite(account, amount, note);
		if (resp != null) {
			JsonObject jo = new JsonObject();
			Gson gson = new GsonBuilder().serializeNulls().create();
			jo.addProperty("head", "depositeReply");
			jo.addProperty("msg", resp);
			return (gson.toJson(jo));
		}
		return null;
	}

	private String deleteClient(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		String persId = jobj.get("id").getAsString();
		String report = tf.deleteCustomer(persId);
		if (report == StaticVars.UNREG_USR) {
			jo.addProperty("head", "error");
			jo.addProperty("msg",
					"could not findclient, deletion was not completed");
		} else {
			jo.addProperty("head", "deleteClientReply");
			jo.addProperty("response", report);
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String alterClient(JsonObject jobj) {
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
						"Some error ocoured and the alteration was not completed");
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

	private String newClientReg(JsonObject jobj) throws ParseException {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		String persId = jobj.get("id").getAsString();
		String fname = jobj.get("fname").getAsString();
		String lname = jobj.get("lname").getAsString();
		String address = jobj.get("address").getAsString();
		String phone = jobj.get("phone").getAsString();
		String eMail = jobj.get("eMail").getAsString();
		String password = jobj.get("password").getAsString();
		String bdate = jobj.get("bdate").getAsString();

		String report = tf.register(persId, fname, lname, eMail, bdate,
				address, phone, password);
		if (report == null) {
			jo.addProperty("head", "error");
			jo.addProperty("msg",
					"Some error ocoured and the registration was not completed");
		} else {
			if (report.equals("")) {
				jo.addProperty("head", "registerClientReply");
				jo.addProperty("response", "registration completed");
			} else {
				jo.addProperty("head", "registerClientReply");
				jo.addProperty("response", report);
			}
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String clientAccounts(JsonObject jobj) {
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

	private String accountStatus(JsonObject jobj) {
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

	private String accountCoowners(JsonObject jobj) {
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

	private String clientSearch(JsonObject jobj) {
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

	public void coordRegister(int empId, Session ses) {
		/*
		 * get the tellerFunctions from the Coordinator list store web sockets
		 * sessionId and hold it on stand by
		 */

		logger.info("in coord EMP id is--- []", empId);
		// Coordinator co = new Coordinator();
		tf = Coordinator.getTellerFunc(empId);
		tf.setWsSession(ses);
	}

	public TellerFunctions getTf() {
		return tf;
	}

	public void setTf(TellerFunctions tf) {
		this.tf = tf;
	}

}
