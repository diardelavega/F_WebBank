package comon;

import java.util.List;

import functions.TellerFunctions;

public class OCRequest {
	private TellerFunctions tellersFunction;
	private List<String> clientIdsList;
	private String reqType; // open ||close
	private char accType;
	private String response;// accepted || denied
	private boolean pin = false;
	private String accNr;

	public OCRequest() {
	}

	//open acc OCR
	public OCRequest(TellerFunctions tf, List<String> persIds,
			String reqType, AccountType type) {
		super();
		this.tellersFunction = tf;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accType=type.value;
	}

	public OCRequest(TellerFunctions tellersFunction, List<String> cList,
			String reqType, String accNr) {
		super();
		this.tellersFunction = tellersFunction;
		this.clientIdsList = cList;
		this.reqType = reqType;
		this.accNr = accNr;
	}

	

	public TellerFunctions getTellersFunction() {
		return tellersFunction;
	}

	public void setTellersFunction(TellerFunctions tellersFunction) {
		this.tellersFunction = tellersFunction;
	}

	public List<String> getClientIdsList() {
		return clientIdsList;
	}

	public void setClientIdsList(List<String> clientIdsList) {
		this.clientIdsList = clientIdsList;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public boolean isPin() {
		return pin;
	}

	public void pin() {
		this.pin = true;
	}

	public void unPin() {
		this.pin = false;
	}

	public String getAccNr() {
		return accNr;
	}

	public void setAccNr(String accNr) {
		this.accNr = accNr;
	}

	public char getAccType() {
		return accType;
	}

	public void setAccType(char accType) {
		this.accType = accType;
	}

}