package com.ibm.managedBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ibm.entity.AccountMaster;
import com.ibm.entity.DatasetMaster;
import com.ibm.entity.FeatureMaster;
import com.ibm.model.ExcelReadBean;

@ManagedBean
@RequestScoped
public class FileUploadView extends CommonFacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(FileUploadView.class);
	private static final String DUMMYDATASET = "Dummy Data Set";
	private static final String BLANKDATA = "No Data";

	public FileUploadView() {

	}

	private UploadedFile file;
	// private Defect defect;

	private List<DatasetMaster> dataSets;
	private List<FeatureMaster> featureMasters;
	private List<AccountMaster> accountMasters;
	private List<ExcelReadBean> excelReadBeansList;

	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;

	/**
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void fileUploadListener(FileUploadEvent e) {
		try {
			// Get uploaded file from the FileUploadEvent
			this.file = e.getFile();
			// Print out the information of the file
			System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());

			// read full file all at once and prepare all data and insert
			List<String[]> dataFromExcel;
			int sheetNumberToRead = 0;

			// for feature data reading and insert

			// sheetNumberToRead = 2;
			// dataFromExcel = new ArrayList<String[]>();
			// dataFromExcel = readSheet(file.getInputstream(),
			// sheetNumberToRead);
			// populateDataSetList(dataFromExcel);

			sheetNumberToRead = 0;
			dataFromExcel = new ArrayList<String[]>();
			excelReadBeansList = new ArrayList<ExcelReadBean>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			excelReadBeansList = populateEcxelList(dataFromExcel);
			insertAllData(excelReadBeansList);

			// for account data reading and insert
			// sheetNumberToRead = 1;
			// dataFromExcel = new ArrayList<String[]>();
			// dataFromExcel = readSheet(file.getInputstream(),
			// sheetNumberToRead);
			// populateCustomerDataList(dataFromExcel);
			// insertDataSet(dataSets);
			// insertCustomerDataAndRelation(accountMasters);
			// insertfeatureData(featureMasters);

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Step1
	 * 
	 * @param inputStream
	 * @param sheetNumberToRead
	 */
	private List<String[]> readSheet(InputStream inputStream, int sheetNumberToRead) {
		List<String[]> dataArray = null;
		try {

			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			// HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(sheetNumberToRead);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			System.out.println(sheet.getPhysicalNumberOfRows() + "---- no of rows");
			int numberofRows = sheet.getPhysicalNumberOfRows();

			System.out.println(sheet.getRow(0).getLastCellNum() + "----- no of columns");

			int numberofColumns = sheet.getRow(0).getLastCellNum();

			// prepare header
			String[] headers = new String[numberofColumns];
			Iterator<Cell> cellIterators = sheet.getRow(0).cellIterator();
			List<String> cellColumns = new ArrayList<String>();
			while (cellIterators.hasNext()) {
				Cell cell = cellIterators.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue() + "\t\t");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					cellColumns.add(String.valueOf(cell.getNumericCellValue()));
					break;
				case Cell.CELL_TYPE_STRING:
					cellColumns.add(cell.getStringCellValue());
					break;
				}
			}
			headers = cellColumns.toArray(new String[numberofColumns]);
			System.out.println(headers[0].toString());
			// now put body in list of arrays.
			dataArray = new ArrayList<String[]>();
			for (int i = 1; i < numberofRows; i++) {
				Iterator<Cell> cellIteratorsforData = sheet.getRow(i).cellIterator();
				List<String> celldataColumns = new ArrayList<String>();
				String[] data = new String[numberofColumns];
				while (cellIteratorsforData.hasNext()) {
					Cell cell = cellIteratorsforData.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						// System.out.print(cell.getBooleanCellValue() +
						// "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						int value = (int) cell.getNumericCellValue();
						celldataColumns.add(String.valueOf(value));
						break;
					case Cell.CELL_TYPE_STRING:
						celldataColumns.add(cell.getStringCellValue());
						System.out.print(cell.getStringCellValue() + "\t\t");
						break;
					case Cell.CELL_TYPE_BLANK:
						celldataColumns.add(BLANKDATA);
					}
				}
				System.out.println();
				data = celldataColumns.toArray(new String[numberofColumns]);
				// System.out.println(data);
				dataArray.add(data);
			}

			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataArray;
	}

	private void populateDataSetList(List<String[]> dataFromExcel) {
		dataSets = new ArrayList<DatasetMaster>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			// read one row each time
			String[] columns = dataFromExcel.get(i);
			if (columns[0] != null && columns[0].length() > 0) {
				DatasetMaster dataSet = new DatasetMaster();
				dataSet.setDatasetname(columns[1]);
				dataSet.setDatasetid(Long.valueOf(columns[0]));
				dataSets.add(dataSet);
			}
		}

		System.out.println(dataSets.size());
	}

	private List<ExcelReadBean> populateEcxelList(List<String[]> dataFromExcel) {

		List<ExcelReadBean> excelReadBeans = new ArrayList<ExcelReadBean>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			// read one row each time
			String[] columns = dataFromExcel.get(i);
			ExcelReadBean bean = new ExcelReadBean();
			bean.setID(columns[0]);
			bean.setGrouping(columns[1]);
			bean.setTestScenario(columns[2]);
			bean.setTestScenarioDescription(columns[3]);
			bean.setTestScript(columns[4]);
			bean.setComments(columns[5]);
			bean.setClarificationsCommentsDonnaSheet(columns[6]);
			bean.setScriptfromDonnaSheet(columns[7]);
			bean.setFeatureEndToEndTestPhase(columns[8]);
			bean.setSITCycle(columns[9]);
			bean.setSITCycleComments(columns[10]);
			bean.setOwner(columns[11]);
			bean.setDataSetCategory(columns[12]);
			bean.setRollout(columns[13]);
			bean.setTotalScripts(columns[14]);
			bean.setStatus(columns[15]);
			bean.setMT(columns[16]);
			bean.setTSSize(columns[17]);
			bean.setDSSize(columns[18]);
			bean.setBlockers(columns[19]);
			bean.setScore(columns[20]);
			bean.setGroup(columns[21]);
			bean.setIBMAssignedTestWeek(columns[22]);
			bean.setAssignedResource(columns[23]);
			bean.setExecutionResource(columns[24]);
			bean.setExecutionDate(columns[25]);
			bean.setExecutionStatus(columns[26]);
			bean.setDefectAndComment(columns[27]);
			bean.setDatasetSPLocation(columns[28]);
			bean.setAccountID(columns[29]);
			bean.setAccountName(columns[30]);
			bean.setSiteID(columns[31]);
			bean.setSiteName(columns[32]);
			bean.setWorkAroundAdopted(columns[33]);
			bean.setInterscriptSequenceByCustomer(columns[34]);
			bean.setRoute(columns[35]);
			bean.setOwnerOffshore(columns[36]);
			bean.setTesterOffshore(columns[37]);
			bean.setE2EBeExecuted(columns[38]);
			bean.setUpdatedbasedonTS_Tracking(columns[39]);
			bean.setIBMDelivered(columns[40]);
			bean.setCategorizationOfDeficiency(columns[41]);
			bean.setExplanationComments(columns[42]);
			bean.setOnsiteBlocker(columns[43]);
			bean.setScriptUpdatedNumberOfTimes(columns[44]);
			bean.setNationalONLYFeature(columns[45]);
			bean.setCannotbeDetermined(columns[46]);
			bean.setIBMwrittenITscripts(columns[47]);
			bean.setIBMITScriptstatus(columns[48]);
			bean.setIBMITscriptsreviewedbyUNF(columns[49]);
			bean.setIBMITScriptNameFTSelection(columns[50]);
			bean.setNoOwner(columns[51]);
			bean.setAditya(columns[52]);
			bean.setAnirnay(columns[53]);
			bean.setArindam(columns[54]);
			bean.setDebdutta(columns[55]);
			bean.setDipak(columns[56]);
			bean.setMrinal(columns[57]);
			bean.setSaumalya(columns[58]);
			bean.setSubhrashis(columns[59]);
			bean.setNotAvilable(columns[60]);
			excelReadBeans.add(bean);
		}

		return excelReadBeans;
	}

	private void populateFeatureBeanList(List<String[]> dataFromExcel) {
		featureMasters = new ArrayList<FeatureMaster>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			// read one row each time
			String[] columns = dataFromExcel.get(i);
			FeatureMaster feature = new FeatureMaster();
			feature.setFeatureset(columns[0]);
			feature.setFeaturegrouping((columns[1] != null) ? columns[1] : "No Data");
			feature.setFeaturetestscriptname((columns[2] != null) ? columns[2] : "No Data");
			feature.setFeaturetestscriptcomments((columns[3] != null) ? columns[3] : "No Data");
			feature.setFeaturephase((columns[4] != null) ? columns[4] : "No Data");
			feature.setFeatureowner((columns[5] != null) ? columns[5] : "No Data");
			feature.setFeaturedatasetcatagoery((columns[6] != null) ? columns[6] : "No Data");
			feature.setFeaturerollout((columns[7] != null) ? columns[7] : "No Data");
			feature.setFeaturestatus((columns[8] != null) ? columns[8] : "No Data");
			feature.setFeaturetestexecutionphase((columns[9] != null) ? columns[9] : "No Data");
			feature.setOwner((columns[10] != null) ? columns[10] : "No Data");
			feature.setBa((columns[11] != null) ? columns[11] : "No Data");
			feature.setFeatureid(Long.valueOf(columns[13]));
			feature.setDatasetId(columns[12]);
			featureMasters.add(feature);
		}
		System.out.println(featureMasters.size());
	}

	private void populateCustomerDataList(List<String[]> dataFromExcel) {
		accountMasters = new ArrayList<AccountMaster>();
		for (int i = 0; i < dataFromExcel.size(); i++) {
			String[] columns = dataFromExcel.get(i);
			if (columns[1] != null && columns[1].length() > 0) {
				AccountMaster account = new AccountMaster();
				account.setAccountname(columns[1]);
				account.setDatasetId(columns[2]);
				account.setAccountid(Long.valueOf(columns[0]));
				accountMasters.add(account);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void insertAllData(List<ExcelReadBean> list) {
		EntityManager entityManager = getEntitymanagerFromCurrent();
		// insert dataset
		entityManager.getTransaction().begin();
		BigDecimal dslastId = (BigDecimal) entityManager.createNativeQuery("select NVL(MAX(DATASETID), 0) lastId from Datasetmaster").getSingleResult();
		long dsprimaryKey = dslastId.longValue();
		// dummy set for feature without any data set
		DatasetMaster dataSetData = new DatasetMaster();
		dataSetData.setDatasetname(DUMMYDATASET);
		dataSetData.setCreatedby(loginManagedBean.getUserName());
		dataSetData.setUpdatedby(loginManagedBean.getUserName());
		dataSetData.setCreationdate(new Timestamp(new Date().getTime()));
		dataSetData.setUpdatedate(new Timestamp(new Date().getTime()));
		dataSetData.setStatus("ACT");
		dsprimaryKey = dsprimaryKey + 1;
		dataSetData.setDatasetid(dsprimaryKey);

		entityManager.persist(dataSetData);
		entityManager.getTransaction().commit();
		for (ExcelReadBean bean : list) {
			entityManager.getTransaction().begin();
			List<DatasetMaster> masters = entityManager.createQuery("select ds.datasetname from DatasetMaster ds where ds.datasetname=:datasetname")
					.setParameter("datasetname", bean.getDatasetSPLocation()).getResultList();
			entityManager.getTransaction().commit();
			if (masters != null && masters.size() > 0) {
				// don't insert again
			} else {
				if (!BLANKDATA.equalsIgnoreCase(bean.getDatasetSPLocation())) {
					dataSetData = new DatasetMaster();
					dataSetData.setDatasetname(bean.getDatasetSPLocation());
					dataSetData.setCreatedby(loginManagedBean.getUserName());
					dataSetData.setUpdatedby(loginManagedBean.getUserName());
					dataSetData.setCreationdate(new Timestamp(new Date().getTime()));
					dataSetData.setUpdatedate(new Timestamp(new Date().getTime()));
					dataSetData.setStatus("ACT");
					dsprimaryKey = dsprimaryKey + 1;
					dataSetData.setDatasetid(dsprimaryKey);
					entityManager.getTransaction().begin();
					entityManager.persist(dataSetData);
					entityManager.getTransaction().commit();
				}
			}

		}
		// insert feature
		entityManager.getTransaction().begin();
		BigDecimal fslastId = (BigDecimal) entityManager.createNativeQuery("select NVL(MAX(featureid), 0) lastId from FeatureMaster").getSingleResult();
		entityManager.getTransaction().commit();
		long fsprimaryKey = fslastId.longValue();
		for (ExcelReadBean bean : list) {
			entityManager.getTransaction().begin();
			List<FeatureMaster> featureMasters = entityManager.createQuery("select fs from FeatureMaster fs where fs.featureset=:featureset ")
					.setParameter("featureset", bean.getID()).getResultList();
			entityManager.getTransaction().commit();
			if (featureMasters != null && featureMasters.size() > 0) {
				// data present do a merge
				for (FeatureMaster master : featureMasters) {
					if (BLANKDATA.equalsIgnoreCase(bean.getDatasetSPLocation())) {
						entityManager.getTransaction().begin();
						List<DatasetMaster> dmtempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
								.setParameter("datasetname", DUMMYDATASET).getResultList();
						master.setDatasetmasters(dmtempList);
						entityManager.merge(master);
						entityManager.getTransaction().commit();
					} else {
						entityManager.getTransaction().begin();
						List<DatasetMaster> tempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
								.setParameter("datasetname", bean.getDatasetSPLocation()).getResultList();
						master.setDatasetmasters(tempList);
						entityManager.merge(master);
						entityManager.getTransaction().commit();
					}
				}
			} else {
				// new feature data // do insert
				// Feature not having any data set
				// associate with dummy dataset
				if (BLANKDATA.equalsIgnoreCase(bean.getDatasetSPLocation())) {
					entityManager.getTransaction().begin();
					List<DatasetMaster> dmtempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
							.setParameter("datasetname", DUMMYDATASET).getResultList();
					FeatureMaster feature = new FeatureMaster();
					fsprimaryKey = fsprimaryKey + 1;
					feature.setFeatureid(fsprimaryKey);
					feature.setFeatureset(bean.getID());
					feature.setDatasetmasters(dmtempList);
					feature.setCreatedby(loginManagedBean.getUserName());
					feature.setUpdatedby(loginManagedBean.getUserName());
					feature.setCreationdate(new Timestamp(new Date().getTime()));
					feature.setUpdatedate(new Timestamp(new Date().getTime()));
					feature.setStatus("ACT");
					entityManager.persist(feature);
					entityManager.getTransaction().commit();
				} else {
					entityManager.getTransaction().begin();
					List<DatasetMaster> tempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
							.setParameter("datasetname", bean.getDatasetSPLocation()).getResultList();
					FeatureMaster feature = new FeatureMaster();
					fsprimaryKey = fsprimaryKey + 1;
					feature.setFeatureid(fsprimaryKey);
					feature.setFeatureset(bean.getID());
					feature.setDatasetmasters(tempList);
					feature.setCreatedby(loginManagedBean.getUserName());
					feature.setUpdatedby(loginManagedBean.getUserName());
					feature.setCreationdate(new Timestamp(new Date().getTime()));
					feature.setUpdatedate(new Timestamp(new Date().getTime()));
					feature.setStatus("ACT");
					entityManager.persist(feature);
					entityManager.getTransaction().commit();
				}
			}
		}

		// insert account master
		entityManager.getTransaction().begin();
		BigDecimal aslastId = (BigDecimal) entityManager.createNativeQuery("select NVL(MAX(accountid), 0) lastId from AccountMaster").getSingleResult();
		entityManager.getTransaction().commit();
		long asprimaryKey = aslastId.longValue();

		for (ExcelReadBean bean : list) {
			// check for account present as there will be multiple account.
			entityManager.getTransaction().begin();
			List<AccountMaster> data = entityManager.createQuery("select acn from AccountMaster acn where acn.accountname=:accountname")
					.setParameter("accountname", bean.getAccountName()).getResultList();
			entityManager.getTransaction().commit();
			if (data != null && data.size() > 0) {
				// account present
				// merge data
				for (AccountMaster master : data) {
					if (BLANKDATA.equalsIgnoreCase(bean.getDatasetSPLocation())) {
						entityManager.getTransaction().begin();
						List<DatasetMaster> dmtempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
								.setParameter("datasetname", DUMMYDATASET).getResultList();
						master.setDatasetmastersList(dmtempList);
						entityManager.merge(master);
						entityManager.getTransaction().commit();
					} else {
						entityManager.getTransaction().begin();
						List<DatasetMaster> tempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
								.setParameter("datasetname", bean.getDatasetSPLocation()).getResultList();

						master.setDatasetmastersList(tempList);
						entityManager.merge(master);
						entityManager.getTransaction().commit();
					}
				}

			} else {
				// insert data

				if (BLANKDATA.equalsIgnoreCase(bean.getDatasetSPLocation())) {
					entityManager.getTransaction().begin();
					List<DatasetMaster> dmtempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
							.setParameter("datasetname", DUMMYDATASET).getResultList();
					AccountMaster accountMaster = new AccountMaster();
					asprimaryKey = asprimaryKey + 1;
					accountMaster.setAccountid(asprimaryKey);
					accountMaster.setAccountname(bean.getAccountName());
					accountMaster.setCreatedby(loginManagedBean.getUserName());
					accountMaster.setUpdatedby(loginManagedBean.getUserName());
					accountMaster.setCreationdate(new Timestamp(new Date().getTime()));
					accountMaster.setUpdatedate(new Timestamp(new Date().getTime()));
					accountMaster.setStatus("ACT");
					accountMaster.setAccountsetid(BigDecimal.valueOf(accountMaster.getAccountid()));
					accountMaster.setDatasetmastersList(dmtempList);
					entityManager.persist(accountMaster);
					entityManager.getTransaction().commit();

				} else {

					// now query from feature table to get list of features ids
					entityManager.getTransaction().begin();
					List<DatasetMaster> tempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetname=:datasetname ")
							.setParameter("datasetname", bean.getDatasetSPLocation()).getResultList();

					AccountMaster accountMaster = new AccountMaster();
					asprimaryKey = asprimaryKey + 1;
					accountMaster.setAccountid(asprimaryKey);
					accountMaster.setAccountname(bean.getAccountName());
					accountMaster.setCreatedby(loginManagedBean.getUserName());
					accountMaster.setUpdatedby(loginManagedBean.getUserName());
					accountMaster.setCreationdate(new Timestamp(new Date().getTime()));
					accountMaster.setUpdatedate(new Timestamp(new Date().getTime()));
					accountMaster.setStatus("ACT");
					accountMaster.setAccountsetid(BigDecimal.valueOf(accountMaster.getAccountid()));
					accountMaster.setDatasetmastersList(tempList);
					entityManager.persist(accountMaster);
					entityManager.getTransaction().commit();
				}
			}
		}

		entityManager.close();
	}

	@SuppressWarnings("unchecked")
	private void insertDataSet(List<DatasetMaster> dataSetsList) {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		// now query from feature table to get list of features ids
		for (DatasetMaster datasetname : dataSetsList) {
			DatasetMaster dataSetData = new DatasetMaster();
			dataSetData.setDatasetname(datasetname.getDatasetname());
			dataSetData.setCreatedby("user");
			dataSetData.setUpdatedby("user");
			dataSetData.setCreationdate(new Timestamp(new Date().getTime()));
			dataSetData.setUpdatedate(new Timestamp(new Date().getTime()));
			dataSetData.setStatus("ACT");
			dataSetData.setDatasetid(datasetname.getDatasetid());
			entityManager.persist(dataSetData);
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * 
	 * @param featuresList
	 */
	private void insertfeatureData(List<FeatureMaster> featuresList) {

		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();
		for (FeatureMaster feature : featuresList) {

			if (feature.getOwner() != null && feature.getOwner().trim().length() > 0) {

			} else {
				feature.setOwner("NA");
			}

			if (feature.getBa() != null && feature.getBa().trim().length() > 0) {

			} else {
				feature.setBa("NA");
			}

			List<DatasetMaster> tempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetid=:datasetid ")
					.setParameter("datasetid", Long.valueOf(feature.getDatasetId())).getResultList();
			feature.setDatasetmasters(tempList);
			feature.setCreatedby("user");
			feature.setUpdatedby("user");
			feature.setCreationdate(new Timestamp(new Date().getTime()));
			feature.setUpdatedate(new Timestamp(new Date().getTime()));
			feature.setStatus("ACT");
			entityManager.persist(feature);

		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@SuppressWarnings("unchecked")
	private void insertCustomerDataAndRelation(List<AccountMaster> accountsList) {

		try {

			// insert account data in accounts table
			EntityManager entityManager = getEntitymanagerFromCurrent();
			entityManager.getTransaction().begin();

			for (AccountMaster data : accountsList) {
				// now query from feature table to get list of features ids
				List<DatasetMaster> tempList = entityManager.createQuery("select ds from DatasetMaster ds where ds.datasetid=:datasetid ")
						.setParameter("datasetid", Long.valueOf(data.getDatasetId())).getResultList();
				if (tempList != null && tempList.size() > 0) {

					data.setCreatedby("user");
					data.setUpdatedby("user");
					data.setCreationdate(new Timestamp(new Date().getTime()));
					data.setUpdatedate(new Timestamp(new Date().getTime()));
					data.setStatus("ACT");
					data.setAccountsetid(BigDecimal.valueOf(data.getAccountid()));
					data.setDatasetmastersList(tempList);
					entityManager.persist(data);
				}

			}
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	private Map<String, String> createUniqueMap(Map<String, String> tempMap) {
		Map<String, String> map = new HashMap<String, String>();
		map = tempMap;
		map.remove(null);
		System.out.println("Initial Map : " + map);
		for (String s : new ConcurrentHashMap<String, String>(map).keySet()) {
			String value = map.get(s);
			for (Map.Entry<String, String> ss : new ConcurrentHashMap<String, String>(map).entrySet()) {
				if (s != ss.getKey() && value == ss.getValue()) {
					map.remove(ss.getKey());
				}
			}
		}
		System.out.println("Final Map : " + map);
		return map;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the featureMasters
	 */
	public List<FeatureMaster> getFeatureMasters() {
		return featureMasters;
	}

	/**
	 * @param featureMasters
	 *            the featureMasters to set
	 */
	public void setFeatureMasters(List<FeatureMaster> featureMasters) {
		this.featureMasters = featureMasters;
	}

	/**
	 * @return the excelReadBeansList
	 */
	public List<ExcelReadBean> getExcelReadBeansList() {
		return excelReadBeansList;
	}

	/**
	 * @param excelReadBeansList
	 *            the excelReadBeansList to set
	 */
	public void setExcelReadBeansList(List<ExcelReadBean> excelReadBeansList) {
		this.excelReadBeansList = excelReadBeansList;
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

}
