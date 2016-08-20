package com.ibm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * The persistent class for the FEATURERUN database table.
 * 
 */
@Entity
@Table(name="FEATURERUN")
@NamedQuery(name="FeatureRun.findAll", query="SELECT f FROM FeatureRun f")
public class FeatureRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=22)
	private long featurerunid;

	@Column(nullable=false, precision=22)
	private BigDecimal datasetrunid;

	@Column(nullable=false, precision=22)
	private BigDecimal featuremasterid;

	@Column(nullable=false, length=20)
	private String status;
	
	@Transient
	private List<DatasetRunDefect> listofDefects;

	public FeatureRun() {
	}

	public long getFeaturerunid() {
		return this.featurerunid;
	}

	public void setFeaturerunid(long featurerunid) {
		this.featurerunid = featurerunid;
	}

	public BigDecimal getDatasetrunid() {
		return this.datasetrunid;
	}

	public void setDatasetrunid(BigDecimal datasetrunid) {
		this.datasetrunid = datasetrunid;
	}

	public BigDecimal getFeaturemasterid() {
		return this.featuremasterid;
	}

	public void setFeaturemasterid(BigDecimal featuremasterid) {
		this.featuremasterid = featuremasterid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the listofDefects
	 */
	public List<DatasetRunDefect> getListofDefects() {
		return listofDefects;
	}

	/**
	 * @param listofDefects the listofDefects to set
	 */
	public void setListofDefects(List<DatasetRunDefect> listofDefects) {
		this.listofDefects = listofDefects;
	}

}