package com.ibm.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ACCOUNTRUN database table.
 * 
 */
@Entity
@Table(name="ACCOUNTRUN")
@NamedQuery(name="AccountRun.findAll", query="SELECT a FROM AccountRun a")
public class AccountRun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=22)
	private long accountrunid;

	@Column(nullable=false, precision=22)
	private BigDecimal datasetrunid;


	
	@Column(nullable=false, precision=22)
	private BigDecimal accountmasterid;
	
	

	public AccountRun() {
	}

	public long getAccountrunid() {
		return this.accountrunid;
	}

	public void setAccountrunid(long accountrunid) {
		this.accountrunid = accountrunid;
	}

	public BigDecimal getDatasetrunid() {
		return this.datasetrunid;
	}

	public void setDatasetrunid(BigDecimal datasetrunid) {
		this.datasetrunid = datasetrunid;
	}

	/**
	 * @return the accountmasterid
	 */
	public BigDecimal getAccountmasterid() {
		return accountmasterid;
	}

	/**
	 * @param accountmasterid the accountmasterid to set
	 */
	public void setAccountmasterid(BigDecimal accountmasterid) {
		this.accountmasterid = accountmasterid;
	}



}