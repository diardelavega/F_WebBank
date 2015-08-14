package cod.server.ws;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import utils.DirMsgWsHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@ServerEndpoint("/dir")
public class DirectorWS {

	@OnOpen
	public void open(Session ses) {
		System.out.println("ses opened");
		// sessions.add(ses);
	}

	@OnClose
	public void close(Session ses) {
		System.out.println("ses with id :" + ses.getId() + " is closed");
		// sessions.remove(ses);
	}

	@OnMessage
	public void recMsg(String msg, Session ses) throws IOException {
		// System.out.println("received msg from: " + ses.getId());
		System.out.println("received msg seas : " + msg);
		String webResponse = DirMsgWsHandler.switchit(msg);

		sendMsg(webResponse, ses);
	}

	public void sendMsg(String msg, Session ses) throws IOException {
		ses.getBasicRemote().sendText(msg);
	}

	@OnError
	public void error(Session ses, Throwable t) {
		System.out.println("ses with id :" + ses.getId()
				+ " encountered an error");
		t.printStackTrace();
	}
}
