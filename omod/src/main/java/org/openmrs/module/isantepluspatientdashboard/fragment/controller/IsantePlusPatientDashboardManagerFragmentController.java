package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusPatientDashboardManager;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class IsantePlusPatientDashboardManagerFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model) {
		model.addAttribute("manager", new IsantePlusPatientDashboardManager());
	}

	public void submitIsantePlusPatientDashboardManagerForm(@RequestParam("extensions") String exts) {
		Context.getService(IsantePlusPatientDashboardService.class).updateComponentStates(new JSONObject(exts));
	}
}
