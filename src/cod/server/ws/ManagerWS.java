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

import utils.DirMsgWsHandler;
import utils.ManMsgHandler;

@ServerEndpoint("/man")
public class ManagerWS {
	Logger logger = LoggerFactory.getLogger(ManagerWS.class);

	@OnOpen
	public void open(Session ses) {
		System.out.println("ON MANAGER :ses opened");
		// TODO add web socket session to a new array
		// sessions.add(ses);
	}

	@OnClose
	public void close(Session ses) {
		System.out.println("ON MANAGER :ses with id :" + ses.getId()
				+ " is closed");
		// sessions.remove(ses);
	}

	@OnMessage
	public void recMsg(String msg, Session ses) throws IOException {
		// System.out.println("received msg from: " + ses.getId());
		System.out.println("ON MANAGER :received msg seas : " + msg);
		ManMsgHandler mmh = new ManMsgHandler();
		String webResponse = mmh.switchit(msg);

		sendMsg(webResponse, ses);
	}

	public void sendMsg(String msg, Session ses) throws IOException {
		System.out.println(msg);
		if (msg != null)
			ses.getBasicRemote().sendText(msg);
	}

	@OnError
	public void error(Session ses, Throwable t) {
		System.out.println("ON MANAGER :ses with id :" + ses.getId()
				+ " encountered an error");
		t.printStackTrace();
	}
}
