package com.ibm.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.ibm.entity.UserDetail;

@SessionScoped
@ManagedBean(name = "loginManagedBean")
public class LoginManagedBean extends CommonFacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(LoginManagedBean.class);
	private boolean loggedIn;

	@ManagedProperty(value = "#{navigationBean}")
	private NavigationBean navigationBean;

	public LoginManagedBean() {

	}

	private String userName;
	private String password;
	private String userRole;

	@SuppressWarnings("unchecked")
	public String checkuserloginWithRole() {
		try {
			List<UserDetail> userDetails = new ArrayList<UserDetail>();
			EntityManager entityManager = getEntitymanagerFromCurrent();
			entityManager.getTransaction().begin();
			userDetails = entityManager
					.createQuery(
							"select ud from UserDetail ud where ud.username=:username")
					.setParameter("username", userName).getResultList();

			entityManager.getTransaction().commit();
			entityManager.close();

			if (userDetails != null && userDetails.size() > 0) {
				userName = userDetails.get(0).getUsername();
				userRole = userDetails.get(0).getAccesslevel();
				loggedIn = true;
				return navigationBean.redirectToWelcome();
			}
			FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			// To to login page
			return navigationBean.toLogin();

		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return null;
	}

	public String logout() throws IOException {
		// Set the paremeter indicating that user is logged in to false
		setLoggedIn(false);
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.invalidateSession();
		// Set logout message
		FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return navigationBean.toLogin();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public NavigationBean getNavigationBean() {
		return navigationBean;
	}

	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}
}
