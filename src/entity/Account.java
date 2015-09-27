/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */

@Entity
@Table(name = "account")
public class Account  {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "accountId")
	private String accountId;
	@Column(name = "opendate", nullable = false)
	private Timestamp openDate;
	@Column(nullable = false)
	private double balance;
	private char accType;// intrest || running
	private String accStatus; // active || passive

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "customers_account", joinColumns = { @JoinColumn(name = "accountId") }, inverseJoinColumns = { @JoinColumn(name = "personalId") })
	private List<Customers> customers;
	
	public List<Customers> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customers> customers) {
		this.customers = customers;
	}

	// private List<Customers> customerss = new ArrayList<Customers>();

	// @ManyToMany(mappedBy = "accounts")
	// private List<Customers> customers = new ArrayList<Customers>();

	public Account() {
//		customers=new ArrayList<Customers>();
	}

	public Account(String accountId, Timestamp openDate, double balance,
			char accType, String accStatus) {
		super();
		this.accountId = accountId;
		this.openDate = openDate;
		this.balance = balance;
		this.accType = accType;
		this.accStatus = accStatus;
//		customers=new ArrayList<Customers>();.
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccountId() {
		return accountId;
	}

	public char getAccType() {
		return accType;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAccType(char accType2) {
		this.accType = accType2;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public Timestamp getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	public void print() {
		System.out.print(accountId + " " + accType + " " + balance + " "
				+ accStatus + " " + openDate);
	}

}
