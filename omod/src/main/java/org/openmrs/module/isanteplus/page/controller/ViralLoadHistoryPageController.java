package org.openmrs.module.isanteplus.page.controller;

import java.util.List;

import org.json.JSONObject;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.IsantePlusObs;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ViralLoadHistoryPageController {
	
	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
		JSONObject patientOpts = new JSONObject();
		patientOpts.put("name", patient.getPersonName().getFullName());
		model.addAttribute("patientPropts", patientOpts);
		List<IsantePlusObs> labresult = Context.getService(IsantePlusService.class).getViralLoadHistory(patient);
		model.addAttribute("labresult", labresult);
	}

}
