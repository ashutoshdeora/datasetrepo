package com.ibm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: MasterData
 *
 */
@Entity
@Table(name="MASTERDATA")
public class MasterData implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public MasterData() {
		super();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
   
	@Column
	private String catagory;
	@Column
	private String catdata;
	


	@Column(nullable=false, length=40)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;


	@Column(nullable=false)
	private Timestamp updatedate;

	@Column(nullable=false, length=40)
	private String updatedby;
	

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * @return the catagory
	 */
	public String getCatagory() {
		return catagory;
	}
	/**
	 * @param catagory the catagory to set
	 */
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	/**
	 * @return the catdata
	 */
	public String getCatdata() {
		return catdata;
	}
	/**
	 * @param catdata the catdata to set
	 */
	public void setCatdata(String catdata) {
		this.catdata = catdata;
	}
	
	
	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the creationdate
	 */
	public Timestamp getCreationdate() {
		return creationdate;
	}
	/**
	 * @param creationdate the creationdate to set
	 */
	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}
	/**
	 * @return the updatedate
	 */
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	/**
	 * @param updatedate the updatedate to set
	 */
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	/**
	 * @return the updatedby
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param updatedby the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	

	
	
	
}
