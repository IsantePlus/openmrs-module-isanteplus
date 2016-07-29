package org.openmrs.module.isantepluspatientdashboard.pages.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.module.appframework.context.AppContextModel;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.coreapps.contextmodel.PatientContextModel;
import org.openmrs.module.emrapi.patient.PatientDomainWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.InjectBeans;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

public class GrowthChartsPageController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model, @FragmentParam("patientId") Patient patient,
			@InjectBeans PatientDomainWrapper patientDomainWrapper,
			@SpringBean("coreAppsProperties") CoreAppsProperties coreAppsProperties, UiSessionContext sessionContext) {
		AppContextModel contextModel = sessionContext.generateAppContextModel();

		contextModel.put("patient", new PatientContextModel(patient));
	}
}
