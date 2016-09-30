package org.openmrs.module.isanteplus.fragment.controller;

import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.Extension;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.appui.AppUiExtensions;
import org.openmrs.ui.framework.WebConstants;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class IsanteplusHeaderFragmentController {
	// RA-592: don't use PrivilegeConstants.VIEW_LOCATIONS
	private static final String GET_LOCATIONS = "Get Locations";
	private static final String VIEW_LOCATIONS = "View Locations";

	public void controller(@SpringBean AppFrameworkService appFrameworkService, FragmentModel fragmentModel) {
		try {
			Context.addProxyPrivilege(GET_LOCATIONS);
			Context.addProxyPrivilege(VIEW_LOCATIONS);
			fragmentModel.addAttribute("loginLocations", appFrameworkService.getLoginLocations());

			List<Extension> exts = appFrameworkService
					.getExtensionsForCurrentUser(AppUiExtensions.HEADER_CONFIG_EXTENSION);
			Map<String, Object> configSettings = exts.size() > 0 ? exts.get(0).getExtensionParams() : null;
			fragmentModel.addAttribute("configSettings", configSettings);
			List<Extension> userAccountMenuItems = appFrameworkService
					.getExtensionsForCurrentUser(AppUiExtensions.HEADER_USER_ACCOUNT_MENU_ITEMS_EXTENSION);
			fragmentModel.addAttribute("userAccountMenuItems", userAccountMenuItems);
		} finally {
			Context.removeProxyPrivilege(GET_LOCATIONS);
			Context.removeProxyPrivilege(VIEW_LOCATIONS);
		}
		fragmentModel.put("contextPath", WebConstants.CONTEXT_PATH);
	}
}
