package com.ibm.model;

import java.io.Serializable;
import java.util.List;

import com.ibm.entity.DatasetRunDefect;
import com.ibm.entity.FeatureMaster;

public class FeatureForDataSetRunBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FeatureMaster featureMaster;
	private String status;
	private long featurerunid;
	private List<DatasetRunDefect> datasetRunDefectsList;
	
	/**
	 * @return the featureMaster
	 */
	public FeatureMaster getFeatureMaster() {
		return featureMaster;
	}
	/**
	 * @param featureMaster the featureMaster to set
	 */
	public void setFeatureMaster(FeatureMaster featureMaster) {
		this.featureMaster = featureMaster;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the featurerunid
	 */
	public long getFeaturerunid() {
		return featurerunid;
	}
	/**
	 * @param featurerunid the featurerunid to set
	 */
	public void setFeaturerunid(long featurerunid) {
		this.featurerunid = featurerunid;
	}
	/**
	 * @return the datasetRunDefectsList
	 */
	public List<DatasetRunDefect> getDatasetRunDefectsList() {
		return datasetRunDefectsList;
	}
	/**
	 * @param datasetRunDefectsList the datasetRunDefectsList to set
	 */
	public void setDatasetRunDefectsList(List<DatasetRunDefect> datasetRunDefectsList) {
		this.datasetRunDefectsList = datasetRunDefectsList;
	}
	
	

}
