package utils;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cod.server.ws.ManagerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;
import comon.StaticVars;
import entity.Transaction;
import functions.DirectorFunctions;

public class ManMsgHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(DirMsgWsHandler.class);
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	JsonObject jo = new JsonObject();
	ManagerWS mws = new ManagerWS(); 

	public static String switchit(String msg) {
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();
		DirectorFunctions df = new DirectorFunctions();
		JsonObject jo = new JsonObject();

		switch (head) {
		case "open":
			return open(df, jobj);
			// break;
		case "close":
			return close(df, jobj);
			// // break;
		case "p_1k":
			return p_1k(df, jobj);
			// // break;
		case "p_6Acc":
			return p_6Acc(df, jobj);
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

	private static String p_6Acc(DirectorFunctions df, JsonObject jobj) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String p_1k(DirectorFunctions df, JsonObject jobj) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String close(DirectorFunctions df, JsonObject jobj) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String open(DirectorFunctions df, JsonObject jobj) {
		// TODO Auto-generated method stub
		return null;
	}

	public void newAlert() {
		jo.addProperty("head", "newRequest");
		String jsonResp = gson.toJson(jo);
//		mws.sendMsg(jsonResp, ses);
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

	public void transReplyClientPart(int i, List<Transaction> trList,
			Session wsSession) {
		jo.addProperty("head", "transRepCliPart");
		jo.addProperty("part", i);
		jo.add("trList", gson.toJsonTree(trList));

		String jsonResp = gson.toJson(jo);
		ManagerWS mws = new ManagerWS();
		try {
			mws.sendMsg(jsonResp, wsSession);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void transReplyClientAll(List<Transaction> trList, Session wsSession) {
		jo.addProperty("head", "transRepCliPart");
		jo.addProperty("part", "All");
		jo.add("trList", gson.toJsonTree(trList));

		String jsonResp = gson.toJson(jo);
		ManagerWS mws = new ManagerWS();
		try {
			mws.sendMsg(jsonResp, wsSession);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void transReplyAccount(List<Transaction> trList, Session wsSession) {
		jo.addProperty("head", "transRepAccPart");
		jo.add("trList", gson.toJsonTree(trList));
		String jsonResp = gson.toJson(jo);
		ManagerWS mws = new ManagerWS();
		try {
			mws.sendMsg(jsonResp, wsSession);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
