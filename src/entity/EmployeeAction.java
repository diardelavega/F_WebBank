package entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.CloneCopyPolicy;

/*@Entity
 @Table(name = "employee_action")*/
public class EmployeeAction {

	// @Id
	// @GeneratedValue
	private int actionSerial;
	// @Column(name = "actions")
	private String actionType;
	private String note; // a random note for the transaction
	// @Column(name = "clieantId")
	private List<String> customerId;
	// @Column(name = "accountId")
	private String accountId1;
	private String accountId2;
	private int empId;
	private long trNr;

	private double amount;

	// private String account;

	public EmployeeAction() {
	}

	public EmployeeAction(String actionType, String note, int empId, long trNr) {
		super();
		this.actionType = actionType;
		this.note = note;
		this.empId = empId;
		this.trNr = trNr;
		// this.accountId = "-";
		// this.customerId = "-";
	}

	public EmployeeAction(String accountId, String actionType, String note,
			int empId) {
		super();
		this.actionType = actionType;
		this.note = note;
		this.empId = empId;
		// this.trNr = (Long) null;
		this.accountId1 = accountId;
		// this.customerId = null;
	}

	public EmployeeAction(List<String> persId, String action, int empId2) {
		this.customerId = persId;
		this.actionType = action;
		this.empId = empId2;
	}

	public int getActionSerial() {
		return actionSerial;
	}

	public void setActionSerial(int actionSerial) {
		this.actionSerial = actionSerial;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public long getTrNr() {
		return trNr;
	}

	public void setTrNr(long trNr) {
		this.trNr = trNr;
	}

	public List<String> getCustomerId() {
		return customerId;
	}

	public void setCustomerId(List<String> customerId) {
		this.customerId = customerId;
	}

	public String getAccountId1() {
		return accountId1;
	}

	public void setAccountId1(String accountId) {
		this.accountId1 = accountId;
	}

	public String getAccountId2() {
		return accountId2;
	}

	public void setAccountId2(String accountId) {
		this.accountId2 = accountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	/*
	 * public String getAccount() { return account; }
	 * 
	 * public void setAccount(String account) { this.account = account; }
	 */

	public String prinatable() {
		String delimeter = ", ";
		StringBuilder sb = new StringBuilder();
		sb.append(idGenerator());
		sb.append(delimeter);
		sb.append(empId);
		sb.append(delimeter);
		sb.append(actionType);
		sb.append(delimeter);
		sb.append(accountId1);
		sb.append(delimeter);
		if (customerId != null) {
			for (String s : customerId) {
				sb.append(s);
				sb.append(delimeter);
			}
		}
		if (amount > 0.5) {
			sb.append(amount);
			sb.append(delimeter);
		}
		sb.append(trNr);
		sb.append(delimeter);
		sb.append(note);
		sb.append(delimeter);
		return sb.toString();
	}

	private String idGenerator() {
		// DateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// Date today = Calendar.getInstance().getTime();
		return df.format(Calendar.getInstance().getTime());
	}
}
