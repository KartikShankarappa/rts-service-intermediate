package com.dewpoint.rts.model;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static org.hibernate.search.annotations.Index.YES;


/**
 * The persistent class for the candidate database table.
 * 
 */
@Indexed
@Entity
@NamedQuery(name="Candidate.findAll", query="SELECT c FROM Candidate c")
public class Candidate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcandidate;

	@Column(name="bill_rate")
	private BigDecimal billRate;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	@Column(name="client_city")
	private String clientCity;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	@Column(name="client_name")
	private String clientName;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	@Column(name="client_state")
	private String clientState;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	@Column(name="client_zip")
	private String clientZip;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@Column(name="current_job_title")
	private String currentJobTitle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_available")
	private Date dateAvailable;

	private String email;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_job_title")
	private String lastJobTitle;

	@Column(name="last_name")
	private String lastName;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;

	@Column(name="phone_number")
	private String phoneNumber;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	private String skills;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	private String source;

	@Field(index=YES, analyze= Analyze.YES, store= Store.YES)
	private String status;

	public Candidate() {
	}

	public int getIdcandidate() {
		return this.idcandidate;
	}

	public void setIdcandidate(int idcandidate) {
		this.idcandidate = idcandidate;
	}

	public BigDecimal getBillRate() {
		return this.billRate;
	}

	public void setBillRate(BigDecimal billRate) {
		this.billRate = billRate;
	}

	public String getClientCity() {
		return this.clientCity;
	}

	public void setClientCity(String clientCity) {
		this.clientCity = clientCity;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientState() {
		return this.clientState;
	}

	public void setClientState(String clientState) {
		this.clientState = clientState;
	}

	public String getClientZip() {
		return this.clientZip;
	}

	public void setClientZip(String clientZip) {
		this.clientZip = clientZip;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCurrentJobTitle() {
		return this.currentJobTitle;
	}

	public void setCurrentJobTitle(String currentJobTitle) {
		this.currentJobTitle = currentJobTitle;
	}

	public Date getDateAvailable() {
		return this.dateAvailable;
	}

	public void setDateAvailable(Date dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastJobTitle() {
		return this.lastJobTitle;
	}

	public void setLastJobTitle(String lastJobTitle) {
		this.lastJobTitle = lastJobTitle;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}