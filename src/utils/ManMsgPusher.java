package utils;

import java.io.IOException;

import javax.websocket.Session;

import cod.server.ws.ManagerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;

public class ManMsgPusher {

	public void requestNotifyer(OCRequest req,Session ses) throws IOException{
		ManagerWS mws = new ManagerWS ();
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "requestAlert");
		jo.add("requestData", gson.toJsonTree(req));
		mws.sendMsg(gson.toJson(jo), ses);
	}
	
}
