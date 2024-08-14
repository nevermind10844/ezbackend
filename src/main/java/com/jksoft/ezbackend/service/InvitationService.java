package com.jksoft.ezbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jksoft.ezbackend.entities.Invitation;
import com.jksoft.ezbackend.entities.Invitation.InvitationType;
import com.jksoft.ezbackend.repositories.InvitationRepository;

@Service
public class InvitationService {

	@Autowired
	InvitationRepository invitationRepository;

	public Invitation createInvitation(Invitation invitation) {
		return invitationRepository.save(invitation);
	}

	public Invitation readInvitation(Long id) {
		if (id == null)
			throw new IllegalArgumentException("id must not be null");
		return invitationRepository.getReferenceById(id);
	}
	
	public Invitation readInvitation(String invitationKey) {
		return this.invitationRepository.findByInvitationKey(invitationKey);
	}

	public List<Invitation> listCompanyInvitations(Long companyId) {
		return this.invitationRepository.findByInvitationTypeAndInvitationTarget(InvitationType.ADMIN_INVITATION,
				companyId);
	}

	public List<Invitation> listInvitations() {
		return invitationRepository.findAll();
	}

	public Invitation updateInvitation(Invitation invitation) {
		return invitationRepository.save(invitation);
	}

	public void deleteInvitation(Long id) {
		invitationRepository.deleteById(id);
	}
}
