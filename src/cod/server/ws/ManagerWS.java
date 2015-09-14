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

import utils.ManMsgHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();
		
		ManMsgHandler mmh = new ManMsgHandler();
		String webResponse = null ;
		
		
		logger.info("HEAD  is {}", head);
		if (head.equalsIgnoreCase("coordinate")) {
			int empId = 0;
			try {
				empId = jobj.get("empId").getAsInt();
				logger.info("EmpId  is {}", empId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mmh.coordRegister(empId, ses);
		} else {
			webResponse = mmh.switchit(jobj,head);// .switchit(msg, jobj, head);
			if (webResponse != null)
				sendMsg(webResponse, ses);
		}
		

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
