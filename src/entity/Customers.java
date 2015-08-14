/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */

@Entity
@Table(name = "customers")
public class Customers {

	@Id
	@Column(name = "personalId")
	private String personalId;
	@Column(name = "accountsNr")
	private int accountsNr;
	@Column(name = "e_mail")
	private String eMail;
	private String fname;
	private String lname;
	private Timestamp bdata;
	private String address;
	private String phone;
	@Column(name="psw")
	private String password;
	private int customerStatus;// OK-0, onHold(frozen acc)-1, something else ??

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "customers_account", joinColumns = { @JoinColumn(name = "personalId") }, inverseJoinColumns = { @JoinColumn(name = "accId") })
//	private List<Account> accounts = new ArrayList<Account>();

	 @ManyToMany(mappedBy = "customers")
	 private List<Account> accounts = new ArrayList<>();

	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy="customers" ) private
	 * List<Transaction> transactions = new ArrayList<>();
	 */

	public Customers() {
	}

	public Customers(String personalId, int accountsNr, String eMail,
			String fname, String lname, Timestamp bdata, String address,
			String phone, String password, int customerStatus) {
		super();
		this.personalId = personalId;
		this.accountsNr = accountsNr;
		this.eMail = eMail;
		this.fname = fname;
		this.lname = lname;
		this.bdata = bdata;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.customerStatus = customerStatus;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	// public List<Transaction> getTransactions() {
	// return transactions;
	// }
	//
	// public void setTransactions(List<Transaction> transactions) {
	// this.transactions = transactions;
	// }

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public int getAccountsNr() {
		return accountsNr;
	}

	public void setAccountsNr(int accountsNr) {
		this.accountsNr = accountsNr;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public Timestamp getBdata() {
		return bdata;
	}

	public void setBdata(Timestamp bdata) {
		this.bdata = bdata;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(int customerStatus) {
		this.customerStatus = customerStatus;
	}

	public void print() {
		System.out.println(personalId + " " + fname + " " + lname + " " + eMail
				+ " " + password + " " + accountsNr + " " + customerStatus);
	}

}
