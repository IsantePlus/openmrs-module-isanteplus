package org.openmrs.module.isanteplus;

import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.isanteplus.api.IsantePlusService;

public class IsantePlusManager {

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

	private String bmiGraphExtensionId;

	private String bmiGraphExtensionChecked;
	
	private boolean toogleBmiGraphExtension;
	
	private String viralLoadGraphExtensionId;

	private String viralLoadGraphExtensionChecked;
	
	private boolean toogleViralLoadGraphExtension;

	public IsantePlusManager() {
		visitFormHistoryExtensionId = IsantePlusConstants.VISITFORMHISTORY_EXTENSIONPOINT_ID;
		labHistoryExtensionId = IsantePlusConstants.LABHISTORY_EXTENSIONPOINT_ID;
		patientFormHistoryExtensionId = IsantePlusConstants.FORMHISTORY_EXTENSIONPOINT_ID;
		growthChartsExtensionId = IsantePlusConstants.GROWTHCHARTS_EXTENSIONPOINT_ID;
		lastViralLoadTestExtensionId = IsantePlusConstants.LASTVIRALLOADTEST_EXTENSIONPOINT_ID;
		weightsGraphExtensionId = IsantePlusConstants.WEIGHTSGRAPH_EXTENSIONPOINT_ID;
		isantePlusFormsExtensionId = IsantePlusConstants.ISANTEFORMS_EXTENSIONPOINT_ID;
		mostRecentVitalsExtensionId = IsantePlusConstants.MOSTRECENTVITALS_EXTENSIONPOINT_ID;
		drugsHistoryExtensionId = IsantePlusConstants.DRUGSHISTORY_EXTENSIONPOINT_ID;
		bmiGraphExtensionId = IsantePlusConstants.BMIGRAPH_EXTENSIONPOINT_ID;
		viralLoadGraphExtensionId = IsantePlusConstants.VIRALLOADGRAPH_EXTENSIONPOINT_ID;
		toggleVisitFormHistoryExtension = getComponentStateStateEnabled(getVisitFormHistoryExtensionId());
		toggleLabHistoryExtension = getComponentStateStateEnabled((getLabHistoryExtensionId()));
		togglePatientFormHistoryExtension = getComponentStateStateEnabled((getPatientFormHistoryExtensionId()));
		toggleGrowthChartsExtension = getComponentStateStateEnabled(getGrowthChartsExtensionId());
		toggleLastViralLoadTestExtension = getComponentStateStateEnabled(getLastViralLoadTestExtensionId());
		toggleWeightsGraphExtension = getComponentStateStateEnabled(getWeightsGraphExtensionId());
		toggleIsantePlusFormsExtension = getComponentStateStateEnabled(getIsantePlusFormsExtensionId());
		toogleMostRecentVitalsExtension = getComponentStateStateEnabled(getMostRecentVitalsExtensionId());
		toogleDrugsHistoryExtension = getComponentStateStateEnabled(getDrugsHistoryExtensionId());
		toogleBmiGraphExtension = getComponentStateStateEnabled(getBmiGraphExtensionId());
		toogleViralLoadGraphExtension = getComponentStateStateEnabled(getViralLoadGraphExtensionId());
		visitFormHistoryExtensionChecked = getToggleVisitFormHistoryExtension() ? "checked" : "";
		labHistoryExtensionChecked = getToggleLabHistoryExtension() ? "checked" : "";
		patientFormHistoryExtensionChecked = getTogglePatientFormHistoryExtension() ? "checked" : "";
		growthChartsExtensionChecked = getToggleGrowthChartsExtension() ? "checked" : "";
		lastViralLoadTestExtensionChecked = getToggleLastViralLoadTestExtension() ? "checked" : "";
		weightsGraphExtensionChecked = getToggleWeightsGraphExtension() ? "checked" : "";
		isantePlusFormsExtensionChecked = getToggleIsantePlusFormsExtension() ? "checked" : "";
		mostRecentVitalsExtensionChecked = getToogleMostRecentVitalsExtension() ? "checked" : "";
		drugsHistoryExtensionChecked = getToogleDrugsHistoryExtension() ? "checked" : "";
		bmiGraphExtensionChecked = getToogleBmiGraphExtension() ? "checked" : "";
		viralLoadGraphExtensionChecked = getToogleViralLoadGraphExtension() ? "checked" : "";
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
		ComponentState cs = Context.getService(IsantePlusService.class)
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

	public boolean getToogleBmiGraphExtension() {
		return toogleBmiGraphExtension;
	}

	public String getBmiGraphExtensionId() {
		return bmiGraphExtensionId;
	}

	public String getBmiGraphExtensionChecked() {
		return bmiGraphExtensionChecked;
	}
	
	public boolean getToogleViralLoadGraphExtension() {
		return toogleViralLoadGraphExtension;
	}
	
	public String getViralLoadGraphExtensionId() {
		return viralLoadGraphExtensionId;
	}

	public String getViralLoadGraphExtensionChecked() {
		return viralLoadGraphExtensionChecked;
	}
}
