package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client_transaction")
public class ClientTransaction implements Serializable{

	@Id
	private String personalId;
	@Id
	private long transactionNr;

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public long getTransactionNr() {
		return transactionNr;
	}

	public void setTransactionNr(long transactionNr) {
		this.transactionNr = transactionNr;
	}

}
