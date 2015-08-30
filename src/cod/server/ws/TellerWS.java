package cod.server.ws;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import utils.DirMsgWsHandler;
import utils.TelMsgHandler;

@ServerEndpoint("/tel")
public class TellerWS {

	Logger logger = LoggerFactory.getLogger(ManagerWS.class);

	@OnOpen
	public void open(Session ses) {
		System.out.println("ON TELLER :ses opened");
		// TODO add web socket session to a new array
		// sessions.add(ses);
	}

	@OnClose
	public void close(Session ses) {
		System.out.println("ON TELLER :ses with id :" + ses.getId()
				+ " is closed");
		// sessions.remove(ses);
	}

	@OnMessage
	public void recMsg(String msg, Session ses) throws IOException {
		System.out.println("ON TELLER :received msg seas : " + msg);
		
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();
		TelMsgHandler tmh = new TelMsgHandler();
		String webResponse;
		
		if (head.equalsIgnoreCase("register")) {
			int empId = jobj.get("empId").getAsInt();
			webResponse = tmh.register(msg, empId, ses);
		} else {
			webResponse = tmh.switchit(msg, jobj, head);
		}

		sendMsg(webResponse, ses);
	}

	public void sendMsg(String msg, Session ses) throws IOException {
		System.out.println(msg);
		ses.getBasicRemote().sendText(msg);
	}

	@OnError
	public void error(Session ses, Throwable t) {
		System.out.println("ON TELLER :ses with id :" + ses.getId()
				+ " encountered an error");
		t.printStackTrace();
	}
}
