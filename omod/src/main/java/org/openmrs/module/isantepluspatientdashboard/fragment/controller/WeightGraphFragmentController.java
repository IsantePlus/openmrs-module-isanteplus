package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.vis.VisLineGraphing;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class WeightGraphFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		JSONArray weights = Context.getService(IsantePlusPatientDashboardService.class).getPatientWeights(patient);
		
		model.addAttribute("items", VisLineGraphing.getWeightsGraphsItems(weights));
		model.addAttribute("options", VisLineGraphing.getOptions(null, null));
	}
}
