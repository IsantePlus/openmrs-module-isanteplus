package org.openmrs.module.isantepluspatientdashboard;

import org.openmrs.ConceptNumeric;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;

public class IsantePlusObs {
	private Obs obs;

	private ConceptNumeric conceptNumeric;

	public IsantePlusObs(Obs obs) {
		setObs(obs);
		Integer conceptId = getObs().getConcept() != null ? getObs().getConcept().getConceptId() : null;

		if (conceptId != null)
		{
			
			setConceptNumeric(Context.getConceptService().getConceptNumeric(conceptId));
		}
	}

	public ConceptNumeric getConceptNumeric() {
		return conceptNumeric;
	}

	public void setConceptNumeric(ConceptNumeric conceptNumeric) {
		this.conceptNumeric = conceptNumeric;
	}

	public Obs getObs() {
		return obs;
	}

	public void setObs(Obs obs) {
		this.obs = obs;
	}
}
