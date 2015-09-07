package utils;

import java.io.IOException;

import javax.websocket.Session;

import cod.server.ws.ManagerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;

public class ManMsgPusher {

	public void requestNotifyer(Session ses) {
		ManagerWS mws = new ManagerWS ();
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "requestAlert");
		jo.addProperty("msg", "New Request Arrived"); 
		try {
			mws.sendMsg(gson.toJson(jo), ses);
		} catch (IOException e) {
			System.out.println("in ManMsgPusher  with errors");
			e.printStackTrace();
		}
	}
	
}
