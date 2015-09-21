package utils;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import system.Coordinator;
import cod.server.ws.ManagerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;
import comon.StaticVars;
import entity.Account;
import entity.Customers;
import entity.Transaction;
import functions.ManagerFunctions;
import functions.TellerFunctions;

public class ManMsgHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(ManMsgHandler.class);
	// private static Gson gson = new GsonBuilder().create();

	// ManagerWS mws = new ManagerWS();
	private ManagerFunctions mf;

	public String switchit(JsonObject jobj, String head) {
		logger.info(jobj.toString());
		logger.info(head);

		int empId;
		try {
			empId = Integer.parseInt(jobj.get("empId").getAsString());
			mf = Coordinator.getManagerFunc(empId);
		} catch (Exception e) {
			logger.warn("EMP ID WAS NOt FOUND");
			mf = new ManagerFunctions();
			e.printStackTrace();
		}

		switch (head) {
		case "getRequest":
			return getRequest(jobj);
		case "leaveRequest":
			return leaveRequest(jobj);
		case "accountTransactions":
			return accountTransactions(jobj);
		case "clientTransactions":
			return clientTransactions(jobj);
		case "clientAccounts":
			return clientAccounts(jobj);
		case "accountCoowners":
			return accountCoowners(jobj);
		case "accountStatus":
			return accountStatus(jobj);
		default:
			logger.info("invalid switch criterias");
		}
		return null;
	}

	private String accountStatus(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "accountStatusReply");

		String accNr = jobj.get("accountNr").getAsString();
		if (accNr.equals("")) {
			jo.addProperty("msg", "Account not found");
		} else {
			Account ret = mf.getAccountStatus(accNr);
			if (ret == null) {
				jo.addProperty("msg", "Account not found");
			} else {
				jo.add("account", gson.toJsonTree(ret));
			}
		}
		logger.info(jo.toString());
		return gson.toJson(jo);
	}

	private String accountCoowners(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "accountOwnersReply");

		String persId = jobj.get("accountNr").getAsString();
		if (persId.equals("")) {
			jo.addProperty("msg", "No Clients Where Found For This Account");
		} else {
			List<Customers> ret = mf.getAccountClients(jobj.get("accountNr")
					.getAsString());
			if (ret == null) {
				jo.addProperty("msg", "No Clients Where Found For This Account");
			} else {
				jo.add("ownersList", gson.toJsonTree(ret));
			}
		}
		return gson.toJson(jo);
	}

	private String clientAccounts(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "clientAccountsReply");

		String persId = jobj.get("personalId").getAsString();
		if (persId.equals("")) {
			jo.addProperty("msg",
					"No Accounts Where Found Involving This Clients");
		} else {
			List<Account> ret = mf.getClientAccounts(persId);
			if (ret == null) {
				jo.addProperty("msg",
						"No Accounts Where Found For This Clients");
			} else {
				jo.add("accountsList", gson.toJsonTree(ret));
			}
		}
		return gson.toJson(jo);
	}

	private String clientTransactions(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "clientTransactionReply");

		String persId = jobj.get("personalId").getAsString();
		if (persId.equals("")) {
			jo.addProperty("msg",
					"No Transactions Where Found Involving This Clients");
		} else {
			List<String> ls = new ArrayList<String>();
			ls.add(persId);
			List<Transaction> ret = mf.clientInvolvedTransactionsAll(ls);
			if (ret == null) {
				jo.addProperty("msg",
						"No Transactions Where Found Involving This Clients");
			} else {
				jo.add("clientTransList", gson.toJsonTree(ret));
			}
		}
		return gson.toJson(jo);
	}

	private String manyClientTransactions(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "manyClientTransactionReply");

		List<String> ls = new ArrayList<String>();
		// for all client id find transactions
		ls.add(jobj.get("personalId").getAsString());
		List<Transaction> ret = mf.clientInvolvedTransactionsAll(ls);

		if (ret == null) {
			jo.addProperty("msg",
					"No Transactions Where Found Involving This Clients");
		} else {
			jo.add("ownersList", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String accountTransactions(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "accountTransactionsReply");

		String accNr = jobj.get("accountNr").getAsString();
		if (accNr.equals("")) {
			jo.addProperty("msg",
					"No Transactions Where Found Involving This Account");
		} else {
			List<Transaction> ret = mf.accountInvolvedTransactions(accNr);
			if (ret == null) {
				jo.addProperty("msg",
						"No Transactions Where Found Involving This Account");
			} else {
				jo.add("accountTransList", gson.toJsonTree(ret));
			}
		}
		return gson.toJson(jo);
	}

	private String leaveRequest(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "leaveRequestReply");
		try {
			mf.getReq().unPin();
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("msg", "No Request Was Found For This Manager");
			return gson.toJson(jo);
		}
		jo.addProperty("msg", "Done");
		return gson.toJson(jo);
	}

	private String getRequest(JsonObject jobj) {
		logger.info("in man msg handler function get request");
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "requestRequestReply");

		mf.getOCR();
		if (mf.getReq() == null) {
			jo.addProperty("msg", "No New Request Available");
		} else {
			jo.add("requestDetails", gson.toJsonTree(mf.getReq()));
		}
		logger.info(gson.toJson(jo));
		return gson.toJson(jo);
	}

	public void convertOCRToMsg(OCRequest req) {
		/* Not Necesary Untill It Is Needed */
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		if (req.getReqType() == StaticVars.OPEN) {
			jo.addProperty("head", "open");
			jo.add("clientList", gson.toJsonTree(req.getClientIdsList()));
		}
		if (req.getReqType() == StaticVars.CLOSE) {
			jo.addProperty("head", "close");
			jo.add("clientList", gson.toJsonTree(req.getClientIdsList()));
			jo.addProperty("accountNr", req.getAccFromNr());
		}
		if (req.getReqType() == StaticVars.PLUS_1K_DEP) {
			jo.addProperty("head", "p_1k_dep");
			jo.addProperty("accountNr", req.getAccFromNr());
			jo.addProperty("amount", req.getAmount());
			jo.addProperty("note", req.getNote());
		}
		if (req.getReqType() == StaticVars.PLUS_1K_WITH) {
			jo.addProperty("head", "p_1k_with");
			jo.add("clientList", gson.toJsonTree(req.getClientIdsList()));
			jo.addProperty("accountNr", req.getAccFromNr());
			jo.addProperty("amount", req.getAmount());
			jo.addProperty("note", req.getNote());
		}
		if (req.getReqType() == StaticVars.PLUS_1K_TRANS) {
			jo.addProperty("head", "p_1k_trans");
			jo.add("clientList", gson.toJsonTree(req.getClientIdsList()));
			jo.addProperty("accountNrFrom", req.getAccFromNr());
			jo.addProperty("accountNrTo", req.getAccToNr());
			jo.addProperty("amount", req.getAmount());
			jo.addProperty("note", req.getNote());
		}
		if (req.getReqType() == StaticVars.PLUS_6_ACC) {
			jo.addProperty("head", "p_6_acc");
			jo.add("clientList", gson.toJsonTree(req.getClientIdsList()));
			jo.addProperty("note", req.getNote());
		}
		String jsonResp = gson.toJson(jo);

	}

	public String coordRegister(int empId, Session ses) {
		/*
		 * get the managerFunctions from the Coordinator list store web sockets
		 * sessionId and hold it on stand by
		 */

		logger.info("in coordRegister EMP id is--- {}", empId);
		// Coordinator co = new Coordinator();
		mf = Coordinator.getManagerFunc(empId);
		if (mf == null) {
			return "The Manager Is Not Registerd";
		}
		mf.setWsSession(ses);
		return null;
	}

}
