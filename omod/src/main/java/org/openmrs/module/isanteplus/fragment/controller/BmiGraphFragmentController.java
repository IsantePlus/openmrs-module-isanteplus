package org.openmrs.module.isanteplus.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.module.isanteplus.vis.VisLineGraphing;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class BmiGraphFragmentController {
	
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		JSONArray bmi = Context.getService(IsantePlusService.class).getPatientBmi(patient);
		model.addAttribute("items", VisLineGraphing.getBmisGraphsItems(bmi));
		model.addAttribute("options", VisLineGraphing.getOptions(null, null));
	}

}
