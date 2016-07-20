package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class WeightGraphFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		JSONArray weights = Context.getService(IsantePlusPatientDashboardService.class).getPatientWeights(patient);
		String[] weightsLabels = new String[weights.length()];
		Double[] weightsValues = new Double[weights.length()];

		for (int i = 0; i < weights.length(); i++) {
			weightsLabels[i] = weights.getJSONObject(i).getString("date");
			weightsValues[i] = weights.getJSONObject(i).getDouble("weight");
		}

		model.addAttribute("weightsLabels", weightsLabels);
		model.addAttribute("weightsValues", weightsValues);
	}
}
