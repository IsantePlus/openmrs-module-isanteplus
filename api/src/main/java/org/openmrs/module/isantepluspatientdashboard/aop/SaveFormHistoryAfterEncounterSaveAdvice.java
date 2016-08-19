package org.openmrs.module.isantepluspatientdashboard.aop;

import java.lang.reflect.Method;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.mapped.FormHistory;
import org.springframework.aop.AfterReturningAdvice;

/**
 * This Advise allows persisting a form history every time an encounter is
 * saved, each encounter is saved through a form and logically form history is
 * encounter history
 * 
 * @author k-joseph
 *
 */
public class SaveFormHistoryAfterEncounterSaveAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if (method.getName().equals("saveEncounter")) {
			Encounter encounterJustSaved = (Encounter) returnValue;
			IsantePlusPatientDashboardService isanteService = Context
					.getService(IsantePlusPatientDashboardService.class);
			FormHistory formHistory = isanteService.createBasicFormHistoryObject(encounterJustSaved, true);

			if (formHistory != null)
				isanteService.saveFormHistory(formHistory);
		}
	}

}
