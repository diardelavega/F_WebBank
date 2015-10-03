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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@ServerEndpoint("/cli")
public class ClientWS {

	Logger logger = LoggerFactory.getLogger(ClientWS.class);

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

		logger.info("ON Client :received msg seas : " + msg);
		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
		String head = jobj.get("head").getAsString();

		CliMsgHandler cmh = new CliMsgHandler();

		logger.info("HEAD  is {}", head);
		if (head.equalsIgnoreCase("coordinate")) {
			String persId;
			try {
				persId = jobj.get("persId").getAsString();
				logger.info("persId  is {}", persId);
				String res = cmh.coordRegister(persId, ses);
				logger.info("response is : {}",res);
				if (res != null) {
					sendMsg(res, ses);
				}
			} catch (Exception e) {
				logger.info("Pers Id not Provided");
				sendMsg("Operation Failed 3333 ", ses);
			}

		} else {
			String webResponse = cmh.switchit(jobj, head);
			logger.info("webResponse ={} ",webResponse);
			sendMsg(webResponse, ses);
		}
	}
	
//	public void recMsg(String msg, String ses) throws IOException {
//
//		logger.info("ON Client :received msg seas : " + msg);
//		JsonObject jobj = new Gson().fromJson(msg, JsonObject.class);
//		String head = jobj.get("head").getAsString();
//
//		CliMsgHandler cmh = new CliMsgHandler();
//
//		logger.info("HEAD  is {}", head);
//		if (head.equalsIgnoreCase("coordinate")) {
//			String persId;
//			try {
//				persId = jobj.get("persId").getAsString();
//				logger.info("persId  is {}", persId);
//				String res = cmh.coordRegister(persId, ses);
//				logger.info("response is : {}",res);
//				if (res != null) {
//					sendMsgo(res, ses);
//				}
//			} catch (Exception e) {
//				logger.info("Pers Id not Provided");
//				sendMsgo("Operation Failed 3333 ", ses);
//			}
//
//		} else {
//			String webResponse = cmh.switchit(jobj, head);
//			logger.info("webResponse ={} ",webResponse);
//			sendMsgo(webResponse, ses);
//		}
//	}
	

	private void sendMsgo(String res, String ses) {
		// TODO Auto-generated method stub
		
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