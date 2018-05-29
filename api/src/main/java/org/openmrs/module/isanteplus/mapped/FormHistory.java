package org.openmrs.module.isanteplus.mapped;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterRole;
import org.openmrs.Obs;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.BaseOpenmrsDataObject;
import org.openmrs.module.isanteplus.IsantePlusGlobalProps;

/**
 * 
 * @author k-joseph
 * 
 *         TODO; Write persistence layer for this class and write a changeset
 *         that goes through the patients data and fills patient's history
 *         <br />
 *         Make sure the persisting API doesn't allow storing a form history for
 *         the same patient visit encounter form but rather updates the existing
 *
 */
@Entity
@Table(name = "isanteplus_form_history")
public class FormHistory extends BaseOpenmrsDataObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "form_history_id", nullable = false)
	private Integer formHistoryId;

	@ManyToOne
	@JoinColumn(name = "visit_id")
	private Visit visit;

	/**
	 * Encounter is the required field here, since a visit can contain a list of
	 * encounters which actually are entered each through a form
	 */
	@ManyToOne
	@JoinColumn(name = "encounter_id", nullable = false)
	private Encounter encounter;

	@Transient
	private String formStatus;

	@Transient
	private String enteredBy;

	@Transient
	private Date date;

	@Transient
	private String provider;

	public FormHistory() {
	}

	public FormHistory(Encounter encounter) {
		setEncounter(encounter);
	}


	public String getProvider(EncounterRole role) {
		
		return encounter.getProvidersByRole(role)!= null
				? encounter.getProvidersByRole(role).iterator().next().getPerson().getGivenName() + " " + encounter.getProvidersByRole(role).iterator().next().getPerson().getFamilyName() : "";
	}

	
	public Date getDate() {
		return encounter.getDateCreated();
	}

	public String getEnteredBy() {
		User entrant = encounter.getChangedBy() != null ? encounter.getChangedBy() : encounter.getCreator();

		return entrant.getPerson() == null || StringUtils.isBlank(entrant.getPerson().getGivenName())
				|| StringUtils.isBlank(entrant.getPerson().getFamilyName())
						? encounter.getForm().getCreator().getPerson().getGivenName() + " "
								+ encounter.getForm().getCreator().getPerson().getFamilyName()
						: entrant.getPerson().getGivenName() + " " + entrant.getPerson().getFamilyName();
	}

	private String generateFormHistoryStatus(Encounter encounter) {
		IsantePlusGlobalProps isantePlusConstants = new IsantePlusGlobalProps();
		String status = "";
		Set<Obs> obs = encounter.getObs();
		Concept formNeedsReview = isantePlusConstants.FORMNEEDSREVIEW_CONCEPT;
		Concept formStatus = isantePlusConstants.FORMSTATUS_CONCEPT;
		Concept yes = isantePlusConstants.YES_CONCEPT;
		Concept completed = isantePlusConstants.COMPLETED_CONCEPT;
		Concept incomplete = isantePlusConstants.INCOMPLETE_CONCEPT;

		if (encounter.getForm() != null && Context.getFormService().getForm(encounter.getForm().getFormId()) != null) {
			for (Obs o : obs) {
				if (formStatus != null && o.getConcept() != null
						&& o.getConcept().getConceptId().equals(formStatus.getConceptId())) {
					if (completed != null && o.getValueCoded() != null
							&& o.getValueCoded().getConceptId().equals(completed.getConceptId())) {
						status += "<b style=\"color:green;\">" + Context.getMessageSourceService()
								.getMessage("isanteplus.formsHistory.formStatus.complete") + "<b/>";
					} else if (incomplete != null && o.getValueCoded() != null
							&& o.getValueCoded().getConceptId().equals(incomplete.getConceptId())) {
						status += "<b style=\"color:red;\">" + Context.getMessageSourceService()
								.getMessage("isanteplus.formsHistory.formStatus.incomplete")+ "<b/>";
					}
				}
				if (formNeedsReview != null && o.getConcept() != null
						&& o.getConcept().getConceptId().equals(formNeedsReview.getConceptId())) {
					if (yes != null && o.getValueCoded() != null
							&& o.getValueCoded().getConceptId().equals(yes.getConceptId())) {
						status += StringUtils.isNotBlank(status)
								? ", " + "<b style=\"color:blue;\">" + Context.getMessageSourceService()
										.getMessage("isanteplus.formsHistory.formStatus.review")
								: Context.getMessageSourceService()
										.getMessage("isanteplus.formsHistory.formStatus.review") + "<b/>";
					}
				}
			}
		} else {
			status = "<b style=\"color:brown;\">" + Context.getMessageSourceService()
					.getMessage("isanteplus.formsHistory.formStatus.deleted") + "<b/>";
		}

		if (StringUtils.isBlank(status)) {
			status = Context.getMessageSourceService()
					.getMessage("isanteplus.formsHistory.formStatus.unkown");
		}

		return status;
	}

	public String getFormStatus() {
		return generateFormHistoryStatus(encounter);
	}

	public Integer getFormHistoryId() {
		return formHistoryId;
	}

	public void setFormHistoryId(Integer formHistoryId) {
		this.formHistoryId = formHistoryId;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
}
