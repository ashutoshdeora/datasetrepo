package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the DATASETFEATUREHISTORY database table.
 * 
 */
@Entity
@Table(name="DATASETFEATUREHISTORY")
@NamedQuery(name="DatasetFeatureHistory.findAll", query="SELECT d FROM DatasetFeatureHistory d")
public class DatasetFeatureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, precision=22)
	private long featurehistoryid;

	@Column(length=255)
	private String ba;

	@Column(nullable=false, length=40)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;

	@Column(length=255)
	private String featuredatasetcatagoery;

	@Column(length=255)
	private String featuregrouping;

	@Column(length=255)
	private String featureowner;

	@Column(length=1)
	private String featurerollout;

	@Column(nullable=false, length=20)
	private String featureset;

	@Column(length=255)
	private String featurestatus;

	@Column(length=255)
	private String featuretestexecutionphase;

	@Column(length=4000)
	private String featuretestscriptcomments;

	@Column(length=4000)
	private String featuretestscriptname;

	@Column(length=255)
	private String owner;

	@Column(precision=10)
	private BigDecimal recordstatus;

	@Column(nullable=false)
	private Timestamp updatedate;

	@Column(nullable=false, length=40)
	private String updatedby;

	//bi-directional many-to-one association to DatasetMaster
	@ManyToOne
	@JoinColumn(name="DATASETID", nullable=false)
	private DatasetMaster datasetmaster;

	//bi-directional many-to-one association to FeatureMaster
	@ManyToOne
	@JoinColumn(name="FEATUREMASTERID", nullable=false)
	private FeatureMaster featuremaster;

	public DatasetFeatureHistory() {
	}

	public long getFeaturehistoryid() {
		return this.featurehistoryid;
	}

	public void setFeaturehistoryid(long featurehistoryid) {
		this.featurehistoryid = featurehistoryid;
	}

	public String getBa() {
		return this.ba;
	}

	public void setBa(String ba) {
		this.ba = ba;
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

	public String getFeaturedatasetcatagoery() {
		return this.featuredatasetcatagoery;
	}

	public void setFeaturedatasetcatagoery(String featuredatasetcatagoery) {
		this.featuredatasetcatagoery = featuredatasetcatagoery;
	}

	public String getFeaturegrouping() {
		return this.featuregrouping;
	}

	public void setFeaturegrouping(String featuregrouping) {
		this.featuregrouping = featuregrouping;
	}

	public String getFeatureowner() {
		return this.featureowner;
	}

	public void setFeatureowner(String featureowner) {
		this.featureowner = featureowner;
	}

	public String getFeaturerollout() {
		return this.featurerollout;
	}

	public void setFeaturerollout(String featurerollout) {
		this.featurerollout = featurerollout;
	}

	public String getFeatureset() {
		return this.featureset;
	}

	public void setFeatureset(String featureset) {
		this.featureset = featureset;
	}

	public String getFeaturestatus() {
		return this.featurestatus;
	}

	public void setFeaturestatus(String featurestatus) {
		this.featurestatus = featurestatus;
	}

	public String getFeaturetestexecutionphase() {
		return this.featuretestexecutionphase;
	}

	public void setFeaturetestexecutionphase(String featuretestexecutionphase) {
		this.featuretestexecutionphase = featuretestexecutionphase;
	}

	public String getFeaturetestscriptcomments() {
		return this.featuretestscriptcomments;
	}

	public void setFeaturetestscriptcomments(String featuretestscriptcomments) {
		this.featuretestscriptcomments = featuretestscriptcomments;
	}

	public String getFeaturetestscriptname() {
		return this.featuretestscriptname;
	}

	public void setFeaturetestscriptname(String featuretestscriptname) {
		this.featuretestscriptname = featuretestscriptname;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getRecordstatus() {
		return this.recordstatus;
	}

	public void setRecordstatus(BigDecimal recordstatus) {
		this.recordstatus = recordstatus;
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

	public FeatureMaster getFeaturemaster() {
		return this.featuremaster;
	}

	public void setFeaturemaster(FeatureMaster featuremaster) {
		this.featuremaster = featuremaster;
	}

}