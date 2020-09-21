package org.openmrs.module.isanteplus.fragment.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class ListAllergyFragmentController {
	
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		List<Obs> allergy = Context.getService(IsantePlusService.class).getAllergiesForPatient(patient);
		if(allergy.size() > 0)
			model.addAttribute("allergy", allergy);
		else
			model.addAttribute("allergy", null);
	}

}
