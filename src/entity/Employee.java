/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */

@Entity
@Table(name = "employee")
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;
	private String fname;
	private String lname;
	private String address;
	private String possition;// teller, manager, director
	@Column(name = "e_mail")
	private String eMail;
	@Column(name = "psw")
	private String password;

	public Employee() {
	}

	/*public Employee(int empId, String fname, String lname, String address,
			String possition, String eMail, String password) {
		super();
		this.empId = empId;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.possition = possition;
		this.eMail = eMail;
		this.password = password;
	}*/

	public Employee(String fname, String lname, String address,
			String possition, String eMail, String password) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.possition = possition;
		this.eMail = eMail;
		this.password = password;
	}

	public int getEmpId() {
		return empId;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getAddress() {
		return address;
	}

	public String geteMail() {
		return eMail;
	}

	public String getPossition() {
		return possition;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public void setPossition(String possition) {
		this.possition = possition;
	}

	public String  print() {
		StringBuilder sb = new StringBuilder();
		String devider="\t";
		
		sb.append(empId);
		sb.append(devider);
		sb.append(fname);
		sb.append(devider);
		sb.append(lname);
		sb.append(devider);
		sb.append(eMail);
		sb.append(devider);
		sb.append(password);
		sb.append(devider);
		sb.append(possition);
		sb.append(devider);
		sb.append(address);
//		sb.append(devider);
		
//		String s=empId + " " + fname + " " + lname + " " + eMail
//				+ " " + password + " " + possition + " " + address;
//		System.out.println(empId + " " + fname + " " + lname + " " + eMail
//				+ " " + password + " " + possition + " " + address);
		System.out.println(sb.toString());
		return sb.toString();
	}

}
