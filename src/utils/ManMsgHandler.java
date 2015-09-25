package utils;

import java.io.IOException;
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
	private ManagerFunctions mf;

	public String switchit(JsonObject jobj, String head) {
		logger.info(jobj.toString());

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
		case "approve":
			return approveRequest(jobj);
		case "dennie":
			return dennieRequest(jobj);
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
		case "logout":
			return logout(jobj);
		default:
			logger.info("invalid switch criterias");
		}
		return null;
	}

	private String logout(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "logoutReplay");
		jo.addProperty("response", "OK!");
		try {
			Coordinator.deleteManager(jobj.get("empId").getAsInt());
		} catch (Exception e) {
			e.printStackTrace();
			jo.addProperty("response", "Something Whent Wrong");
		}
		return gson.toJson(jo);
	}

	private String dennieRequest(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "dennieRequestReply");
		jo.addProperty("msg", "OK!");

		String note = jobj.get("note").getAsString();
		try {
			mf.decision("DENNIED", note);
		} catch (IOException e) {
			logger.warn("Some Error While Trying to Dennie The Request");
			jo.addProperty("msg", "A Problem Occurred");
			// e.printStackTrace();
		}
		return gson.toJson(jo);
	}

	private String approveRequest(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "approveRequestReply");
		jo.addProperty("msg", "OK!");

		String note = jobj.get("note").getAsString();
		try {
			mf.decision("ACCEPTED", note);
		} catch (IOException e) {
			logger.warn("Some Error While Trying to Approve The Request");
			jo.addProperty("msg", "A Problem Occurred");
			// e.printStackTrace();
		}
		return gson.toJson(jo);
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
			jo.addProperty("msg", "No Clients Where Found");
		} else {
			List<Customers> ret = mf.getAccountClients(jobj.get("accountNr")
					.getAsString());
			if (ret == null) {
				jo.addProperty("msg", "No Clients Where Found");
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
			jo.addProperty("msg", "No Accounts Where Found");
		} else {
			List<Account> ret = mf.getClientAccounts(persId);
			if (ret == null) {
				jo.addProperty("msg", "No Accounts Where Found");
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
			jo.addProperty("msg", "No Transactions Involve This Clients");
		} else {
			List<String> ls = new ArrayList<String>();
			ls.add(persId);
			List<Transaction> ret = mf.clientInvolvedTransactionsAll(ls);
			if (ret == null) {
				jo.addProperty("msg", "No Transaction Involve This Clients");
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
			jo.addProperty("msg", "No Transactions nvolve This Account");
		} else {
			List<Transaction> ret = mf.accountInvolvedTransactions(accNr);
			if (ret == null) {
				jo.addProperty("msg", "No TransactionsInvolve This Account");
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
			mf.leaveOCR();
		} catch (Exception e) {
			// e.printStackTrace();
			logger.warn("Some Error while trying to leave OCR");
			jo.addProperty("msg",
					"No Request Was Found For This Manager At This Time");
			return gson.toJson(jo);
		}
		jo.addProperty("msg", "Request Is Out");
		return gson.toJson(jo);
	}

	private String getRequest(JsonObject jobj) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "requestRequestReply");

		String resp = mf.getOCR();
		logger.info("RRRRRRRResp " + resp);
		if (resp == "") {
			jo.add("requestDetails", gson.toJsonTree(mf.getReq()));
		} else {
			jo.addProperty("msg", "No New Request Available--");
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
