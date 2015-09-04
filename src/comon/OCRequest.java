package comon;

import java.util.List;

import functions.TellerFunctions;

public class OCRequest {
	private String note;
	private int tellerId;
	private List<String> clientIdsList;
	private String reqType; // open ||close || p_1k(dep, with, transf) || p_6Acc
	private char accType;
	private String response;// accepted || denied
	private boolean pin = false;
	private String accFromNr;
	private String accToNr;
	private double amount;
	private boolean status = false;// true -complete, false-incomplete

	public int lastManCons;

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
}