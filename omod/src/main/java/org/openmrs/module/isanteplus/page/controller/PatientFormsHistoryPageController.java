package org.openmrs.module.isanteplus.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.module.isanteplus.shared.SharedControllerStuff;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientFormsHistoryPageController {
	protected final Log log = LogFactory.getLog(getClass());
	SharedControllerStuff shared = new SharedControllerStuff();

	public void controller(PageModel model, @RequestParam("patientId") Patient patient,
			@RequestParam(value = "visitId", required = false) Visit visit,
			@RequestParam(value = "all") Integer allPatientForms) {
		model.addAttribute("patientPropts", shared.patientPropsInit(model, patient));
	}
}
