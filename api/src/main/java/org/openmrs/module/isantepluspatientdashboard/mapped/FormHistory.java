package org.openmrs.module.isantepluspatientdashboard.mapped;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Encounter;
import org.openmrs.Visit;

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
@Table(name = "isantepluspatientdashboard_form_history")
public class FormHistory extends BaseOpenmrsData implements Serializable {
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
	
	//TODO add formStatus and formLastModification and use form attributes to add form tags to each form to unify iSante forms from all the rest of the forms

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

	@Override
	public Integer getId() {
		return getFormHistoryId();
	}

	@Override
	public void setId(Integer id) {
		setFormHistoryId(id);
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
}
