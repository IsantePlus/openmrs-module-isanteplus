package org.openmrs.module.isanteplus.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LastViralLoadTestFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		
		Obs lastViralLoadTest = null;
		
		Obs lastViralLoadQuantitative = Context.getService(IsantePlusService.class)
				.getLastViralLoadTestResultObsForPatient(patient);
		
		
		Obs lastViralLoadQualitative = Context.getService(IsantePlusService.class).getLastViralLoadQualitativeObsForPatient(patient);
		if(lastViralLoadQuantitative != null && lastViralLoadQualitative != null)
		{
			if(lastViralLoadQuantitative.getObsDatetime().after(lastViralLoadQualitative.getObsDatetime()))
			lastViralLoadTest = lastViralLoadQuantitative;
			
			else
			{
					if(lastViralLoadQuantitative.getObsDatetime().equals(lastViralLoadQualitative.getObsDatetime()))
						lastViralLoadTest = lastViralLoadQuantitative;	
					else
						lastViralLoadTest = lastViralLoadQualitative;
				
			}
				
				
		}
		else
		{
			if(lastViralLoadQuantitative != null && lastViralLoadQualitative == null)
				lastViralLoadTest = lastViralLoadQuantitative;
			else
				lastViralLoadTest = lastViralLoadQualitative;	
		}
		
		model.put("lastViralLoadTest", lastViralLoadTest);
	}
}
