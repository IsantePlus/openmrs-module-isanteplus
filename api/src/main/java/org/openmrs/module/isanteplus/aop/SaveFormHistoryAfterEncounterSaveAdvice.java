package org.openmrs.module.isanteplus.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

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

			if (encounterJustSaved != null && encounterJustSaved.getForm() != null) {
				IsantePlusService isanteService = Context.getService(IsantePlusService.class);
				FormHistory formHistory = isanteService.createBasicFormHistoryObject(encounterJustSaved, true);
				List<FormHistory> existingFormHistory = Context.getService(IsantePlusService.class)
						.getFormHistoryByEncounterId(encounterJustSaved.getEncounterId());

				if (existingFormHistory.size() > 0) {
					formHistory = existingFormHistory.get(0);
					formHistory.setChangedBy(Context.getAuthenticatedUser());
					formHistory.setDateChanged(new Date());
				}
				isanteService.saveFormHistory(formHistory);
			}
		}
	}
}
