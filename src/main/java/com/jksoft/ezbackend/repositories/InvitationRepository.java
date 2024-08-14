package com.jksoft.ezbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jksoft.ezbackend.entities.Invitation;
import java.util.List;
import com.jksoft.ezbackend.entities.Invitation.InvitationType;




public interface InvitationRepository extends JpaRepository<Invitation, Long> {
	List<Invitation> findByInvitationTypeAndInvitationTarget(InvitationType invitationType, Long invitationTarget);
	Invitation findByInvitationKey(String invitationKey);
}
