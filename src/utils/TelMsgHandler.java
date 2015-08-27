package utils;

import java.util.ArrayList;
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
			.getLogger(TelMsgHandler.class);
//	private Gson gson = new GsonBuilder().serializeNulls().create();

	public String switchit(String msg) {
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();
		TellerFunctions tf = new TellerFunctions();
		// JsonObject jo = new JsonObject();

		switch (head) {
		case "accountStatus":
			return accountStatus(tf, jobj);
			// break;
		case "accountCoowners":
			return accountCoowners(tf, jobj);
			// break;
		case "clientAccounts":
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
//			logger.info("------------:ON ACCOUNT STATUS");
//			acc.print();
			
			jo.addProperty("head", "accountStatusReply");
			if (acc == null) {
				jo.addProperty("msg", "the account doesn't exist");
			} else {
				jo.add("Account", gson.toJsonTree(acc));
//				jo.addProperty("ac1",acc.getAccountId().toString());
//				jo.addProperty("ac2",acc.getAccStatus().toString());
//				jo.addProperty("ac3",acc.getAccType());
//				jo.addProperty("ac4",acc.getBalance());
//				jo.add("acc5", gson.toJsonTree(acc.getOpenDate()));
				
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

}
