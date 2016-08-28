package org.openmrs.module.isantepluspatientdashboard.page.controller;

import java.util.List;
import org.json.JSONObject;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class LabHistoryPageController {
	
	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
		JSONObject patientOpts = new JSONObject();
		patientOpts.put("name", patient.getPersonName().getFullName());
		model.addAttribute("patientPropts", patientOpts);
		List<Obs> labresult = Context.getService(IsantePlusPatientDashboardService.class).getLabsHistory(patient);
		model.addAttribute("labresult", labresult);
	}

}
