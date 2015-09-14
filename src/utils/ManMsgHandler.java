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

public class ManMsgHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(ManMsgHandler.class);
	// private static Gson gson = new GsonBuilder().create();

	ManagerWS mws = new ManagerWS();
	private ManagerFunctions mf;

	public String switchit(JsonObject jobj, String head) {
		// Gson gson = new GsonBuilder().create();
		// JsonObject jobj = gson.fromJson(msg, JsonObject.class);
		// String head = jobj.get("head").getAsString();
		// DirectorFunctions df = new DirectorFunctions();
		// JsonObject jo = new JsonObject();

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
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		Account ret = mf.getAccountStatus(jobj.get("accountNr").getAsString());
		Gson gson = null;
		JsonObject jo = new JsonObject();
		jo.addProperty("head", "accountStatusReply");
		if (ret == null) {
			jo.addProperty("msg", "Account not found");
		} else {
			gson = new GsonBuilder().serializeNulls().create();
			jo.add("account", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String accountCoowners(JsonObject jobj) {
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		JsonObject jo = new JsonObject();
		List<Customers> ret = mf.getAccountClients(jobj.get("accountNr")
				.getAsString());
		Gson gson = null;
		jo.addProperty("head", "accountOwnersReply");
		if (ret == null) {
			jo.addProperty("msg", "No Clients Where Found For This Account");
		} else {
			gson = new GsonBuilder().serializeNulls().create();
			jo.add("ownersList", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String clientAccounts(JsonObject jobj) {
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		List<Account> ret = mf.getClientAccounts(jobj.get("personalId")
				.getAsString());
		JsonObject jo = new JsonObject();
		Gson gson = null;
		jo.addProperty("head", "clientAccountsReply");
		if (ret == null) {
			jo.addProperty("msg", "No Accounts Where Found For This Clients");
		} else {
			gson = new GsonBuilder().serializeNulls().create();
			jo.add("accountsList", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String clientTransactions(JsonObject jobj) {
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		List<String> ls = new ArrayList<String>();
		ls.add(jobj.get("personalId").getAsString());
		List<Transaction> ret = mf.clientInvolvedTransactionsAll(ls);
		JsonObject jo = new JsonObject();
		Gson gson = null;
		jo.addProperty("head", "clientTransactionReply");
		if (ret == null) {
			jo.addProperty("msg",
					"No Transactions Where Found Involving This Clients");
		} else {
			gson = new GsonBuilder().serializeNulls().create();
			jo.add("clientTransList", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String manyClientTransactions(JsonObject jobj) {
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		List<String> ls = new ArrayList<String>();
		// TODO for all client id find transactions
		ls.add(jobj.get("personalId").getAsString());
		List<Transaction> ret = mf.clientInvolvedTransactionsAll(ls);
		JsonObject jo = new JsonObject();
		Gson gson = null;
		jo.addProperty("head", "manyClientTransactionReply");
		if (ret == null) {
			jo.addProperty("msg",
					"No Transactions Where Found Involving This Clients");
		} else {
			gson = new GsonBuilder().serializeNulls().create();
			jo.add("ownersList", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String accountTransactions(JsonObject jobj) {
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		List<Transaction> ret = mf.accountInvolvedTransactions(jobj.get(
				"accountNr").getAsString());
		JsonObject jo = new JsonObject();
		Gson gson = null;
		jo.addProperty("head", "accountTransactionsReply");
		if (ret == null) {
			jo.addProperty("msg",
					"No Transactions Where Found Involving This Account");
		} else {
			gson = new GsonBuilder().serializeNulls().create();
			jo.add("accountTransList", gson.toJsonTree(ret));
		}
		return gson.toJson(jo);
	}

	private String leaveRequest(JsonObject jobj) {
		// TODO Auto-generated method stub
		JsonObject jo = new JsonObject();
		int manEmpId = jobj.get("empId").getAsInt();
		mf = Coordinator.getManagerFunc(manEmpId);
		mf.getReq().unPin();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "leaveRequestReply");
		jo.addProperty("msg", "Done");

		return gson.toJson(jo);
	}

	private String getRequest(JsonObject jobj) {
		logger.info("in teller function get request");
		int manEmpId = 0;
		try {
			manEmpId = jobj.get("empId").getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "requestRequestReply");

		mf = Coordinator.getManagerFunc(manEmpId);
		if (mf == null) {
			logger.warn("Null functioner, EmployeeId not provided");
		}
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

	public void coordRegister(int empId, Session ses) {
		/*
		 * get the tellerFunctions from the Coordinator list store web sockets
		 * sessionId and hold it on stand by
		 */

		logger.info("in coord EMP id is--- []", empId);
		// Coordinator co = new Coordinator();
		mf = Coordinator.getManagerFunc(empId);
		mf.setWsSession(ses);
	}

}
