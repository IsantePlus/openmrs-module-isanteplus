package org.openmrs.module.isantepluspatientdashboard;

import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;

public class IsantePlusPatientDashboardManager {

	private String visitFormHistoryExtensionId;

	private String labHistoryExtensionId;

	private String patientFormHistoryExtensionId;

	private String growthChartsExtensionId;

	private String lastViralLoadTestExtensionId;

	private String weightsGraphExtensionId;

	private boolean toggleVisitFormHistoryExtension;

	private boolean toggleLabHistoryExtension;

	private boolean togglePatientFormHistoryExtension;

	private boolean toggleGrowthChartsExtension;

	private boolean toggleLastViralLoadTestExtension;

	private boolean toggleWeightsGraphExtension;

	private String visitFormHistoryExtensionChecked;

	private String labHistoryExtensionChecked;

	private String patientFormHistoryExtensionChecked;

	private String growthChartsExtensionChecked;

	private String lastViralLoadTestExtensionChecked;

	private String weightsGraphExtensionChecked;

	public IsantePlusPatientDashboardManager() {
		visitFormHistoryExtensionId = IsantePlusPatientDashboardConstants.VISITFORMHISTORY_EXTENSIONPOINT_ID;
		labHistoryExtensionId = IsantePlusPatientDashboardConstants.LABHISTORY_EXTENSIONPOINT_ID;
		patientFormHistoryExtensionId = IsantePlusPatientDashboardConstants.FORMHISTORY_EXTENSIONPOINT_ID;
		growthChartsExtensionId = IsantePlusPatientDashboardConstants.GROWTHCHARTS_EXTENSIONPOINT_ID;
		lastViralLoadTestExtensionId = IsantePlusPatientDashboardConstants.LASTVIRALLOADTEST_EXTENSIONPOINT_ID;
		weightsGraphExtensionId = IsantePlusPatientDashboardConstants.WEIGHTSGRAPH_EXTENSIONPOINT_ID;
		toggleVisitFormHistoryExtension = getComponentStateStateEnabled(getVisitFormHistoryExtensionId());
		toggleLabHistoryExtension = getComponentStateStateEnabled((getLabHistoryExtensionId()));
		togglePatientFormHistoryExtension = getComponentStateStateEnabled((getPatientFormHistoryExtensionId()));
		toggleGrowthChartsExtension = getComponentStateStateEnabled(getGrowthChartsExtensionId());
		toggleLastViralLoadTestExtension = getComponentStateStateEnabled(getLastViralLoadTestExtensionId());
		toggleWeightsGraphExtension = getComponentStateStateEnabled(getWeightsGraphExtensionId());
		visitFormHistoryExtensionChecked = getToggleVisitFormHistoryExtension() ? "checked" : "";
		labHistoryExtensionChecked = getToggleLabHistoryExtension() ? "checked" : "";
		patientFormHistoryExtensionChecked = getTogglePatientFormHistoryExtension() ? "checked" : "";
		growthChartsExtensionChecked = getToggleGrowthChartsExtension() ? "checked" : "";
		lastViralLoadTestExtensionChecked = getToggleLastViralLoadTestExtension() ? "checked" : "";
		weightsGraphExtensionChecked = getToggleWeightsGraphExtension() ? "checked" : "";
	}

	public String getVisitFormHistoryExtensionId() {
		return visitFormHistoryExtensionId;
	}

	public String getLabHistoryExtensionId() {
		return labHistoryExtensionId;
	}

	public String getPatientFormHistoryExtensionId() {
		return patientFormHistoryExtensionId;
	}

	public String getGrowthChartsExtensionId() {
		return growthChartsExtensionId;
	}

	public String getLastViralLoadTestExtensionId() {
		return lastViralLoadTestExtensionId;
	}

	public String getWeightsGraphExtensionId() {
		return weightsGraphExtensionId;
	}

	public boolean getToggleVisitFormHistoryExtension() {
		return toggleVisitFormHistoryExtension;
	}

	public boolean getToggleLabHistoryExtension() {
		return toggleLabHistoryExtension;
	}

	public boolean getTogglePatientFormHistoryExtension() {
		return togglePatientFormHistoryExtension;
	}

	public boolean getToggleGrowthChartsExtension() {
		return toggleGrowthChartsExtension;
	}

	public boolean getToggleLastViralLoadTestExtension() {
		return toggleLastViralLoadTestExtension;
	}

	public boolean getToggleWeightsGraphExtension() {
		return toggleWeightsGraphExtension;
	}

	public String getVisitFormHistoryExtensionChecked() {
		return visitFormHistoryExtensionChecked;
	}

	public String getLabHistoryExtensionChecked() {
		return labHistoryExtensionChecked;
	}

	public String getPatientFormHistoryExtensionChecked() {
		return patientFormHistoryExtensionChecked;
	}

	public String getGrowthChartsExtensionChecked() {
		return growthChartsExtensionChecked;
	}

	public String getLastViralLoadTestExtensionChecked() {
		return lastViralLoadTestExtensionChecked;
	}

	public String getWeightsGraphExtensionChecked() {
		return weightsGraphExtensionChecked;
	}

	private boolean getComponentStateStateEnabled(String extensionId) {
		ComponentState cs = Context.getService(IsantePlusPatientDashboardService.class)
				.getAppframeworkComponentState(extensionId);
		if (cs != null)
			return cs.getEnabled();
		return false;
	}
}
