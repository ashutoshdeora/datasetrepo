package com.ibm.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the DATASETMASTER database table.
 * 
 */
@Entity
@Table(name="DATASETMASTER")
@NamedQuery(name="DatasetMaster.findAll", query="SELECT d FROM DatasetMaster d")
public class DatasetMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, precision=22)
	private long datasetid;

	@Column(nullable=false, length=40)
	private String createdby;

	@Column(nullable=false)
	private Timestamp creationdate;

	@Column(nullable=false, length=250)
	private String datasetname;

	@Column(nullable=false, length=20)
	private String status;

	@Column(nullable=false)
	private Timestamp updatedate;

	@Column(nullable=false, length=40)
	private String updatedby;
	
	@Transient
	private String selectedDataSetEvent;
	
	@Transient
	private List<DatasetRunDefect> defects;

	//bi-directional many-to-one association to DatasetAccountHistory
	@OneToMany(mappedBy="datasetmaster")
	private List<DatasetAccountHistory> datasetaccounthistories;

	//bi-directional many-to-one association to DatasetFeatureHistory
	@OneToMany(mappedBy="datasetmaster")
	private List<DatasetFeatureHistory> datasetfeaturehistories;

	//bi-directional many-to-one association to DatasetHistory
	@OneToMany(mappedBy="datasetmaster")
	private List<DatasetHistory> datasethistories;

	//bi-directional many-to-many association to AccountMaster
	@ManyToMany(mappedBy="datasetmastersList")
	private List<AccountMaster> accountmasters;

	//bi-directional many-to-one association to DatasetRun
	@OneToMany(mappedBy="datasetmaster")
	private List<DatasetRun> datasetruns;

	//bi-directional many-to-many association to FeatureMaster
	@ManyToMany(mappedBy="datasetmasters")
	private List<FeatureMaster> featuremasters;

	public DatasetMaster() {
	}

	public DatasetMaster(String selectedValue){
		this.selectedDataSetEvent = selectedValue;
	}
	public long getDatasetid() {
		return this.datasetid;
	}

	public void setDatasetid(long datasetid) {
		this.datasetid = datasetid;
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

	public String getDatasetname() {
		return this.datasetname;
	}

	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
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

	public List<DatasetAccountHistory> getDatasetaccounthistories() {
		return this.datasetaccounthistories;
	}

	public void setDatasetaccounthistories(List<DatasetAccountHistory> datasetaccounthistories) {
		this.datasetaccounthistories = datasetaccounthistories;
	}

	public DatasetAccountHistory addDatasetaccounthistory(DatasetAccountHistory datasetaccounthistory) {
		getDatasetaccounthistories().add(datasetaccounthistory);
		datasetaccounthistory.setDatasetmaster(this);

		return datasetaccounthistory;
	}

	public DatasetAccountHistory removeDatasetaccounthistory(DatasetAccountHistory datasetaccounthistory) {
		getDatasetaccounthistories().remove(datasetaccounthistory);
		datasetaccounthistory.setDatasetmaster(null);

		return datasetaccounthistory;
	}

	public List<DatasetFeatureHistory> getDatasetfeaturehistories() {
		return this.datasetfeaturehistories;
	}

	public void setDatasetfeaturehistories(List<DatasetFeatureHistory> datasetfeaturehistories) {
		this.datasetfeaturehistories = datasetfeaturehistories;
	}

	public DatasetFeatureHistory addDatasetfeaturehistory(DatasetFeatureHistory datasetfeaturehistory) {
		getDatasetfeaturehistories().add(datasetfeaturehistory);
		datasetfeaturehistory.setDatasetmaster(this);

		return datasetfeaturehistory;
	}

	public DatasetFeatureHistory removeDatasetfeaturehistory(DatasetFeatureHistory datasetfeaturehistory) {
		getDatasetfeaturehistories().remove(datasetfeaturehistory);
		datasetfeaturehistory.setDatasetmaster(null);

		return datasetfeaturehistory;
	}

	public List<DatasetHistory> getDatasethistories() {
		return this.datasethistories;
	}

	public void setDatasethistories(List<DatasetHistory> datasethistories) {
		this.datasethistories = datasethistories;
	}

	public DatasetHistory addDatasethistory(DatasetHistory datasethistory) {
		getDatasethistories().add(datasethistory);
		datasethistory.setDatasetmaster(this);

		return datasethistory;
	}

	public DatasetHistory removeDatasethistory(DatasetHistory datasethistory) {
		getDatasethistories().remove(datasethistory);
		datasethistory.setDatasetmaster(null);

		return datasethistory;
	}

	public List<AccountMaster> getAccountmasters() {
		return this.accountmasters;
	}

	public void setAccountmasters(List<AccountMaster> accountmasters) {
		this.accountmasters = accountmasters;
	}

	public List<DatasetRun> getDatasetruns() {
		return this.datasetruns;
	}

	public void setDatasetruns(List<DatasetRun> datasetruns) {
		this.datasetruns = datasetruns;
	}

	public DatasetRun addDatasetrun(DatasetRun datasetrun) {
		getDatasetruns().add(datasetrun);
		datasetrun.setDatasetmaster(this);

		return datasetrun;
	}

	public DatasetRun removeDatasetrun(DatasetRun datasetrun) {
		getDatasetruns().remove(datasetrun);
		datasetrun.setDatasetmaster(null);

		return datasetrun;
	}

	public List<FeatureMaster> getFeaturemasters() {
		return this.featuremasters;
	}

	public void setFeaturemasters(List<FeatureMaster> featuremasters) {
		this.featuremasters = featuremasters;
	}

	/**
	 * @return the selectedDataSetEvent
	 */
	public String getSelectedDataSetEvent() {
		return selectedDataSetEvent;
	}

	/**
	 * @param selectedDataSetEvent the selectedDataSetEvent to set
	 */
	public void setSelectedDataSetEvent(String selectedDataSetEvent) {
		this.selectedDataSetEvent = selectedDataSetEvent;
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

}