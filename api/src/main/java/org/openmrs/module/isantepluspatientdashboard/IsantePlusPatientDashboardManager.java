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

	private String isantePlusFormsExtensionId;

	private String mostRecentVitalsExtensionId;

	private boolean toggleVisitFormHistoryExtension;

	private boolean toggleLabHistoryExtension;

	private boolean togglePatientFormHistoryExtension;

	private boolean toggleGrowthChartsExtension;

	private boolean toggleLastViralLoadTestExtension;

	private boolean toggleWeightsGraphExtension;

	private boolean toggleIsantePlusFormsExtension;

	private boolean toogleMostRecentVitalsExtension;

	private String visitFormHistoryExtensionChecked;

	private String labHistoryExtensionChecked;

	private String patientFormHistoryExtensionChecked;

	private String growthChartsExtensionChecked;

	private String lastViralLoadTestExtensionChecked;

	private String weightsGraphExtensionChecked;

	private String isantePlusFormsExtensionChecked;

	private String mostRecentVitalsExtensionChecked;

	private String drugsHistoryExtensionId;

	private String drugsHistoryExtensionChecked;

	public boolean toogleDrugsHistoryExtension;

	public IsantePlusPatientDashboardManager() {
		visitFormHistoryExtensionId = IsantePlusPatientDashboardConstants.VISITFORMHISTORY_EXTENSIONPOINT_ID;
		labHistoryExtensionId = IsantePlusPatientDashboardConstants.LABHISTORY_EXTENSIONPOINT_ID;
		patientFormHistoryExtensionId = IsantePlusPatientDashboardConstants.FORMHISTORY_EXTENSIONPOINT_ID;
		growthChartsExtensionId = IsantePlusPatientDashboardConstants.GROWTHCHARTS_EXTENSIONPOINT_ID;
		lastViralLoadTestExtensionId = IsantePlusPatientDashboardConstants.LASTVIRALLOADTEST_EXTENSIONPOINT_ID;
		weightsGraphExtensionId = IsantePlusPatientDashboardConstants.WEIGHTSGRAPH_EXTENSIONPOINT_ID;
		isantePlusFormsExtensionId = IsantePlusPatientDashboardConstants.ISANTEFORMS_EXTENSIONPOINT_ID;
		mostRecentVitalsExtensionId = IsantePlusPatientDashboardConstants.MOSTRECENTVITALS_EXTENSIONPOINT_ID;
		drugsHistoryExtensionId = IsantePlusPatientDashboardConstants.DRUGSHISTORY_EXTENSIONPOINT_ID;
		toggleVisitFormHistoryExtension = getComponentStateStateEnabled(getVisitFormHistoryExtensionId());
		toggleLabHistoryExtension = getComponentStateStateEnabled((getLabHistoryExtensionId()));
		togglePatientFormHistoryExtension = getComponentStateStateEnabled((getPatientFormHistoryExtensionId()));
		toggleGrowthChartsExtension = getComponentStateStateEnabled(getGrowthChartsExtensionId());
		toggleLastViralLoadTestExtension = getComponentStateStateEnabled(getLastViralLoadTestExtensionId());
		toggleWeightsGraphExtension = getComponentStateStateEnabled(getWeightsGraphExtensionId());
		toggleIsantePlusFormsExtension = getComponentStateStateEnabled(getIsantePlusFormsExtensionId());
		toogleMostRecentVitalsExtension = getComponentStateStateEnabled(getMostRecentVitalsExtensionId());
		toogleDrugsHistoryExtension = getComponentStateStateEnabled(getDrugsHistoryExtensionId());
		visitFormHistoryExtensionChecked = getToggleVisitFormHistoryExtension() ? "checked" : "";
		labHistoryExtensionChecked = getToggleLabHistoryExtension() ? "checked" : "";
		patientFormHistoryExtensionChecked = getTogglePatientFormHistoryExtension() ? "checked" : "";
		growthChartsExtensionChecked = getToggleGrowthChartsExtension() ? "checked" : "";
		lastViralLoadTestExtensionChecked = getToggleLastViralLoadTestExtension() ? "checked" : "";
		weightsGraphExtensionChecked = getToggleWeightsGraphExtension() ? "checked" : "";
		isantePlusFormsExtensionChecked = getToggleIsantePlusFormsExtension() ? "checked" : "";
		mostRecentVitalsExtensionChecked = getToogleMostRecentVitalsExtension() ? "checked" : "";
		drugsHistoryExtensionChecked = getToogleDrugsHistoryExtension() ? "checked" : "";
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

	public String getIsantePlusFormsExtensionId() {
		return isantePlusFormsExtensionId;
	}

	public boolean getToggleIsantePlusFormsExtension() {
		return toggleIsantePlusFormsExtension;
	}

	public String getIsantePlusFormsExtensionChecked() {
		return isantePlusFormsExtensionChecked;
	}

	public String getMostRecentVitalsExtensionId() {
		return mostRecentVitalsExtensionId;
	}

	public boolean getToogleMostRecentVitalsExtension() {
		return toogleMostRecentVitalsExtension;
	}

	public String getMostRecentVitalsExtensionChecked() {
		return mostRecentVitalsExtensionChecked;
	}

	private boolean getComponentStateStateEnabled(String extensionId) {
		ComponentState cs = Context.getService(IsantePlusPatientDashboardService.class)
				.getAppframeworkComponentState(extensionId);
		if (cs != null)
			return cs.getEnabled();
		return false;
	}

	public boolean getToogleDrugsHistoryExtension() {
		return toogleDrugsHistoryExtension;
	}

	public String getDrugsHistoryExtensionId() {
		return drugsHistoryExtensionId;
	}

	public String getDrugsHistoryExtensionChecked() {
		return drugsHistoryExtensionChecked;
	}
}
