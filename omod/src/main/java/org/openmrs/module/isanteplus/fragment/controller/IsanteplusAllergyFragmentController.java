package org.openmrs.module.isanteplus.fragment.controller;

import java.util.Collections;
import java.util.Comparator;

import org.openmrs.Allergies;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.module.allergyui.extension.html.AllergyComparator;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.util.ByFormattedObjectComparator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;

public class IsanteplusAllergyFragmentController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient, UiUtils ui,
            @SpringBean("patientService") PatientService patientService) {

			Allergies allergies = patientService.getAllergies(patient);
			Comparator comparator = new AllergyComparator(new ByFormattedObjectComparator(ui));
			Collections.sort(allergies, comparator);
			
			model.addAttribute("allergies", allergies);
			
			List<Obs> isanteplusAllergies = Context.getService(IsantePlusService.class).getAllergiesForPatient(patient);
			if(isanteplusAllergies.size() > 0)
				model.addAttribute("allergy", isanteplusAllergies);
			else
				model.addAttribute("allergy", null);
	}

}
