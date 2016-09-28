package org.openmrs.module.isantepluspatientdashboard.fragment.controller;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LastDrugsFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		List<Obs> lastDrugs = Context.getService(IsantePlusPatientDashboardService.class).getLastDrugsObsForPatient(patient);

		model.put("lastDrugs", lastDrugs);
	}
}
