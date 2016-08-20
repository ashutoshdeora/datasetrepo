package com.ibm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the DATASETRUN database table.
 * 
 */
@Entity
@Table(name="DATASETRUN")
@NamedQuery(name="DatasetRun.findAll", query="SELECT d FROM DatasetRun d")
public class DatasetRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=22)
	private long datasetrunid;

	@Column(nullable=true, precision=22)
	private BigDecimal parentdatasetrunid;

	@Column(nullable=false, length=2)
	private String readyforrun;

	@Column(nullable=false, length=40)
	private String runby;

	@Column(nullable=false, length=20)
	private String runphase;

	@Column(nullable=false, length=20)
	private String runstatus;

	@Column(nullable=false)
	private Timestamp runtime;

	//bi-directional many-to-one association to DatasetMaster
	@ManyToOne
	@JoinColumn(name="DATASETID", nullable=false)
	private DatasetMaster datasetmaster;

	public DatasetRun() {
	}

	public long getDatasetrunid() {
		return this.datasetrunid;
	}

	public void setDatasetrunid(long datasetrunid) {
		this.datasetrunid = datasetrunid;
	}

	public BigDecimal getParentdatasetrunid() {
		return this.parentdatasetrunid;
	}

	public void setParentdatasetrunid(BigDecimal parentdatasetrunid) {
		this.parentdatasetrunid = parentdatasetrunid;
	}

	public String getReadyforrun() {
		return this.readyforrun;
	}

	public void setReadyforrun(String readyforrun) {
		this.readyforrun = readyforrun;
	}

	public String getRunby() {
		return this.runby;
	}

	public void setRunby(String runby) {
		this.runby = runby;
	}

	public String getRunphase() {
		return this.runphase;
	}

	public void setRunphase(String runphase) {
		this.runphase = runphase;
	}

	public String getRunstatus() {
		return this.runstatus;
	}

	public void setRunstatus(String runstatus) {
		this.runstatus = runstatus;
	}

	public Timestamp getRuntime() {
		return this.runtime;
	}

	public void setRuntime(Timestamp runtime) {
		this.runtime = runtime;
	}

	public DatasetMaster getDatasetmaster() {
		return this.datasetmaster;
	}

	public void setDatasetmaster(DatasetMaster datasetmaster) {
		this.datasetmaster = datasetmaster;
	}

}