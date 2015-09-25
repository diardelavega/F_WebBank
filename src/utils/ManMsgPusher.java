package utils;

import java.io.IOException;

import javax.websocket.Session;

import cod.server.ws.ManagerWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import comon.OCRequest;

public class ManMsgPusher {

	public void requestNotifyer(Session ses, String reqType, String d2,
			String ed) {
		ManagerWS mws = new ManagerWS();
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "requestAlert");
		jo.addProperty("msg", "New Request Arrived");
		jo.addProperty("reqType", reqType);
		jo.addProperty("d2", d2);
		jo.addProperty("ed", ed);
		try {
			mws.sendMsg(gson.toJson(jo), ses);
		} catch (IOException e) {
			System.out.println("in ManMsgPusher  with errors");
			e.printStackTrace();
		}
	}

	public void reqNrNotifyer(Session ses, int nr) {
		ManagerWS mws = new ManagerWS();
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jo.addProperty("head", "requestNumber");
		jo.addProperty("number", nr);
		try {
			mws.sendMsg(gson.toJson(jo), ses);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
