package com.ibm.model;

import java.io.Serializable;

public class DefectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String HPQCID; // id
	private String defectseverity;// severity
	private String defectstatus;// status
	private String defectOwner; // owner
	private String defectCause;// user-02 // defect issue cause
	private String defectName; // name
	private String defectPriority; // priority
	private String defectCreationDate; // creation-time
	private String defectLastModifies; // last-modified
	private String defectClosingDate; // closing-date
	private String defectRaisedBy; // detected-by

	/**
	 * @return the hPQCID
	 */
	public String getHPQCID() {
		return HPQCID;
	}

	/**
	 * @param hPQCID
	 *            the hPQCID to set
	 */
	public void setHPQCID(String hPQCID) {
		HPQCID = hPQCID;
	}

	/**
	 * @return the defectseverity
	 */
	public String getDefectseverity() {
		return defectseverity;
	}

	/**
	 * @param defectseverity
	 *            the defectseverity to set
	 */
	public void setDefectseverity(String defectseverity) {
		this.defectseverity = defectseverity;
	}

	/**
	 * @return the defectstatus
	 */
	public String getDefectstatus() {
		return defectstatus;
	}

	/**
	 * @param defectstatus
	 *            the defectstatus to set
	 */
	public void setDefectstatus(String defectstatus) {
		this.defectstatus = defectstatus;
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
