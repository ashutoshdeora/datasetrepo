package com.ibm.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.ibm.entity.AccountMaster;
import com.ibm.entity.DatasetMaster;
import com.ibm.entity.FeatureMaster;

@ManagedBean
@ViewScoped
public class DataManagedBean extends CommonFacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(DataManagedBean.class);

	private DatasetMaster masterRecordFromselectedValue;
	private List<AccountMaster> accountmastersList;
	private List<FeatureMaster> featuremastersList;
	private List<DatasetMaster> datasetmastersList;
	private boolean showupdatePanel;

	@PostConstruct
	private void init() {
		setDatasetmastersList(new ArrayList<DatasetMaster>());
		setDatasetmastersList(populateDataSet());
	}

	

	@SuppressWarnings("unchecked")
	private List<DatasetMaster> populateDataSet() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<DatasetMaster> temp = new ArrayList<DatasetMaster>();
		temp = entityManager.createNamedQuery("DatasetMaster.findAll").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		List<DatasetMaster> tempList = new ArrayList<DatasetMaster>();
		for (int i = 1; i < temp.size(); i++) {

			if (temp.get(i).getDatasetid() < 18) {
				tempList.add(temp.get(i));
			}
		}
		return tempList;
	}

	public void populateSelDset(ActionEvent actionEvent) {
		try {
			DatasetMaster bean = (DatasetMaster) actionEvent.getComponent().getAttributes().get("selectedDs");
			masterRecordFromselectedValue = new DatasetMaster();
			masterRecordFromselectedValue = bean;
			showupdatePanel=true;
		} catch (Exception exception) {
			exception.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Application/Data Error", exception.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	
	public void saveDataSet(ActionEvent actionEvent){
		showupdatePanel =false;
	}
	

	/**
	 * @return the accountmastersList
	 */
	public List<AccountMaster> getAccountmastersList() {
		return accountmastersList;
	}

	/**
	 * @param accountmastersList
	 *            the accountmastersList to set
	 */
	public void setAccountmastersList(List<AccountMaster> accountmastersList) {
		this.accountmastersList = accountmastersList;
	}

	/**
	 * @return the featuremastersList
	 */
	public List<FeatureMaster> getFeaturemastersList() {
		return featuremastersList;
	}

	/**
	 * @param featuremastersList
	 *            the featuremastersList to set
	 */
	public void setFeaturemastersList(List<FeatureMaster> featuremastersList) {
		this.featuremastersList = featuremastersList;
	}

	/**
	 * @return the datasetmastersList
	 */
	public List<DatasetMaster> getDatasetmastersList() {
		return datasetmastersList;
	}

	/**
	 * @param datasetmastersList
	 *            the datasetmastersList to set
	 */
	public void setDatasetmastersList(List<DatasetMaster> datasetmastersList) {
		this.datasetmastersList = datasetmastersList;
	}

	/**
	 * @return the masterRecordFromselectedValue
	 */
	public DatasetMaster getMasterRecordFromselectedValue() {
		return masterRecordFromselectedValue;
	}

	/**
	 * @param masterRecordFromselectedValue
	 *            the masterRecordFromselectedValue to set
	 */
	public void setMasterRecordFromselectedValue(DatasetMaster masterRecordFromselectedValue) {
		this.masterRecordFromselectedValue = masterRecordFromselectedValue;
	}



	/**
	 * @return the showupdatePanel
	 */
	public boolean isShowupdatePanel() {
		return showupdatePanel;
	}



	/**
	 * @param showupdatePanel the showupdatePanel to set
	 */
	public void setShowupdatePanel(boolean showupdatePanel) {
		this.showupdatePanel = showupdatePanel;
	}

}
