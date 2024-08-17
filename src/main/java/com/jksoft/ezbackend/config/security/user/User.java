package com.jksoft.ezbackend.config.security.user;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jksoft.ezbackend.entities.Company;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "ezb_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Company company;

	private String email;
	private String username;
	private String password;
	private boolean active;
	private boolean deleted;

	private boolean setupUser;
	private boolean instanceAdmin;
	private boolean companyAdmin;

	private UUID activationKey;
	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean isSetupUser() {
		return setupUser;
	}

	public void setSetupUser(boolean setupUser) {
		this.setupUser = setupUser;
	}

	public boolean isInstanceAdmin() {
		return instanceAdmin;
	}

	public void setInstanceAdmin(boolean instanceAdmin) {
		this.instanceAdmin = instanceAdmin;
	}

	public boolean isCompanyAdmin() {
		return companyAdmin;
	}

	public void setCompanyAdmin(boolean companyAdmin) {
		this.companyAdmin = companyAdmin;
	}

	public UUID getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(UUID activationKey) {
		this.activationKey = activationKey;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", company=" + company == null ? "" : company.getName() + ", email=" + email + ", username=" + username + ", password="
				+ "none of your business" + ", active=" + active + ", deleted=" + deleted + ", setupUser=" + setupUser
				+ ", instanceAdmin=" + instanceAdmin + ", companyAdmin=" + companyAdmin + ", activationKey="
				+ activationKey + ", created=" + created + ", updated=" + updated + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id);
	}
}
