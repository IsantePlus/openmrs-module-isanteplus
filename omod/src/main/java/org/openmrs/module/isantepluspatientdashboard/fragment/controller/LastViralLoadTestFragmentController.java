package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LastViralLoadTestFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		Obs lastViralLoadTest = Context.getService(IsantePlusPatientDashboardService.class)
				.getLastViralLoadTestResultObsForPatient(patient);

		System.out.println(lastViralLoadTest);
		model.put("lastViralLoadTest", lastViralLoadTest);
	}
}
