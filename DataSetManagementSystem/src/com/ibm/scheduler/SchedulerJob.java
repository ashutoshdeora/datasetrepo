package com.ibm.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.entity.DatasetRun;
import com.ibm.entity.DatasetRunDefect;
import com.ibm.hpqc.RestCallToHPQC;
import com.ibm.managedBean.CommonFacesBean;
import com.ibm.model.DefectBean;

public class SchedulerJob extends CommonFacesBean implements Job {
	final static Logger logger = Logger.getLogger(SchedulerJob.class);
	private static final String DATASETRUNPASS = "Passed";
	private static final String DATASETRUNFAILED = "Failed";
	private static final String READYFORRERUNYES = "YES";
	private static final String READYFORRERUNREADY = "READY";
	private static final String USERNAME = "a099996";
	private static final String PASSWORD = "MYname_3880";

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		updateDBForDefectStatus();

		System.out.println("JSF 2 From SchedulerJob+ Quartz 2 example");
		System.out.println("Time when called " + Calendar.getInstance().getTime());
	}

	@SuppressWarnings("unchecked")
	private void updateDBForDefectStatus() {
		try {
			// query all defects and get the latest
			EntityManager entityManager = getEntitymanagerFromCurrent();
			List<DatasetRunDefect> tempList = new ArrayList<DatasetRunDefect>();
			entityManager.getTransaction().begin();
			tempList = entityManager.createNamedQuery(
					"DatasetRunDefect.findAll").getResultList();
			entityManager.getTransaction().commit();
			ArrayList<String> hpqcDefectList = new ArrayList<String>();
			for (DatasetRunDefect defect : tempList) {
				hpqcDefectList.add(String.valueOf(defect.getHpqcdefectid()));
			}
			// this is the latest defects bean .
			List<DefectBean> beans = null;

			beans = validateWithRest(hpqcDefectList);

			// update all defects in db.
			for (DefectBean defectBean : beans) {
				for (DatasetRunDefect defect : tempList) {
					if (defectBean.getHPQCID().equalsIgnoreCase(
							String.valueOf(defect.getHpqcdefectid()))) {
						defect = new DatasetRunDefect();
						defect.setDefectsevrity(defectBean.getDefectseverity());// severity
						defect.setDefectstatus(defectBean.getDefectstatus());// status
						defect.setHpqcdefectid(BigDecimal.valueOf(Long
								.valueOf(defectBean.getHPQCID())));// id
						defect.setDefectOwner(defectBean.getDefectOwner()); // owner
						defect.setDefectCause(defectBean.getDefectCause());// user-02
						defect.setDefectName(defectBean.getDefectName()); // name
						defect.setDefectPriority(defectBean.getDefectPriority()); // priority
						defect.setDefectCreationDate(defectBean
								.getDefectCreationDate()); // creation-time
						defect.setDefectLastModifies(defectBean
								.getDefectLastModifies()); // last-modified
						defect.setDefectClosingDate(defectBean
								.getDefectClosingDate()); // closing-date
						defect.setDefectRaisedBy(defectBean.getDefectRaisedBy()); // detected-by
						entityManager.getTransaction().begin();
						entityManager.merge(defect);
						entityManager.getTransaction().commit();
					}
				}
			}

			// after insert make a select query to get the latest one
			tempList = new ArrayList<DatasetRunDefect>();
			entityManager.getTransaction().begin();
			tempList = entityManager.createNamedQuery(
					"DatasetRunDefect.findAll").getResultList();

			// now update the datasetrunfor the defects
			List<DatasetRun> datasetRunsList = new ArrayList<DatasetRun>();
			datasetRunsList = entityManager
					.createQuery(
							"select dr from DatasetRun dr where dr.runstatus =:runstatus and dr.readyforrun =:readyforrun ")
					.setParameter("runstatus", DATASETRUNFAILED)
					.setParameter("readyforrun", READYFORRERUNYES)
					.getResultList();
			for (DatasetRun datasetRun : datasetRunsList) {
				ArrayList<String> temp = new ArrayList<String>();
				for (DatasetRunDefect defect : tempList) {
					if (defect.getId().getDatasetrunid() == datasetRun
							.getDatasetrunid()) {
						// defect status
						// Assigned - false
						// Cancelled -true
						// Change Request - true
						// Closed - true
						// Deferred - true
						// Duplicated - true
						// Fixed - false
						// New - false
						// Not a Defect - true
						// Open - false
						// Re-assigned - false
						// Reopen - false
						// Retest - true

						if ("Cancelled".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else if ("Change Request".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else if ("Closed".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else if ("Deferred".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else if ("Duplicated".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else if ("Not a Defect".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else if ("Retest".equalsIgnoreCase(defect
								.getDefectstatus())) {
							temp.add(DATASETRUNPASS);
							break;
						} else {
							temp.add(DATASETRUNFAILED);
							break;
						}

					}
				}
				// list cannot be empty
				if (temp != null && temp.size() > 0) {
					if (temp.contains(DATASETRUNFAILED)) {
						datasetRun.setRunstatus(DATASETRUNFAILED);
					} else {
						datasetRun.setRunstatus(DATASETRUNPASS);
						datasetRun.setReadyforrun(READYFORRERUNREADY);
					}
				}
				entityManager.merge(datasetRun);
			}
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * it will validate defects with HPQC via rest call .
	 * 
	 * @param defList
	 * @return
	 * @throws Exception
	 */
	private List<DefectBean> validateWithRest(ArrayList<String> defList)
			throws Exception {
		RestCallToHPQC callToHPQC = new RestCallToHPQC();
		List<DefectBean> bean = callToHPQC.callRestFromHPQCForDefects(defList,
				USERNAME, PASSWORD);
		return bean;

	}
}
