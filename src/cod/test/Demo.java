package cod.test;

import utils.DirMsgWsHandler;

public class Demo {

	public static void main(String[] args) {

		String msg="{'head':'info','id':'2'}";
//			{"head":"info","id":"12","fname":"Mario","lname":"Rista","address":"Kolonje Lushnje","possition":"DIRECTOR","eMail":"mr@hua.gr"}
		DirMsgWsHandler.switchit(msg);
		
	}

}
