package org.openmrs.module.isanteplus.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.ConfigurableGlobalProperties;
import org.openmrs.module.isanteplus.IsantePlusVital;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class IsantePlusMostRecentVitalsFragmentController {
	protected final Log log = LogFactory.getLog(getClass());

	private Double calculateLatestBMI(Patient patient) {
		Obs height = Context.getService(IsantePlusService.class).getLatestHeightForPatient(patient);
		Obs weight = Context.getService(IsantePlusService.class).getLatestWeightForPatient(patient);

		if (height != null && weight != null) {
			if(weight.getValueNumeric() > 0 && height.getValueNumeric() > 0)
			{
				return Context.getService(IsantePlusService.class)
						.roundAbout(weight.getValueNumeric() / (Math.pow(height.getValueNumeric() * 0.01, 2)), 1);
			}
			else
			{
				return null;
			}
		}
		return null;
	}

	/**
	 * TODO the instead of defining units in messages, use concept units using
	 * something like;
	 * Context.getConceptService().getConceptNumeric(concept.getConceptId()).getUnits(
	 * )
	 * 
	 * @param model
	 * @param patient
	 */
	public void controller(FragmentModel model, @RequestParam("patientId") Patient patient) {
		IsantePlusVital height = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.height.label"),
				Context.getService(IsantePlusService.class).getLatestHeightForPatient(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.height.unit"));
		IsantePlusVital weight = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.weight.label"),
				Context.getService(IsantePlusService.class).getLatestWeightForPatient(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.weight.unit"));
		IsantePlusVital temperature = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.temperature.label"),
				Context.getService(IsantePlusService.class).getLatestTemperatureForPatient(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.temperature.unit"));
		IsantePlusVital pulse = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.pulse.label"),
				Context.getService(IsantePlusService.class).getLatestPulseForPatient(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.pulse.unit"));
		IsantePlusVital respiratoryRate = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.respiratoryRate.label"),
				Context.getService(IsantePlusService.class).getLatestRespiratoryRateForPatient(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.respiratoryRate.unit"));
		IsantePlusVital bloodOxygenSaturation = new IsantePlusVital(
				Context.getMessageSourceService()
						.getMessage("isanteplus.vitals.bloodOxygenSaturation.label"),
				Context.getService(IsantePlusService.class)
						.getLatestBloodOxygenSaturationForPatient(patient),
				Context.getMessageSourceService()
						.getMessage("isanteplus.vitals.bloodOxygenSaturation.unit"));
		IsantePlusVital bloodPressure = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.bloodPressure.label"),
				getBloodPressure(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.bloodPressure.unit"));
		List<IsantePlusVital> vitals = new ArrayList<IsantePlusVital>();
		IsantePlusVital bmi = new IsantePlusVital(
				Context.getMessageSourceService().getMessage("isanteplus.vitals.bmi.label"),
				calculateLatestBMI(patient),
				Context.getMessageSourceService().getMessage("isanteplus.vitals.bmi.unit"));
		IsantePlusVital midUpperArmCircumference = new IsantePlusVital(
				Context.getMessageSourceService()
						.getMessage("isanteplus.vitals.midUpperArmCircumference.label"),
				Context.getService(IsantePlusService.class)
						.getLatestMidUpperArmCircumferenceForPatient(patient),
				Context.getMessageSourceService()
						.getMessage("isanteplus.vitals.midUpperArmCircumference.unit"));
		Integer patientAge = patient.getAge();
		Integer enfantEndingAge = Integer.parseInt(
				Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.ADULTSTARTINGAGE));

		vitals.add(height);
		vitals.add(weight);
		vitals.add(bmi);
		vitals.add(temperature);
		vitals.add(pulse);
		vitals.add(respiratoryRate);
		vitals.add(bloodPressure);
		vitals.add(bloodOxygenSaturation);
		if (patientAge != null && patientAge <= enfantEndingAge)
			vitals.add(midUpperArmCircumference);
		model.put("vitals", vitals);
	}

	private String getBloodPressure(Patient patient) {
		String bp = "";
		Obs dBP = Context.getService(IsantePlusService.class)
				.getLatestDiastolicBloodPressureForPatient(patient);
		Obs sBP = Context.getService(IsantePlusService.class)
				.getLatestSystolicBloodPressureForPatient(patient);

		if (dBP != null && sBP != null) {
			bp = String.valueOf(sBP.getValueNumeric()) + "/" + String.valueOf(dBP.getValueNumeric());
		}

		return bp;
	}
}
