package comon;

import java.util.List;

import functions.TellerFunctions;

public class OCRequest {
	private String note;
	private TellerFunctions tellersFunction;
	private List<String> clientIdsList;
	private String reqType; // open ||close
	private char accType;
	private String response;// accepted || denied
	private boolean pin = false;
	private String accFromNr;
	private String accToNr;
	private double amount;
	private boolean status = false;// true -complete, false-incomplete

	public OCRequest() {
	}

	// open acc OCR
	public OCRequest(TellerFunctions tf, List<String> persIds, String reqType,
			AccountType type) {
		super();
		this.tellersFunction = tf;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accType = type.value;
	}

	public OCRequest(TellerFunctions tf, List<String> persIds, String reqType,
			String accNr) {
		super();
		this.tellersFunction = tf;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accFromNr = accNr;
	}

	public OCRequest(TellerFunctions tf, List<String> persIds, String reqType,
			String accNr1, String accNr2,double amount, String note2) {

		this.tellersFunction = tf;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accFromNr = accNr2;
		this.accFromNr = accNr1;
		this.accToNr = accNr2;
		this.amount=amount;
		this.note = note2;
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

	public String getAccFromNr() {
		return accFromNr;
	}

	public void setAccFromNr(String accFromNr) {
		this.accFromNr = accFromNr;
	}

	public String getAccToNr() {
		return accToNr;
	}

	public void setAccToNr(String accToNr) {
		this.accToNr = accToNr;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public char getAccType() {
		return accType;
	}

	public void setAccType(char accType) {
		this.accType = accType;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatusComplete() {
		this.status = true;
	}

	public void setStatusInComplete() {
		this.status = false;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}