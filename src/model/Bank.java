package model;

import java.io.Serializable;

public class Bank implements Serializable {

	private static final long serialVersionUID = 1L;
	private int bankID;
	private String namaBank;
	
	public int getBankID() {
		return bankID;
	}
	
	public void setBankID(int bankID) {
		this.bankID = bankID;
	}
	
	public String getNamaBank() {
		return namaBank;
	}
	
	public void setNamaBank(String namaBank) {
		this.namaBank = namaBank;
	}
	
}
