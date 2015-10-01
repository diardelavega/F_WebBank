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

import utils.CliMsgHandler;
import utils.DirMsgWsHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@ServerEndpoint("/cli")
public class ClientWS {
	
	Logger logger = LoggerFactory.getLogger(ManagerWS.class);

	@OnOpen
	public void open(Session ses) {
		System.out.println("ses opened");
		// TODO add web socket session to a new array
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
//		System.out.println("received msg seas : " + msg);
//		String webResponse = DirMsgWsHandler.switchit(msg);
//
//		sendMsg(webResponse, ses);

		logger.info("ON Client :received msg seas : " + msg);
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();

		CliMsgHandler cmh = new CliMsgHandler();
		logger.info("HEAD  is {}", head);
		if (head.equalsIgnoreCase("coordinate")) {
			int persId = 0;
			try {
				persId = jobj.get("PersId").getAsInt();
				logger.info("EmpId  is {}", persId);
				String res = cmh.coordRegister(persId, ses);
				if (res != null) {
					sendMsg(res, ses);
				}
			} catch (Exception e) {
				e.printStackTrace();
				sendMsg("Operation Failed ", ses);
			}

		} else {
			String webResponse = cmh.switchit(jobj, head);
			// if (webResponse != null)
			sendMsg(webResponse, ses);
		}

	}

	public void sendMsg(String msg, Session ses) throws IOException {
		System.out.println(msg);
		ses.getBasicRemote().sendText(msg);
	}

	@OnError
	public void error(Session ses, Throwable t) {
		System.out.println("ses with id :" + ses.getId()
				+ " encountered an error");
		t.printStackTrace();
	}
}