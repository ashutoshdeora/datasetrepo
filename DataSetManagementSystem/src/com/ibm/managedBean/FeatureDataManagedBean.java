/**
 * 
 */
package com.ibm.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.ibm.entity.DatasetRunDefect;
import com.ibm.entity.FeatureMaster;
import com.ibm.entity.FeatureRun;
import com.ibm.model.FeatureRunModelBean;

/**
 * @author ibm
 * 
 */

@ManagedBean
@ViewScoped
public class FeatureDataManagedBean extends CommonFacesBean implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(FeatureDataManagedBean.class);
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	private int number2 = 0;
	private int number3 = 60;
	private String[] rolloutOption;

	private List<FeatureRunModelBean> featurDataList;

	public FeatureDataManagedBean() {

	}

	@PostConstruct
	public void populateFeatureAllList() {
		rolloutOption = new String[2];
		rolloutOption[0] = "O";
		rolloutOption[1] = "Y";
		try {
			if (loginManagedBean != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				String objectId = context.getExternalContext()
						.getRequestParameterMap().get("selectedFeatureId");
				if (objectId != null) {
					retriveFeatureListFromDBForFeatureId(objectId);
				} else {
					retriveFeatureListFromDB();
				}

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<FeatureRunModelBean> retriveFeatureListFromDB() {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		featurDataList = new ArrayList<FeatureRunModelBean>();
		List<FeatureMaster> list = new ArrayList<FeatureMaster>();
		List<FeatureRun> featureRuns = new ArrayList<FeatureRun>();
		List<FeatureRun> tempRuns = null;
		List<DatasetRunDefect> defects = null;
		List<DatasetRunDefect> defectsTemp = new ArrayList<DatasetRunDefect>();
		list = entityManager.createQuery(" select fm from FeatureMaster fm")
				.getResultList();
		FeatureRunModelBean bean = null;
		for (FeatureMaster master : list) {
			tempRuns = new ArrayList<FeatureRun>();
			bean = new FeatureRunModelBean();

			bean.setFeatureMaster(master);
			featureRuns = entityManager
					.createQuery(
							"select fr from FeatureRun fr where fr.featuremasterid = :featuremasterid")
					.setParameter("featuremasterid",
							BigDecimal.valueOf(master.getFeatureid()))
					.getResultList();
			defectsTemp = new ArrayList<DatasetRunDefect>();
			if (featureRuns != null && featureRuns.size() > 0) {
				for (FeatureRun run : featureRuns) {
					defects = new ArrayList<DatasetRunDefect>();
					defects = entityManager
							.createQuery(
									"select df from DatasetRunDefect df where df.featurerunid = :featurerunid")
							.setParameter("featurerunid",
									BigDecimal.valueOf(run.getFeaturerunid()))
							.getResultList();
					run.setListofDefects(defects);
					tempRuns.add(run);
					defectsTemp.addAll(defects);
				}
			}
			bean.setDefects(defectsTemp);
			bean.setFeatureRunCount(featureRuns.size());
			bean.setFeatureRuns(tempRuns);
			featurDataList.add(bean);
		}
		entityManager.getTransaction().commit();
		entityManager.close();

		return featurDataList;

	}

	@SuppressWarnings("unchecked")
	private List<FeatureRunModelBean> retriveFeatureListFromDBForFeatureId(
			String featureID) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		featurDataList = new ArrayList<FeatureRunModelBean>();
		List<FeatureMaster> list = new ArrayList<FeatureMaster>();
		List<FeatureRun> featureRuns = new ArrayList<FeatureRun>();
		List<DatasetRunDefect> defects = new ArrayList<DatasetRunDefect>();
		List<DatasetRunDefect> defectsTemp = new ArrayList<DatasetRunDefect>();
		list = entityManager
				.createQuery(
						" select fm from FeatureMaster fm where fm.featureset=:featureset")
				.setParameter("featureset", featureID).getResultList();
		FeatureRunModelBean bean = null;
		for (FeatureMaster master : list) {
			bean = new FeatureRunModelBean();
			if (master.getFeatureid() == 9) {
				System.out.println(master.getFeatureid());
			}
			bean.setFeatureMaster(master);
			featureRuns = entityManager
					.createQuery(
							"select fr from FeatureRun fr where fr.featuremasterid = :featuremasterid")
					.setParameter("featuremasterid",
							BigDecimal.valueOf(master.getFeatureid()))
					.getResultList();
			bean.setFeatureRuns(featureRuns);
			defectsTemp = new ArrayList<DatasetRunDefect>();
			if (featureRuns != null && featureRuns.size() > 0) {
				for (FeatureRun run : featureRuns) {
					defects = new ArrayList<DatasetRunDefect>();
					defects = entityManager
							.createQuery(
									"select df from DatasetRunDefect df where df.featurerunid = :featurerunid")
							.setParameter("featurerunid",
									BigDecimal.valueOf(run.getFeaturerunid()))
							.getResultList();
					defectsTemp.addAll(defects);
				}
			}
			bean.setDefects(defectsTemp);
			bean.setFeatureRunCount(featureRuns.size());
			bean.setFeatureRuns(featureRuns);
			featurDataList.add(bean);
		}

		entityManager.getTransaction().commit();
		entityManager.close();

		return featurDataList;

	}

	/**
	 * @return the loginManagedBean
	 */
	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	/**
	 * @param loginManagedBean
	 *            the loginManagedBean to set
	 */
	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	public List<String> getRolloutOption() {
		return Arrays.asList(rolloutOption);
	}

	/**
	 * @return the number2
	 */
	public int getNumber2() {
		return number2;
	}

	/**
	 * @param number2
	 *            the number2 to set
	 */
	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	/**
	 * @return the number3
	 */
	public int getNumber3() {
		return number3;
	}

	/**
	 * @param number3
	 *            the number3 to set
	 */
	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	/**
	 * @return the featurDataList
	 */
	public List<FeatureRunModelBean> getFeaturDataList() {
		return featurDataList;
	}

	/**
	 * @param featurDataList
	 *            the featurDataList to set
	 */
	public void setFeaturDataList(List<FeatureRunModelBean> featurDataList) {
		this.featurDataList = featurDataList;
	}

}
