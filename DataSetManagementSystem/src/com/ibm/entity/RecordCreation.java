package com.ibm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: RecordCreation
 *
 */

public class RecordCreation implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public RecordCreation() {
		super();
	}
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(nullable=false) 
	@Temporal(TemporalType.TIMESTAMP)
	private Date updationDate;
	@Column(nullable=false)
	private String createdBy;
	@Column(nullable=false)
	private String updatedBy;

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the updationDate
	 */
	public Date getUpdationDate() {
		return updationDate;
	}
	/**
	 * @param updationDate the updationDate to set
	 */
	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RecordCreation [creationDate=" + creationDate + ", updationDate=" + updationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ "]";
	}
   
}
