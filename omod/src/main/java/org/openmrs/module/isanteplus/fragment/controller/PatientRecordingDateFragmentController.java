package org.openmrs.module.isanteplus.fragment.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class PatientRecordingDateFragmentController {
	
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		Encounter patientRecordingDate = Context.getService(IsantePlusService.class).getFirstEncounterDate(patient);
		DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		if(patientRecordingDate != null)
		{
			model.put("patientRecordingDate", inputFormat.format(patientRecordingDate.getEncounterDatetime()));
		}
		else{
			model.put("patientRecordingDate", null);
		}
	}

}
