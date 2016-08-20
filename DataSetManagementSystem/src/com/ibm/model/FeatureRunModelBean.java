package com.ibm.model;

import java.io.Serializable;
import java.util.List;

import com.ibm.entity.DatasetRunDefect;
import com.ibm.entity.FeatureMaster;
import com.ibm.entity.FeatureRun;

public class FeatureRunModelBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String featureSetId;
	private List<DefectBean> defectBeansList;
	private String featureRunPhase;
	private String featureRunResult;
	private String featureTestPhase;
	private long featuremasterID;
	private boolean readyForinsert;
	private FeatureMaster featureMaster;
	private String inputDefects;
	private String defectsData;
	private List<String> defectList;
	private List<DatasetRunDefect> defects;
	private List<FeatureRun> featureRuns;
	private int featureRunCount ;
	
	/**
	 * @return the featureSetId
	 */
	public String getFeatureSetId() {
		return featureSetId;
	}
	/**
	 * @param featureSetId the featureSetId to set
	 */
	public void setFeatureSetId(String featureSetId) {
		this.featureSetId = featureSetId;
	}
	/**
	 * @return the defectBeansList
	 */
	public List<DefectBean> getDefectBeansList() {
		return defectBeansList;
	}
	/**
	 * @param defectBeansList the defectBeansList to set
	 */
	public void setDefectBeansList(List<DefectBean> defectBeansList) {
		this.defectBeansList = defectBeansList;
	}
	/**
	 * @return the featureRunPhase
	 */
	public String getFeatureRunPhase() {
		return featureRunPhase;
	}
	/**
	 * @param featureRunPhase the featureRunPhase to set
	 */
	public void setFeatureRunPhase(String featureRunPhase) {
		this.featureRunPhase = featureRunPhase;
	}
	/**
	 * @return the featureRunResult
	 */
	public String getFeatureRunResult() {
		return featureRunResult;
	}
	/**
	 * @param featureRunResult the featureRunResult to set
	 */
	public void setFeatureRunResult(String featureRunResult) {
		this.featureRunResult = featureRunResult;
	}
	/**
	 * @return the featureTestPhase
	 */
	public String getFeatureTestPhase() {
		return featureTestPhase;
	}
	/**
	 * @param featureTestPhase the featureTestPhase to set
	 */
	public void setFeatureTestPhase(String featureTestPhase) {
		this.featureTestPhase = featureTestPhase;
	}
	/**
	 * @return the readyForinsert
	 */
	public boolean isReadyForinsert() {
		return readyForinsert;
	}
	/**
	 * @param readyForinsert the readyForinsert to set
	 */
	public void setReadyForinsert(boolean readyForinsert) {
		this.readyForinsert = readyForinsert;
	}
	/**
	 * @return the featuremasterID
	 */
	public long getFeaturemasterID() {
		return featuremasterID;
	}
	/**
	 * @param featuremasterID the featuremasterID to set
	 */
	public void setFeaturemasterID(long featuremasterID) {
		this.featuremasterID = featuremasterID;
	}
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
	 * @return the inputDefects
	 */
	public String getInputDefects() {
		return inputDefects;
	}
	/**
	 * @param inputDefects the inputDefects to set
	 */
	public void setInputDefects(String inputDefects) {
		this.inputDefects = inputDefects;
	}
	/**
	 * @return the defectsData
	 */
	public String getDefectsData() {
		return defectsData;
	}
	/**
	 * @param defectsData the defectsData to set
	 */
	public void setDefectsData(String defectsData) {
		this.defectsData = defectsData;
	}
	/**
	 * @return the defectList
	 */
	public List<String> getDefectList() {
		return defectList;
	}
	/**
	 * @param defectList the defectList to set
	 */
	public void setDefectList(List<String> defectList) {
		this.defectList = defectList;
	}
	/**
	 * @return the defects
	 */
	public List<DatasetRunDefect> getDefects() {
		return defects;
	}
	/**
	 * @param defects the defects to set
	 */
	public void setDefects(List<DatasetRunDefect> defects) {
		this.defects = defects;
	}
	/**
	 * @return the featureRuns
	 */
	public List<FeatureRun> getFeatureRuns() {
		return featureRuns;
	}
	/**
	 * @param featureRuns the featureRuns to set
	 */
	public void setFeatureRuns(List<FeatureRun> featureRuns) {
		this.featureRuns = featureRuns;
	}
	/**
	 * @return the featureRunCount
	 */
	public int getFeatureRunCount() {
		return featureRunCount;
	}
	/**
	 * @param featureRunCount the featureRunCount to set
	 */
	public void setFeatureRunCount(int featureRunCount) {
		this.featureRunCount = featureRunCount;
	}
	

}
