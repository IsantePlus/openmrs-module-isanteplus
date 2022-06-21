package org.openmrs.module.isanteplus.page.controller;

import java.util.List;

import org.json.JSONObject;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.IsantePlusObs;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetAllDiagnosisPageController {
	
	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
		JSONObject patientOpts = new JSONObject();
		patientOpts.put("name", patient.getPersonName().getFullName());
		model.addAttribute("patientPropts", patientOpts);
		List<IsantePlusObs> allDiagnosis = Context.getService(IsantePlusService.class).getAllDiagnosis(patient);
		List<Obs> otherDiagnosis = Context.getService(IsantePlusService.class).getAllDiagnosisByPatient(patient);
		model.addAttribute("allDiagnosis", allDiagnosis);
		model.addAttribute("diagnosisByPatient", otherDiagnosis);
		
	}

}
