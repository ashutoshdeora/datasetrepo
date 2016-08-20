package com.ibm.model;

import java.io.Serializable;
import java.util.List;

import com.ibm.entity.AccountRun;
import com.ibm.entity.DatasetMaster;
import com.ibm.entity.DatasetRun;
import com.ibm.entity.FeatureRun;

public class DataSetRunBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DatasetRun datasetRun;
	private DatasetMaster datasetMaster;
	private List<FeatureForDataSetRunBean> featureForDataSetRunBeansList;
	private boolean readyForReExecute;
	private String styleClassForDataSetStatus;
	/**
	 * @return the datasetRun
	 */
	public DatasetRun getDatasetRun() {
		return datasetRun;
	}
	/**
	 * @param datasetRun the datasetRun to set
	 */
	public void setDatasetRun(DatasetRun datasetRun) {
		this.datasetRun = datasetRun;
	}
	
	/**
	 * @return the datasetMaster
	 */
	public DatasetMaster getDatasetMaster() {
		return datasetMaster;
	}
	/**
	 * @param datasetMaster the datasetMaster to set
	 */
	public void setDatasetMaster(DatasetMaster datasetMaster) {
		this.datasetMaster = datasetMaster;
	}
	/**
	 * @return the featureForDataSetRunBeansList
	 */
	public List<FeatureForDataSetRunBean> getFeatureForDataSetRunBeansList() {
		return featureForDataSetRunBeansList;
	}
	/**
	 * @param featureForDataSetRunBeansList the featureForDataSetRunBeansList to set
	 */
	public void setFeatureForDataSetRunBeansList(List<FeatureForDataSetRunBean> featureForDataSetRunBeansList) {
		this.featureForDataSetRunBeansList = featureForDataSetRunBeansList;
	}
	/**
	 * @return the readyForReExecute
	 */
	public boolean isReadyForReExecute() {
		return readyForReExecute;
	}
	/**
	 * @param readyForReExecute the readyForReExecute to set
	 */
	public void setReadyForReExecute(boolean readyForReExecute) {
		this.readyForReExecute = readyForReExecute;
	}
	/**
	 * @return the styleClassForDataSetStatus
	 */
	public String getStyleClassForDataSetStatus() {
		return styleClassForDataSetStatus;
	}
	/**
	 * @param styleClassForDataSetStatus the styleClassForDataSetStatus to set
	 */
	public void setStyleClassForDataSetStatus(String styleClassForDataSetStatus) {
		this.styleClassForDataSetStatus = styleClassForDataSetStatus;
	}
	
	

}
