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

//		boolean f = true;
//		foo(-1);
	}

	public static void foo(boolean f) {
		if(f!=(Boolean)null){
		if (f) {
			System.out.println("YESSSSSSSS");
		}
		if (!f) {
			System.out.println("NOOOOOOOO");
		}
		}else{
			System.out.println("NULLLLLLL");
		}
	}

}
