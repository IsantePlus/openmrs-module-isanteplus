package org.openmrs.module.isanteplus.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.IsantePlusManager;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class IsantePlusManagerFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model) {
		model.addAttribute("manager", new IsantePlusManager());
	}

	public void submitIsantePlusPatientDashboardManagerForm(@RequestParam("extensions") String exts) {
		Context.getService(IsantePlusService.class).updateComponentStates(new JSONObject(exts));
	}
}
