package com.ibm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the DATASETRUNDEFECT database table.
 * 
 */
@Entity
@Table(name="DATASETRUNDEFECT")
@NamedQuery(name="DatasetRunDefect.findAll", query="SELECT d FROM DatasetRunDefect d")
public class DatasetRunDefect implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatasetRunDefectPK id;

	@Column(nullable=false, length=20)
	private String defectsevrity;

	@Column(nullable=false, length=20)
	private String defectstatus;

	@Column(precision=22)
	private BigDecimal featurerunid;

	@Column(nullable=false, precision=22)
	private BigDecimal hpqcdefectid;
	
	@Transient
	private List<DatasetRun> datasetRuns;

	public DatasetRunDefect() {
	}

	public DatasetRunDefectPK getId() {
		return this.id;
	}

	public void setId(DatasetRunDefectPK id) {
		this.id = id;
	}

	public String getDefectsevrity() {
		return this.defectsevrity;
	}

	public void setDefectsevrity(String defectsevrity) {
		this.defectsevrity = defectsevrity;
	}

	public String getDefectstatus() {
		return this.defectstatus;
	}

	public void setDefectstatus(String defectstatus) {
		this.defectstatus = defectstatus;
	}

	public BigDecimal getFeaturerunid() {
		return this.featurerunid;
	}

	public void setFeaturerunid(BigDecimal featurerunid) {
		this.featurerunid = featurerunid;
	}

	public BigDecimal getHpqcdefectid() {
		return this.hpqcdefectid;
	}

	public void setHpqcdefectid(BigDecimal hpqcdefectid) {
		this.hpqcdefectid = hpqcdefectid;
	}

	/**
	 * @return the datasetRuns
	 */
	public List<DatasetRun> getDatasetRuns() {
		return datasetRuns;
	}

	/**
	 * @param datasetRuns the datasetRuns to set
	 */
	public void setDatasetRuns(List<DatasetRun> datasetRuns) {
		this.datasetRuns = datasetRuns;
	}

	

}