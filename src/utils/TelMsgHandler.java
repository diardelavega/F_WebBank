package utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import entity.Account;
import functions.TellerFunctions;

public class TelMsgHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(DirMsgWsHandler.class);
	private Gson gson = new GsonBuilder().serializeNulls().create();

	public String switchit(String msg) {
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();
		TellerFunctions tf = new TellerFunctions();
//		JsonObject jo = new JsonObject();

		switch (head) {
		case "accStats":
			return accountStatus(tf, jobj);
			// break;
		case "accCoo":
			return accountCoowners(tf, jobj);
			// break;
		case "cliAcc":
			return clientAccounts(tf, jobj);
			// break;
			// case "delete":
			// return deleteEmployee(df, jobj);
			// // break;
			// case "balance":
			// return getBalance(df, jobj);
			// // break;
			// case "transaction":
			// return getTransactions(df, jobj);
			// // break;
			// case "empAct":
			// break;
		default:
			logger.info("invalid switch criterias");
		}

		return null;
	}

	private String clientAccounts(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		if (jobj.has("persId")) {
			String personalId = jobj.get("persId").getAsString();
			jo.addProperty("head", "clientAccs");
			List<String> ids = tf.getClientAccounts(personalId);
			jo.add("accList", gson.toJsonTree(ids));

		} else {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "personal Id was not provided");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String accountStatus(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		if (jobj.has("accStats")) {
			String accId = jobj.get("accStats").getAsString();
			Account acc = tf.getAccount(accId);
			if (acc == null) {
				jo.addProperty("head", "accStatusReply");
				jo.addProperty("msg", "the account doesn't exist");
			} else {
				jo.addProperty("head", "accStatusReply");
				jo.add("Account", gson.toJsonTree(acc));
			}
		} else {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "the account nr was not provided");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

	private String accountCoowners(TellerFunctions tf, JsonObject jobj) {
		JsonObject jo = new JsonObject();
		if (jobj.has("accCoo")) {
			String accountId = jobj.get("accCoo").getAsString();
			List<String> coowners = tf.getAccountClients(accountId);
			if (coowners == null || coowners.size() == 0) {
				jo.addProperty("head", "accCoownersReply");
				jo.addProperty("msg", "the Account has no owners");
			} else {
				jo.addProperty("head", "accCoownersReply");
				jo.add("Account", gson.toJsonTree(coowners));
			}
		} else {
			jo.addProperty("head", "error");
			jo.addProperty("msg", "the account nr was not provided");
		}
		String jsonResp = gson.toJson(jo);
		return jsonResp;
	}

}
