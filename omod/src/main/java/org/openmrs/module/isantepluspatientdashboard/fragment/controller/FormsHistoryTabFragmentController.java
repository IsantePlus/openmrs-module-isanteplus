package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusGlobalProps;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.mapped.FormHistory;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class FormsHistoryTabFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model, @RequestParam("patientId") Patient patient,
			@RequestParam("visitId") Visit visit) {
		if (patient != null && visit != null && patient.getPatientId().equals(visit.getPatient().getPatientId())) {
			List<FormHistory> formHistory = new IsantePlusGlobalProps().EXCLUDE_DEFAULT_OPENMRSFORMHISTORY
					? Context.getService(IsantePlusPatientDashboardService.class).getOnlyIsanteFormHistories(visit)
					: Context.getService(IsantePlusPatientDashboardService.class).getAllFormHistory(visit);

			Collections.reverse(formHistory);
			model.addAttribute("allFormHistory", formHistory);
		}
	}

	public void deleteSelectedFormHistory(@RequestParam("selectedFormHistory[]") String[] uuids) {
		if (uuids != null && uuids.length > 0) {
			for (int i = 0; i < uuids.length; i++) {
				FormHistory history = Context.getService(IsantePlusPatientDashboardService.class)
						.getFormHistoryByUuid(uuids[i]);

				Context.getService(IsantePlusPatientDashboardService.class).deleteFormHistory(history);
			}
		}
	}
}
