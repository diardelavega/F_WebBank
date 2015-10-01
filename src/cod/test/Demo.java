package cod.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import utils.DirMsgWsHandler;

public class Demo {

	public static void main(String[] args) {

//		List<String> ls = new ArrayList<String>();
		String[]aa=new String[3];
		aa[0]="marko";
		aa[1]="marko1";
		aa[2]="marko2";
		List<String> ls2 = new ArrayList<String>();
//		ls.add("marko");
//		ls.add("marko2");
//		ls.add("marko2");

		JsonObject jo = new JsonObject();
		// JsonObject jo = new JsonObject();
		Gson gson = new Gson();
		jo.addProperty("head", "test");
		jo.add("namList", gson.toJsonTree(aa));

		System.out.println(jo.toString());

		Type listType = new TypeToken<List<String>>() {
		}.getType();
		ls2 = gson.fromJson(jo.get("namList"), listType);

		for (int i = 0; i < ls2.size(); i++) {
			System.out.println(ls2.get(i));
		}

	}

}
