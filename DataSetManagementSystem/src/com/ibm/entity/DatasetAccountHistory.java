package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DATASETACCOUNTHISTORY database table.
 * 
 */
@Entity
@Table(name="DATASETACCOUNTHISTORY")
@NamedQuery(name="DatasetAccountHistory.findAll", query="SELECT d FROM DatasetAccountHistory d")
public class DatasetAccountHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, precision=22)
	private long accounthistoryid;

	@Column(nullable=false, length=40)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;

	@Column(nullable=false)
	private Timestamp updatedate;

	@Column(nullable=false, length=40)
	private String updatedby;

	//bi-directional many-to-one association to AccountMaster
	@ManyToOne
	@JoinColumn(name="ACCOUNTMASTERID", nullable=false)
	private AccountMaster accountmaster;

	//bi-directional many-to-one association to DatasetMaster
	@ManyToOne
	@JoinColumn(name="DATASETMASTERID", nullable=false)
	private DatasetMaster datasetmaster;

	public DatasetAccountHistory() {
	}

	public long getAccounthistoryid() {
		return this.accounthistoryid;
	}

	public void setAccounthistoryid(long accounthistoryid) {
		this.accounthistoryid = accounthistoryid;
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

	public AccountMaster getAccountmaster() {
		return this.accountmaster;
	}

	public void setAccountmaster(AccountMaster accountmaster) {
		this.accountmaster = accountmaster;
	}

	public DatasetMaster getDatasetmaster() {
		return this.datasetmaster;
	}

	public void setDatasetmaster(DatasetMaster datasetmaster) {
		this.datasetmaster = datasetmaster;
	}

}