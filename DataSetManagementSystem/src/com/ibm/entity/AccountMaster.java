package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the ACCOUNTMASTER database table.
 * 
 */
@Entity
@Table(name = "ACCOUNTMASTER")
@NamedQuery(name = "AccountMaster.findAll", query = "SELECT a FROM AccountMaster a")
public class AccountMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, precision = 22)
	private long accountid;

	@Column(nullable = false, length = 200)
	private String accountname;

	@Column(nullable = false, precision = 22)
	private BigDecimal accountsetid;

	@Column(nullable = false, length = 40)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(nullable = false)
	private Timestamp updatedate;

	@Column(nullable = false, length = 40)
	private String updatedby;
	
	@Transient
	private String datasetId;



	// bi-directional many-to-one association to DatasetAccountHistory
	@OneToMany(mappedBy = "accountmaster")
	private List<DatasetAccountHistory> datasetaccounthistories;

	// bi-directional many-to-many association to DatasetMaster
	@ManyToMany
	@JoinTable(name = "DATASETACCOUNT", joinColumns = { @JoinColumn(name = "ACCOUNTMASTERID", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "DATASETMASTERID", nullable = false) })
	private List<DatasetMaster> datasetmastersList;

	public AccountMaster() {
	}

	public long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}

	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public BigDecimal getAccountsetid() {
		return this.accountsetid;
	}

	public void setAccountsetid(BigDecimal accountsetid) {
		this.accountsetid = accountsetid;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public List<DatasetAccountHistory> getDatasetaccounthistories() {
		return this.datasetaccounthistories;
	}

	public void setDatasetaccounthistories(List<DatasetAccountHistory> datasetaccounthistories) {
		this.datasetaccounthistories = datasetaccounthistories;
	}

	public DatasetAccountHistory addDatasetaccounthistory(DatasetAccountHistory datasetaccounthistory) {
		getDatasetaccounthistories().add(datasetaccounthistory);
		datasetaccounthistory.setAccountmaster(this);

		return datasetaccounthistory;
	}

	public DatasetAccountHistory removeDatasetaccounthistory(DatasetAccountHistory datasetaccounthistory) {
		getDatasetaccounthistories().remove(datasetaccounthistory);
		datasetaccounthistory.setAccountmaster(null);

		return datasetaccounthistory;
	}

	

	/**
	 * @return the datasetId
	 */
	public String getDatasetId() {
		return datasetId;
	}

	/**
	 * @param datasetId the datasetId to set
	 */
	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	/**
	 * @return the datasetmastersList
	 */
	public List<DatasetMaster> getDatasetmastersList() {
		return datasetmastersList;
	}

	/**
	 * @param datasetmastersList the datasetmastersList to set
	 */
	public void setDatasetmastersList(List<DatasetMaster> datasetmastersList) {
		this.datasetmastersList = datasetmastersList;
	}

}