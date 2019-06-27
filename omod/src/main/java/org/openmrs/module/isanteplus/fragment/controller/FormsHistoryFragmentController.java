package org.openmrs.module.isanteplus.fragment.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.IsantePlusGlobalProps;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.module.isanteplus.mapped.FormHistory;
import org.openmrs.module.isanteplus.shared.SharedControllerStuff;
import org.openmrs.ui.framework.WebConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class FormsHistoryFragmentController {
	protected final Log log = LogFactory.getLog(getClass());
	SharedControllerStuff shared = new SharedControllerStuff();

	public void controller(PageModel model, @RequestParam("patientId") Patient patient,
			@RequestParam(value = "visitId", required = false) Visit visit,
			@RequestParam(value = "all", required = false) Integer allPatientForms) {
		List<FormHistory> formHistory = null;

		if (patient != null) {
			if (allPatientForms != null && allPatientForms == 1) {
				formHistory = Context.getService(IsantePlusService.class)
						.getAllFormHistory(patient); /*.getAllFormHistoryForAPatient(patient);*/
			} else {
				if (visit != null && patient.getPatientId().equals(visit.getPatient().getPatientId())) {
					formHistory = new IsantePlusGlobalProps().EXCLUDE_DEFAULT_OPENMRSFORMHISTORY
							? Context.getService(IsantePlusService.class)
									.getOnlyIsanteFormHistoriesByVisit(visit)
							: Context.getService(IsantePlusService.class)
									.getAllFormHistory(visit);
				}
			}

			if (formHistory != null)
				Collections.reverse(formHistory);
			else 
				formHistory = new ArrayList<FormHistory>();
			model.addAttribute("allFormHistory", formHistory);
		}

		model.addAttribute("appName", WebConstants.WEBAPP_NAME);
	}

	public void deleteSelectedFormHistory(@RequestParam("selectedFormHistory[]") String[] uuids) {
		if (uuids != null && uuids.length > 0) {
			for (int i = 0; i < uuids.length; i++) {
				FormHistory history = Context.getService(IsantePlusService.class)
						.getFormHistoryByUuid(uuids[i]);

				Context.getService(IsantePlusService.class).deleteFormHistory(history);
			}
		}
	}
}
