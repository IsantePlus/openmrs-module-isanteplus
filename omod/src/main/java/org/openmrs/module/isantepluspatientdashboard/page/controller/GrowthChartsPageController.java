package org.openmrs.module.isantepluspatientdashboard.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GrowthChartsPageController {
	protected final Log log = LogFactory.getLog(getClass());

	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
		JSONObject patientOpts = new JSONObject();
		JSONObject patientAge = new JSONObject();
		JSONObject chartAxisLabels = new JSONObject();

		patientAge.put("years", patient.getAge());
		patientAge.put("days",
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInDays(patient));
		patientAge.put("months",
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInMonths(patient));
		patientOpts.put("name", patient.getPersonName().getFullName());
		patientOpts.put("gender", "M".equals(patient.getGender()) ? 2 : ("F".equals(patient.getGender()) ? 1 : null));
		patientOpts.put("age", patientAge);
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

		model.addAttribute("patientPropts", patientOpts);
		model.addAttribute("chartAxisLabels", chartAxisLabels);
	}
}
