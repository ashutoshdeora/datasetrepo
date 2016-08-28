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
@Table(name = "DATASETRUNDEFECT")
@NamedQuery(name = "DatasetRunDefect.findAll", query = "SELECT d FROM DatasetRunDefect d")
public class DatasetRunDefect implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatasetRunDefectPK id;

	@Column(nullable = false)
	private String defectsevrity;// severity

	@Column(nullable = false)
	private String defectstatus;// status

	@Column(precision = 22)
	private BigDecimal featurerunid;

	@Column(nullable = false, precision = 22)
	private BigDecimal hpqcdefectid;// id

	@Transient
	private List<DatasetRun> datasetRuns;

	@Column(nullable = false)
	private String defectOwner; // owner

	@Column(nullable = false)
	private String defectCause;// user-02 // defect issue cause

	@Column(nullable = false)
	private String defectName; // name

	@Column(nullable = false)
	private String defectPriority; // priority

	@Column(nullable = false)
	private String defectCreationDate; // creation-time

	@Column(nullable = false)
	private String defectLastModifies; // last-modified

	@Column(nullable = false)
	private String defectClosingDate; // closing-date

	@Column(nullable = false)
	private String defectRaisedBy; // detected-by

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
	 * @param datasetRuns
	 *            the datasetRuns to set
	 */
	public void setDatasetRuns(List<DatasetRun> datasetRuns) {
		this.datasetRuns = datasetRuns;
	}

	/**
	 * @return the defectOwner
	 */
	public String getDefectOwner() {
		return defectOwner;
	}

	/**
	 * @param defectOwner
	 *            the defectOwner to set
	 */
	public void setDefectOwner(String defectOwner) {
		this.defectOwner = defectOwner;
	}

	/**
	 * @return the defectCause
	 */
	public String getDefectCause() {
		return defectCause;
	}

	/**
	 * @param defectCause
	 *            the defectCause to set
	 */
	public void setDefectCause(String defectCause) {
		this.defectCause = defectCause;
	}

	/**
	 * @return the defectName
	 */
	public String getDefectName() {
		return defectName;
	}

	/**
	 * @param defectName
	 *            the defectName to set
	 */
	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}

	/**
	 * @return the defectPriority
	 */
	public String getDefectPriority() {
		return defectPriority;
	}

	/**
	 * @param defectPriority
	 *            the defectPriority to set
	 */
	public void setDefectPriority(String defectPriority) {
		this.defectPriority = defectPriority;
	}

	/**
	 * @return the defectCreationDate
	 */
	public String getDefectCreationDate() {
		return defectCreationDate;
	}

	/**
	 * @param defectCreationDate
	 *            the defectCreationDate to set
	 */
	public void setDefectCreationDate(String defectCreationDate) {
		this.defectCreationDate = defectCreationDate;
	}

	/**
	 * @return the defectLastModifies
	 */
	public String getDefectLastModifies() {
		return defectLastModifies;
	}

	/**
	 * @param defectLastModifies
	 *            the defectLastModifies to set
	 */
	public void setDefectLastModifies(String defectLastModifies) {
		this.defectLastModifies = defectLastModifies;
	}

	/**
	 * @return the defectClosingDate
	 */
	public String getDefectClosingDate() {
		return defectClosingDate;
	}

	/**
	 * @param defectClosingDate
	 *            the defectClosingDate to set
	 */
	public void setDefectClosingDate(String defectClosingDate) {
		this.defectClosingDate = defectClosingDate;
	}

	/**
	 * @return the defectRaisedBy
	 */
	public String getDefectRaisedBy() {
		return defectRaisedBy;
	}

	/**
	 * @param defectRaisedBy
	 *            the defectRaisedBy to set
	 */
	public void setDefectRaisedBy(String defectRaisedBy) {
		this.defectRaisedBy = defectRaisedBy;
	}

}