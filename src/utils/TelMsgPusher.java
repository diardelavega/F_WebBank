package utils;

import java.io.IOException;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cod.server.ws.TellerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;
import comon.StaticVars;
import functions.TellerFunctions;

public class TelMsgPusher {
	Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
	private TellerWS tws = new TellerWS();

	public void returnResponse(OCRequest req, Session ses) throws IOException {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		logger.info("on tel pusher");

		switch (req.getReqType()) {
		case StaticVars.OPEN:
		case StaticVars.CLOSE:
		case StaticVars.PLUS_6_ACC:
			jo.addProperty("head", "ocRequestReply");
			break;

		case StaticVars.PLUS_1K_DEP:
		case StaticVars.PLUS_1K_TRANS:
		case StaticVars.PLUS_1K_WITH:
			jo.addProperty("head", "transRequestReply");
			break;
		default:
			logger.info("I dont know how we got here");
		}

		// jo.addProperty("head", "requestReply");
		jo.add("replyData", gson.toJsonTree(req));
		System.out.println(gson.toJson(jo));
		tws.sendMsg(gson.toJson(jo), ses);

		// TODO Auto-generated method stub

	}

}
