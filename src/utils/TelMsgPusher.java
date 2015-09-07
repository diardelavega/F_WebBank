package utils;

import java.io.IOException;

import javax.websocket.Session;

import cod.server.ws.TellerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;
import comon.StaticVars;

public class TelMsgPusher {

	 private TellerWS tws =new TellerWS();

	public void returnResponse(OCRequest req, Session ses) throws IOException {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		jo.addProperty("head", "requestReply");
		jo.add("replyData", gson.toJsonTree(req));
		 tws.sendMsg(gson.toJson(jo), ses);
//		System.out.println(gson.toJson(jo));
		// TODO Auto-generated method stub

	}

}
