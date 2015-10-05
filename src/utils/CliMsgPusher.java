package utils;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import entity.Account;

public class CliMsgPusher {
	public static final Logger logger = LoggerFactory
			.getLogger(CliMsgPusher.class);

	public void pushAccounts(Session ses, List<Account> accl) {
		JsonObject jo = new JsonObject();
		Gson gson = new GsonBuilder().create();
		logger.info("on CliMsgPusher");
		
		jo.addProperty("head", "accountsPushed");
		jo.add("accs", gson.toJsonTree(accl));
		
		String ret = gson.toJson(jo);
		logger.info("display return on pusher {}",ret);
		try {
			if (ret != null)
				ses.getBasicRemote().sendText(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
