package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers_account")
public class CustomersAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4113546839116058405L;

	@Id
	@Column(name = "personalId")
	private String personalId;

	@Id
	@Column(name = "accountId")
	private String accountId;

	public CustomersAccount() {
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	

}
