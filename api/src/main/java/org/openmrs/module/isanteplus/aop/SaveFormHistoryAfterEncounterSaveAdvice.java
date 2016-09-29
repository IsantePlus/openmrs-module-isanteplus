package org.openmrs.module.isanteplus.aop;

import java.lang.reflect.Method;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.module.isanteplus.mapped.FormHistory;
import org.springframework.aop.AfterReturningAdvice;

/**
 * This Advise allows persisting a form history every time an encounter is saved
 * under/through/with a form, each encounter is saved through a form and
 * logically form history is encounter history
 * 
 * @author k-joseph
 *
 */
public class SaveFormHistoryAfterEncounterSaveAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if (method.getName().equals("saveEncounter")) {
			Encounter encounterJustSaved = (Encounter) returnValue;
			IsantePlusService isanteService = Context
					.getService(IsantePlusService.class);
			FormHistory formHistory = isanteService.createBasicFormHistoryObject(encounterJustSaved, true);

			if (formHistory != null && encounterJustSaved.getForm() != null)
				isanteService.saveFormHistory(formHistory);
		}
	}

}
