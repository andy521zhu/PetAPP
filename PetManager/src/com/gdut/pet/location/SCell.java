/**
 * 基于基站定位
 */
package com.gdut.pet.location;

public class SCell {
	private int MCC;
	private int MNC;
	private int LAC;
	private int CID;
	
	public int getMCC() {
		return MCC;
	}
	public void setMCC(int mCC) {
		MCC = mCC;
	}
	public int getMNC() {
		return MNC;
	}
	public void setMNC(int mNC) {
		MNC = mNC;
	}
	public int getLAC() {
		return LAC;
	}
	public void setLAC(int lAC) {
		LAC = lAC;
	}
	public int getCID() {
		return CID;
	}
	public void setCID(int cID) {
		CID = cID;
	}
}
