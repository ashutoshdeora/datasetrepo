package com.ibm.model;

import java.io.Serializable;

public class DefectBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String HPQCID;

	/**
	 * @return the hPQCID
	 */
	public String getHPQCID() {
		return HPQCID;
	}

	/**
	 * @param hPQCID the hPQCID to set
	 */
	public void setHPQCID(String hPQCID) {
		HPQCID = hPQCID;
	}

}
