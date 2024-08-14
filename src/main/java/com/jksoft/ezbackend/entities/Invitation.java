package com.jksoft.ezbackend.entities;

import java.sql.Timestamp;
import java.util.stream.Stream;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "ezb_invitation")
public class Invitation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private Long invitationTarget;
	
	private InvitationType invitationType;
	
	@CreationTimestamp
	private Timestamp created;
	@UpdateTimestamp
	private Timestamp updated;

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

	public Long getInvitationTarget() {
		return invitationTarget;
	}

	public void setInvitationTarget(Long invitationTarget) {
		this.invitationTarget = invitationTarget;
	}

	public InvitationType getInvitationType() {
		return invitationType;
	}

	public void setInvitationType(InvitationType invitationType) {
		this.invitationType = invitationType;
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
		return "Invitation [id=" + id + ", email=" + email + ", invitationTarget=" + invitationTarget + ", created="
				+ created + ", updated=" + updated + "]";
	}

	public enum InvitationType {
		USER_INVITATION(0L, "Benutzereinladung"),
		ADMIN_INVITATION(1L, "Administratoreinladung"),
		INSTANCE_ADMIN_INVITATION(2L, "Instanzadministratoreinladung");

		private Long id;
		private String name;

		private InvitationType(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return this.id;
		}

		public String getName() {
			return this.name;
		}

		public static InvitationType getEnum(String name) {
			return Stream.of(InvitationType.values()).filter(type -> type.getName().equals(name)).findFirst()
					.orElseThrow();
		}

		public static InvitationType getEnum(Long id) {
			return Stream.of(InvitationType.values()).filter(type -> type.getId().equals(id)).findFirst().orElseThrow();
		}
	}
}
