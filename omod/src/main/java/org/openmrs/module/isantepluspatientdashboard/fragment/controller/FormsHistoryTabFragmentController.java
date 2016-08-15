package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.mapped.FormHistory;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class FormsHistoryTabFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model, @RequestParam("patientId") Patient patient,
			@RequestParam("visitId") Visit visit) {
		List<FormHistory> allFormHistory = Context.getService(IsantePlusPatientDashboardService.class)
				.getAllFormHistory();
		model.addAttribute("allFormHistory", allFormHistory);
	}
}
