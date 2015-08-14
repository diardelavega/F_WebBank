/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue
	@Column(name = "trNr")
	private long transactionNr;
	@Column(name = "trData")
	private Timestamp trDate;
	@Column(name = "personalId")
	private String clientId;
	private String acction; // deposid withdraw transfer
	@Column(name = "acc1")
	private String account1;
	@Column(name = "acc2")
	private String account2;
	private double amount;
	private char method;// t, a, o, teller,ATM, online

	public Transaction() {
	}

	public Transaction(Timestamp trDate, String clientId, String acction,
			String account1, String account2, double ammount, char method) {
		super();
		// this.transactionNr = transactionNr;
		this.trDate = trDate;
		this.clientId = clientId;
		this.acction = acction;
		this.account1 = account1;
		this.account2 = account2;
		this.amount = ammount;
		this.method = method;
	}

	public long getTransactionNr() {
		return transactionNr;
	}

	public void setTransactionNr(long transactionNr) {
		this.transactionNr = transactionNr;
	}

	public Timestamp getTrDate() {
		return trDate;
	}

	public void setTrDate(Timestamp trDate) {
		this.trDate = trDate;
	}

	public String getAcction() {
		return acction;
	}

	public void setAcction(String acction) {
		this.acction = acction;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmmount(double ammount) {
		this.amount = ammount;
	}

	public char getMethod() {
		return method;
	}

	public void setMethod(char method) {
		this.method = method;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAccount1() {
		return account1;
	}

	public void setAccount1(String account1) {
		this.account1 = account1;
	}

	public String getAccount2() {
		return account2;
	}

	public void setAccount2(String account2) {
		this.account2 = account2;
	}

}
