package cod.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import utils.DirMsgWsHandler;

public class Demo {

	public static void main(String[] args) {

		// List<String> ls = new ArrayList<String>();
		/*
		 * String[]aa=new String[3]; aa[0]="marko"; aa[1]="marko1";
		 * aa[2]="marko2"; List<String> ls2 = new ArrayList<String>();
		 */
		// ls.add("marko");
		// ls.add("marko2");
		// ls.add("marko2");

		// JsonObject jo = new JsonObject();
		// JsonObject jo = new JsonObject();
		// Gson gson = new Gson();

		Map<Integer, String> m1 = new HashMap<>();
		m1.put(1, "oone");
		m1.put(2, "two");
		m1.put(3, "three");

		m1.put(1, "ten");

		for (int i : m1.keySet()) {
			System.out.println(m1.get(i));
		}

	}

}
