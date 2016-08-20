package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DATASETHISTORY database table.
 * 
 */
@Entity
@Table(name="DATASETHISTORY")
@NamedQuery(name="DatasetHistory.findAll", query="SELECT d FROM DatasetHistory d")
public class DatasetHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, precision=22)
	private long datasethistoryid;
	@Column(nullable=false, length=250)
	private String datasetname;

	@Column(nullable=false, length=40)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;


	@Column(nullable=false)
	private Timestamp updatedate;

	@Column(nullable=false, length=40)
	private String updatedby;

	//bi-directional many-to-one association to DatasetMaster
	@ManyToOne
	@JoinColumn(name="DATASETID", nullable=false)
	private DatasetMaster datasetmaster;

	public DatasetHistory() {
	}

	public long getDatasethistoryid() {
		return this.datasethistoryid;
	}

	public void setDatasethistoryid(long datasethistoryid) {
		this.datasethistoryid = datasethistoryid;
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

	public String getDatasetname() {
		return this.datasetname;
	}

	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
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

	public DatasetMaster getDatasetmaster() {
		return this.datasetmaster;
	}

	public void setDatasetmaster(DatasetMaster datasetmaster) {
		this.datasetmaster = datasetmaster;
	}

}