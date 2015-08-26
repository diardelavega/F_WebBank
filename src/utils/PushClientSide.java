package utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import cod.server.ws.DirectorWS;
import entity.Transaction;

/**
 * @author Administrator
 *
 *         in this class should be implemented methods that push information to
 *         the necessary client side pages
 */
public class PushClientSide {

	DirectorWS dws = new DirectorWS();

	public String pushToClient(String requestType, String response,
			String accNr, String note) {
		return note;
	}
	
	public void pushTransToDir(Transaction t){
	 Gson gson = new GsonBuilder().serializeNulls().create();
	 JsonObject jo = new JsonObject();
//		List<Object[]> list = df.getBalance(fromDate, toDate);

		jo.addProperty("head", "dirTransaction");
		jo.add("newTrans", gson.toJsonTree(t));
		String jsonResp = gson.toJson(jo);
		
//		dws.sendMsg(msg, ses);
	}

}
