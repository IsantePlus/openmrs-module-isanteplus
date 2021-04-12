package org.openmrs.module.isanteplus.page.controller;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.WebConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ImmunizationHistoryPageController {
	
	public String get(PageModel model, @RequestParam("patientId") Patient patient, UiUtils ui)
	{
		String encounterUuid = Context.getService(IsantePlusService.class).getEncounterImmunizationUuidByPatient(patient);
		model.addAttribute("patientVaccination", patient);
		model.addAttribute("appName", WebConstants.WEBAPP_NAME);
		model.addAttribute("encounterUuid", encounterUuid);
		if(encounterUuid != null)
			return "redirect:" + ui.pageLink("htmlformentryui", "htmlform/viewEncounterWithHtmlForm", SimpleObject.create("patientId", patient.getUuid(), "encounter", encounterUuid));
		else
			return null;
	}
	
}
