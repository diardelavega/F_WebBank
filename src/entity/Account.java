/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
public class Account {

	@Id
	@Column(name = "accountId")
	private String accountId;
	@Column(name = "opendate", nullable = false)
	private Timestamp openDate;
	@Column(nullable = false)
	private double balance;
	// private int integerBalance;
	// @Column(nullable = false)
	// private int decimalBalance;
	// @Column(nullable=false )
	private char accType;// intrest || running
	private String accStatus; // active || passive

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "customers_account", joinColumns = { @JoinColumn(name = "accountId") }, inverseJoinColumns = { @JoinColumn(name = "personalId") })
	private List<Customers> customers = new ArrayList<Customers>();

	// @ManyToMany(mappedBy = "accounts")
	// private List<Customers> customers = new ArrayList<Customers>();

	public Account() {
	}

	public Account(String accountId, Timestamp openDate, double balance,
			char accType, String accStatus) {
		super();
		this.accountId = accountId;
		this.openDate = openDate;
		this.balance = balance;
		this.accType = accType;
		this.accStatus = accStatus;
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

	public List<Customers> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customers> customers) {
		this.customers = customers;
	}

	public Timestamp getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	public void print() {
		System.out.print(accountId + " " + accType + " " + balance + " "
				+ accStatus);
	}

}
