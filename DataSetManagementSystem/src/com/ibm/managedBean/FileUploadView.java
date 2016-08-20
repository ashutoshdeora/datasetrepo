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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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


@ManagedBean
@RequestScoped
public class FileUploadView  extends CommonFacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(FileUploadView.class);


	public FileUploadView() {

	}

	private UploadedFile file;
	// private Defect defect;

	private List<DatasetMaster> dataSets;
	private List<FeatureMaster> featureMasters;
	private List<AccountMaster> accountMasters;



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

			sheetNumberToRead = 2;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateDataSetList(dataFromExcel);
			
			sheetNumberToRead = 0;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateFeatureBeanList(dataFromExcel);

			// for account data reading and insert
			sheetNumberToRead = 1;
			dataFromExcel = new ArrayList<String[]>();
			dataFromExcel = readSheet(file.getInputstream(), sheetNumberToRead);
			populateCustomerDataList(dataFromExcel);
			insertDataSet(dataSets);
			insertCustomerDataAndRelation(accountMasters);
			insertfeatureData(featureMasters);

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
						;
						break;
					case Cell.CELL_TYPE_BLANK:
						celldataColumns.add(cell.getStringCellValue());
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
			feature.setFeaturetestexecutionphase((columns[9]!= null) ? columns[9]: "No Data");
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

		try{

		// insert account data in accounts table
		EntityManager entityManager = getEntitymanagerFromCurrent();
		entityManager.getTransaction().begin();

		for(AccountMaster data : accountsList){
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
		}catch(Exception exception){
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

}
