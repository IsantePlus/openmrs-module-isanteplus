package org.openmrs.module.isantepluspatientdashboard.shared;

import org.json.JSONObject;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.page.PageModel;

public class SharedControllerStuff {

	public JSONObject patientPropsInit(PageModel model, Patient patient) {
		JSONObject patientOpts = new JSONObject();
		JSONObject patientAge = new JSONObject();

		patientAge.put("years", patient.getAge());
		patientAge.put("days",
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInDays(patient));
		patientAge.put("months",
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInMonths(patient));
		patientOpts.put("name", patient.getPersonName().getFullName());
		patientOpts.put("gender", "M".equals(patient.getGender()) ? 2 : ("F".equals(patient.getGender()) ? 1 : null));
		patientOpts.put("age", patientAge);

		return patientOpts;
	}

}
