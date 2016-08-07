package org.openmrs.module.isantepluspatientdashboard.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.AgeUnit;
import org.openmrs.module.isantepluspatientdashboard.ChartJSAgeAxis;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GrowthChartsPageController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
		JSONObject patientOpts = new JSONObject();
		JSONObject patientAge = new JSONObject();

		patientAge.put("years", patient.getAge());
		patientAge.put("days",
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInDays(patient));
		patientAge.put("months",
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInMonths(patient));
		patientOpts.put("name", patient.getPersonName().getFullName());
		patientOpts.put("gender", "M".equals(patient.getGender()) ? 2 : ("F".equals(patient.getGender()) ? 1 : null));
		patientOpts.put("age", patientAge);

		model.addAttribute("patientPropts", patientOpts);
		model.addAttribute("chartAxisLabels", setupAxisLabelNames());
		model.addAttribute("patientPlottableData", getPatientPlottableData(patient));
	}

	private JSONObject setupAxisLabelNames() {
		JSONObject chartAxisLabels = new JSONObject();

		chartAxisLabels.put("WTAGEINF_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("WTAGEINF_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.weight"));
		chartAxisLabels.put("LENAGEINF_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("LENAGEINF_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.length"));
		chartAxisLabels.put("WTLENINF_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.length"));
		chartAxisLabels.put("WTLENINF_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.weight"));
		chartAxisLabels.put("HCAGEINF_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("HCAGEINF_y", Context.getMessageSourceService()
				.getMessage("isantepluspatientdashboard.chart.label.headCircumference"));
		chartAxisLabels.put("WTSTAT_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.stature"));
		chartAxisLabels.put("WTSTAT_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.weight"));
		chartAxisLabels.put("WTAGE_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.years"));
		chartAxisLabels.put("WTAGE_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.weight"));
		chartAxisLabels.put("STATAGE_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.years"));
		chartAxisLabels.put("STATAGE_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.stature"));
		chartAxisLabels.put("BMIAGE_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.years"));
		chartAxisLabels.put("BMIAGE_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.bmi"));
		chartAxisLabels.put("BFA_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("BFA_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.bmi"));
		chartAxisLabels.put("HCFA_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("HCFA_y", Context.getMessageSourceService()
				.getMessage("isantepluspatientdashboard.chart.label.headCircumference"));
		chartAxisLabels.put("LHFA_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("LHFA_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.length"));
		chartAxisLabels.put("WFA_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.age.months"));
		chartAxisLabels.put("WFA_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.weight"));
		chartAxisLabels.put("WFL_x",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.length"));
		chartAxisLabels.put("WFL_y",
				Context.getMessageSourceService().getMessage("isantepluspatientdashboard.chart.label.weight"));

		return chartAxisLabels;
	}

	private JSONObject getPatientPlottableData(Patient patient) {
		ChartJSAgeAxis birthTo36Months = new ChartJSAgeAxis(0, 36, 1, AgeUnit.MONTHS);
		ChartJSAgeAxis birthTo24Months = new ChartJSAgeAxis(0, 24, 1, AgeUnit.MONTHS);
		ChartJSAgeAxis twoTo20Years = new ChartJSAgeAxis(2, 20, 1, AgeUnit.YEARS);
		JSONObject patientPlottableData = new JSONObject();

		patientPlottableData.put("wtageinfPatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getWeightsAtGivenPatientAges(patient, birthTo36Months));
		patientPlottableData.put("lenageinfPatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getHeightsAtGivenPatientAges(patient, birthTo36Months));
		patientPlottableData.put("wtagePatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getWeightsAtGivenPatientAges(patient, twoTo20Years));
		patientPlottableData.put("statagePatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getHeightsAtGivenPatientAges(patient, twoTo20Years));
		patientPlottableData.put("whoWeightForAgePatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getWeightsAtGivenPatientAges(patient, birthTo24Months));
		patientPlottableData.put("whoLengthForAgePatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getHeightsAtGivenPatientAges(patient, birthTo24Months));
		patientPlottableData.put("hcageinfPatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getHeadCircumferenceAtGivenPatientAges(patient, birthTo36Months));
		patientPlottableData.put("whoHeadCircumferenceForAgePatient", Context.getService(IsantePlusPatientDashboardService.class)
				.getHeadCircumferenceAtGivenPatientAges(patient, twoTo20Years));

		return patientPlottableData;
	}
}
