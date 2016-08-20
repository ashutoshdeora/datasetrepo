package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DATASETRUNDEFECT database table.
 * 
 */
@Embeddable
public class DatasetRunDefectPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false, precision=22)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long rundefectid;

	@Column(unique=true, nullable=false, precision=22)
	private long datasetrunid;

	public DatasetRunDefectPK() {
	}
	public long getRundefectid() {
		return this.rundefectid;
	}
	public void setRundefectid(long rundefectid) {
		this.rundefectid = rundefectid;
	}
	public long getDatasetrunid() {
		return this.datasetrunid;
	}
	public void setDatasetrunid(long datasetrunid) {
		this.datasetrunid = datasetrunid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DatasetRunDefectPK)) {
			return false;
		}
		DatasetRunDefectPK castOther = (DatasetRunDefectPK)other;
		return 
			(this.rundefectid == castOther.rundefectid)
			&& (this.datasetrunid == castOther.datasetrunid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.rundefectid ^ (this.rundefectid >>> 32)));
		hash = hash * prime + ((int) (this.datasetrunid ^ (this.datasetrunid >>> 32)));
		
		return hash;
	}
}