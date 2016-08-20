package com.ibm.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.ibm.entity.DatasetMaster;
import com.ibm.entity.DatasetRun;
import com.ibm.entity.DatasetRunDefect;

@ManagedBean
@ViewScoped
public class DataSetManagedBean extends CommonFacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(DataSetManagedBean.class);
	private List<DatasetMaster> datasetMastersList;


	@PostConstruct
	private void populateAllDataSet() {

		try {

			FacesContext context = FacesContext.getCurrentInstance();
			String objectId = context.getExternalContext().getRequestParameterMap().get("selectedDataSetLocation");
			if (objectId != null) {
				datasetMastersList = retriveAllDataSetDataForDataSetId(objectId);
			} else {
				datasetMastersList = retriveAllDataSetData();
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	
	@SuppressWarnings("unchecked")
	private List<DatasetMaster> retriveAllDataSetDataForDataSetId(String objectId) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		List<DatasetMaster> temp = entityManager.createQuery("select dm from DatasetMaster dm where dm.datasetid=:datasetid")
				.setParameter("datasetid", Long.valueOf(objectId)).getResultList();
		List<DatasetMaster> tempList = new ArrayList<DatasetMaster>();
		List<DatasetRunDefect> tempdefects = null;
		for (DatasetMaster master : temp) {
			tempdefects = new ArrayList<DatasetRunDefect>();
			for (DatasetRun datasetRun : master.getDatasetruns()) {
				List<DatasetRunDefect> defects = entityManager
						.createNativeQuery("select * from DATASETRUNDEFECT df where df.datasetrunid= " + datasetRun.getDatasetrunid(), DatasetRunDefect.class)
						.getResultList();
				tempdefects.addAll(defects);
			}
			master.setDefects(tempdefects);
			tempList.add(master);

		}

		entityManager.getTransaction().commit();
		entityManager.close();
		return temp;

	}

	@SuppressWarnings("unchecked")
	private List<DatasetMaster> retriveAllDataSetData() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		List<DatasetMaster> temp = entityManager.createNamedQuery("DatasetMaster.findAll").getResultList();
		List<DatasetMaster> tempList = new ArrayList<DatasetMaster>();
		List<DatasetRunDefect> tempdefects = null;
		for (DatasetMaster master : temp) {
			tempdefects = new ArrayList<DatasetRunDefect>();
			for (DatasetRun datasetRun : master.getDatasetruns()) {
				List<DatasetRunDefect> defects = entityManager
						.createNativeQuery("select * from DATASETRUNDEFECT df where df.datasetrunid= " + datasetRun.getDatasetrunid(), DatasetRunDefect.class)
						.getResultList();
				tempdefects.addAll(defects);
			}
			master.setDefects(tempdefects);
			tempList.add(master);

		}

		entityManager.getTransaction().commit();
		entityManager.close();
		return tempList;

	}

	/**
	 * @return the datasetMastersList
	 */
	public List<DatasetMaster> getDatasetMastersList() {
		return datasetMastersList;
	}

	/**
	 * @param datasetMastersList
	 *            the datasetMastersList to set
	 */
	public void setDatasetMastersList(List<DatasetMaster> datasetMastersList) {
		this.datasetMastersList = datasetMastersList;
	}
}
