package org.openmrs.module.isanteplus.fragment.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class NextVisitDateFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		Obs nextVisitDate = Context.getService(IsantePlusService.class).getLatestNextVisitDate(patient);
		DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		if(nextVisitDate != null)
		{
			model.put("nextVisitDate", inputFormat.format(nextVisitDate.getValueDatetime()));
		}
		else{
			model.put("nextVisitDate", null);
		}
	}

}
