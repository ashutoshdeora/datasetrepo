package com.ibm.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.ibm.entity.UserDetail;
import com.ibm.utils.EncryptDecryptStringWithDES;

@ManagedBean
@ViewScoped
public class UserRegistrationManagedBean extends CommonFacesBean implements Serializable {
	final static Logger logger = Logger.getLogger(UserRegistrationManagedBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	private List<String> currentUserDetails;
	private UserDetail userDetail;
	private String existingUser;
	private boolean edituser;

	@PostConstruct
	private void init() {
		populateData();
	}

	private void populateData() {
		userDetail = new UserDetail();
		EntityManager entityManager = getEntitymanagerFromCurrent();
		currentUserDetails = new ArrayList<String>();
		entityManager.getTransaction().begin();
		currentUserDetails = entityManager.createQuery("select ud.username from UserDetail ud").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void createNewUser() {
		try {
			if (currentUserDetails.contains(userDetail.getUsername())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "User Already present", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				EncryptDecryptStringWithDES des = new EncryptDecryptStringWithDES();
				UserDetail detail = new UserDetail();
				detail = userDetail;
				detail.setPassword(des.encryptData(userDetail.getPassword()));
				detail.setHpqcPassword(des.encryptData(userDetail.getHpqcPassword()));
				detail.setCreatedby(loginManagedBean.getUserName());
				detail.setUpdatedby(loginManagedBean.getUserName());
				detail.setCreationdate(new Timestamp(new Date().getTime()));
				detail.setUpdatedate(new Timestamp(new Date().getTime()));
				EntityManager entityManager = getEntitymanagerFromCurrent();
				entityManager.getTransaction().begin();
				entityManager.persist(detail);
				entityManager.getTransaction().commit();
				entityManager.close();
				populateData();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "User Created", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception exception) {
			logger.error(exception);
			exception.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Application/Data Error", exception.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

	}

	public void updateExistingsUer() {
		try {
			EntityManager entityManager = getEntitymanagerFromCurrent();
			EncryptDecryptStringWithDES des = new EncryptDecryptStringWithDES();
			entityManager.getTransaction().begin();
			UserDetail temp = userDetail;
			temp.setPassword(des.encryptData(userDetail.getPassword()));
			temp.setHpqcPassword(des.encryptData(temp.getHpqcPassword()));
			temp.setUpdatedate(new Timestamp(new Date().getTime()));
			temp.setUpdatedby(loginManagedBean.getUserName());
			entityManager.merge(temp);
			entityManager.getTransaction().commit();
			entityManager.close();
			edituser = false;
		} catch (Exception exception) {
			logger.error(exception);
			exception.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Application/Data Error", exception.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

	}

	public void updateUser() {
		try {
			if (currentUserDetails.contains(existingUser)) {
				EncryptDecryptStringWithDES des = new EncryptDecryptStringWithDES();
				UserDetail temp = new UserDetail();
				EntityManager entityManager = getEntitymanagerFromCurrent();
				entityManager.getTransaction().begin();
				temp = (UserDetail) entityManager.createQuery("select ud from UserDetail ud").getSingleResult();
				entityManager.getTransaction().commit();
				entityManager.close();
				userDetail = temp;
				userDetail.setPassword(des.decryptData(temp.getPassword()));
				userDetail.setHpqcPassword(des.decryptData(temp.getHpqcPassword()));
				edituser = true;
			} else {
				edituser = false;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "User Not Present,Please create new user", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception exception) {
			logger.error(exception);
			exception.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Application/Data Error", exception.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
	}

	/**
	 * @return the userDetail
	 */
	public UserDetail getUserDetail() {
		return userDetail;
	}

	/**
	 * @param userDetail
	 *            the userDetail to set
	 */
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
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

	/**
	 * @return the existingUser
	 */
	public String getExistingUser() {
		return existingUser;
	}

	/**
	 * @param existingUser
	 *            the existingUser to set
	 */
	public void setExistingUser(String existingUser) {
		this.existingUser = existingUser;
	}

	/**
	 * @return the edituser
	 */
	public boolean isEdituser() {
		return edituser;
	}

	/**
	 * @param edituser
	 *            the edituser to set
	 */
	public void setEdituser(boolean edituser) {
		this.edituser = edituser;
	}
}
