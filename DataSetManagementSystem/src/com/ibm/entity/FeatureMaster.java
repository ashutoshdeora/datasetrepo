package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the FEATUREMASTER database table.
 * 
 */
@Entity
@Table(name = "FEATUREMASTER")
@NamedQuery(name = "FeatureMaster.findAll", query = "SELECT f FROM FeatureMaster f")
public class FeatureMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, precision = 22)
	private long featureid;

	@Column(length = 255)
	private String ba;

	@Column(nullable = false, length = 40)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	@Column(length = 255)
	private String featuredatasetcatagoery;

	@Column(length = 255)
	private String featuregrouping;

	@Column(length = 255)
	private String featureowner;

	@Column(length = 1)
	private String featurerollout;

	@Column(nullable = false, length = 20)
	private String featureset;

	@Column(length = 255)
	private String featurestatus;

	@Column(length = 255)
	private String featuretestexecutionphase;
	
	@Column(length = 255)
	private String featurephase;

	@Column(length = 4000)
	private String featuretestscriptcomments;

	@Column(length = 4000)
	private String featuretestscriptname;

	@Column(length = 255)
	private String owner;

	@Column(precision = 10)
	private BigDecimal recordstatus;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(nullable = false)
	private Timestamp updatedate;

	@Column(nullable = false, length = 40)
	private String updatedby;
	
	@Transient
	private String datasetId;
	
	@Transient
	private List<String> defectId;

	// bi-directional many-to-one association to DatasetFeatureHistory
	@OneToMany(mappedBy = "featuremaster")
	private List<DatasetFeatureHistory> datasetfeaturehistories;

	// bi-directional many-to-many association to DatasetMaster
	@ManyToMany
	@JoinTable(name = "DATASETFEATURE", joinColumns = { @JoinColumn(name = "FEATUREID", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "DATASETID", nullable = false) })
	private List<DatasetMaster> datasetmasters;

	

	public FeatureMaster() {
	}

	public long getFeatureid() {
		return this.featureid;
	}

	public void setFeatureid(long featureid) {
		this.featureid = featureid;
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

	public List<DatasetFeatureHistory> getDatasetfeaturehistories() {
		return this.datasetfeaturehistories;
	}

	public void setDatasetfeaturehistories(List<DatasetFeatureHistory> datasetfeaturehistories) {
		this.datasetfeaturehistories = datasetfeaturehistories;
	}

	public DatasetFeatureHistory addDatasetfeaturehistory(DatasetFeatureHistory datasetfeaturehistory) {
		getDatasetfeaturehistories().add(datasetfeaturehistory);
		datasetfeaturehistory.setFeaturemaster(this);

		return datasetfeaturehistory;
	}

	public DatasetFeatureHistory removeDatasetfeaturehistory(DatasetFeatureHistory datasetfeaturehistory) {
		getDatasetfeaturehistories().remove(datasetfeaturehistory);
		datasetfeaturehistory.setFeaturemaster(null);

		return datasetfeaturehistory;
	}

	public List<DatasetMaster> getDatasetmasters() {
		return this.datasetmasters;
	}

	public void setDatasetmasters(List<DatasetMaster> datasetmasters) {
		this.datasetmasters = datasetmasters;
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
	 * @return the defectId
	 */
	public List<String> getDefectId() {
		return defectId;
	}

	/**
	 * @param defectId the defectId to set
	 */
	public void setDefectId(List<String> defectId) {
		this.defectId = defectId;
	}

	/**
	 * @return the featurephase
	 */
	public String getFeaturephase() {
		return featurephase;
	}

	/**
	 * @param featurephase the featurephase to set
	 */
	public void setFeaturephase(String featurephase) {
		this.featurephase = featurephase;
	}

}