package comon;

import java.util.List;

import functions.TellerFunctions;

public class OCRequest {

	private String reqType; // open ||close || p_1k(dep, with, transf) || p_6Acc
	private String response;// accepted || denied
	private char accType;

	private List<String> clientIdsList;
	private String note;

	private String accFromNr;
	private String accToNr;
	private double amount;

	private int manId;
	private int tellerId;

	private boolean pin = false;
	private boolean status = false;// true -complete, false-incomplete

	public void print() {
		StringBuilder sb = new StringBuilder();
		sb.append(reqType);
		sb.append(", ");
		sb.append(response);
		sb.append(", ");
		sb.append(accFromNr);
		sb.append(", ");
		sb.append(accToNr);
		sb.append(", ");
		sb.append(amount);
		sb.append(", ");
		sb.append(pin);
		sb.append(", ");
		sb.append(tellerId);
		sb.append(", ");
		sb.append(manId);
		sb.append(", ");

		System.out.println(sb.toString());

		// System.out.println("lastManagerToConsiderIt -"
		// + manId + "\n tellerId-" + tellerId);
		// System.out.println("reqType-" + reqType + " \n accType-" + accType);
		// System.out.println("response-" + response + "\n note-" + note);
		// System.out.println("accFromNr-" + accFromNr + " \n accToNr-" +
		// accToNr
		// + "\n amount-" + amount);
		// System.out.println("pin-" + pin + "\n status-" + status);
		for (String s : clientIdsList) {
			System.out.println(s);
		}
	}

	public OCRequest() {
	}

	// open acc OCR
	public OCRequest(int telEmpId, List<String> persIds, String reqType,
			char type) {
		super();
		this.tellerId = telEmpId;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accType = type;
	}

	public OCRequest(int telEmpId, List<String> persIds, String reqType,
			String accNr) {
		super();
		this.tellerId = telEmpId;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accFromNr = accNr;
	}

	public OCRequest(int telEmpId, List<String> persIds, String reqType,
			String accNr1, String accNr2, double amount, String note2) {

		this.tellerId = telEmpId;
		this.clientIdsList = persIds;
		this.reqType = reqType;
		this.accFromNr = accNr2;
		this.accFromNr = accNr1;
		this.accToNr = accNr2;
		this.amount = amount;
		this.note = note2;
	}

	public int getTellerId() {
		return tellerId;
	}

	public void setTellerId(int tellerId) {
		this.tellerId = tellerId;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public int getLastManagerToConsiderIt() {
		return manId;
	}

	public void setLastManagerToConsiderIt(int lastManagerToConsiderIt) {
		this.manId = lastManagerToConsiderIt;
	}

}