package org.openmrs.module.isanteplus.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.patient.PatientDomainWrapper;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.isanteplus.ConfigurableGlobalProperties;
import org.openmrs.module.isanteplus.IsantePlusHtmlForm;
import org.openmrs.ui.framework.annotation.InjectBeans;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.springframework.web.bind.annotation.RequestParam;

public class IsantePlusFormsFragmentController {

	public void controller(FragmentConfiguration config, FragmentModel model,
			@RequestParam("patientId") Patient patient, @InjectBeans PatientDomainWrapper wrapper,
			@SpringBean("adtService") AdtService adtService, UiSessionContext sessionContext,
			@SpringBean("coreResourceFactory") ResourceFactory resourceFactory,
			@SpringBean("htmlFormEntryService") HtmlFormEntryService htmlFormEntryService,
			@SpringBean("formService") FormService formService, HttpServletRequest request,
			@RequestParam(value = "visitId", required = false) Visit visit) {
		VisitDomainWrapper activeVisit = (VisitDomainWrapper) config.getAttribute("activeVisit");
		Location visitLocation = adtService.getLocationThatSupportsVisits(sessionContext.getSessionLocation());
		Integer patientAge = patient.getAge();
		String patientSex = patient.getGender();
		Boolean isActiveVisit = false;

		activeVisit = adtService.getActiveVisit(patient, visitLocation);
		isActiveVisit = activeVisit != null || request.getRequestURL().toString().endsWith("patientDashboard.page")
				? true : false;
		model.put("isActiveVisit", isActiveVisit);

		model.put("showObygnForms", StringUtils.isNotBlank(patientSex) && patientSex.equals("F"));
		if (isActiveVisit) {
			IsantePlusHtmlForm adherence = new IsantePlusHtmlForm("Adh.xml", resourceFactory, formService,
					htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm analyseDeLaboratoire = new IsantePlusHtmlForm("Lab.xml",
					resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm ficheDeConsultationOBGYN = new IsantePlusHtmlForm("OBGYN.xml",
					resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm ficheDePremiereConsultationOBGYN = new IsantePlusHtmlForm(
					"FOBGYN.xml", resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm ficheDeTravailEtDaccouchement = new IsantePlusHtmlForm(
					"Tacc.xml", resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm fichePsychosocialeAdulte = new IsantePlusHtmlForm("PsyA.xml",
					resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm fichePsychosocialePediatrique = new IsantePlusHtmlForm(
					"PsyP.xml", resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm ordonnanceMedicale = new IsantePlusHtmlForm("OrdM.xml", resourceFactory,
					formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm ordonnancepediatrique = new IsantePlusHtmlForm("Ordpd.xml",
					resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm rapportDarretDuProgrammeSoinsEtTraitementVIHOrSIDA = new IsantePlusHtmlForm(
					"ArretVIH.xml", resourceFactory, formService,
					htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm saisiePremiereVisiteAdult = new IsantePlusHtmlForm("PVisitAdult.xml",
					resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm saisiePremiereVisitePediatrique = new IsantePlusHtmlForm(
					"PVisitPed.xml", resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm soinsDeSantePrimaireConsultationPediatrique = new IsantePlusHtmlForm(
					"ConsPed.xml", resourceFactory, formService,
					htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultation = new IsantePlusHtmlForm(
					"PCons.xml", resourceFactory, formService, htmlFormEntryService,
					patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultationPediatrique = new IsantePlusHtmlForm(
					"PConsPed.xml", resourceFactory, formService,
					htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm soinsDeSantePrimaireConsultation = new IsantePlusHtmlForm(
					"Cons.xml", resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm vaccination = new IsantePlusHtmlForm("Vacc.xml", resourceFactory, formService,
					htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm visiteDeSuivi = new IsantePlusHtmlForm("VisitSuivi.xml", resourceFactory, formService,
					htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm visiteDeSuiviPediatrique = new IsantePlusHtmlForm("VisitSuiviPed.xml",
					resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm imagerie = new IsantePlusHtmlForm("Imagerie.xml",
					resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			IsantePlusHtmlForm homeVisit = new IsantePlusHtmlForm("HomeVisit.xml",
					resourceFactory, formService, htmlFormEntryService, patient,
					visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
			List<IsantePlusHtmlForm> primaryCareForms = new ArrayList<IsantePlusHtmlForm>();
			List<IsantePlusHtmlForm> labForms = new ArrayList<IsantePlusHtmlForm>();
			List<IsantePlusHtmlForm> obygnForms = new ArrayList<IsantePlusHtmlForm>();
			List<IsantePlusHtmlForm> hivCareForms = new ArrayList<IsantePlusHtmlForm>();
			List<IsantePlusHtmlForm> psychoSocialForms = new ArrayList<IsantePlusHtmlForm>();
			List<IsantePlusHtmlForm> otherForms = new ArrayList<IsantePlusHtmlForm>();
			Integer adultStartingAge = Integer.parseInt(Context.getAdministrationService()
					.getGlobalProperty(ConfigurableGlobalProperties.ADULTSTARTINGAGE));

			if (patientAge != null && patientAge > adultStartingAge) {
				primaryCareForms.add(soinsDeSantePrimairePremiereConsultation);
				primaryCareForms.add(soinsDeSantePrimaireConsultation);
			}
			if (patientAge != null && patientAge <= adultStartingAge) {
				primaryCareForms.add(soinsDeSantePrimairePremiereConsultationPediatrique);
				primaryCareForms.add(soinsDeSantePrimaireConsultationPediatrique);
			}

			labForms.add(analyseDeLaboratoire);
			if (patientAge != null && patientAge > adultStartingAge)
				labForms.add(ordonnanceMedicale);
			if (patientAge != null && patientAge <= adultStartingAge)
				labForms.add(ordonnancepediatrique);

			if (StringUtils.isNotBlank(patientSex) && patientAge != null && "F".equals(patientSex)) {
				obygnForms.add(ficheDePremiereConsultationOBGYN);
				obygnForms.add(ficheDeConsultationOBGYN);
				/*if (patientAge > adultStartingAge)*/
					obygnForms.add(ficheDeTravailEtDaccouchement);
			}

			if (patientAge != null && patientAge > adultStartingAge) {
				hivCareForms.add(saisiePremiereVisiteAdult);
				hivCareForms.add(visiteDeSuivi);
			}
			if (patientAge != null && patientAge <= adultStartingAge) {
				hivCareForms.add(saisiePremiereVisitePediatrique);
				hivCareForms.add(visiteDeSuiviPediatrique);
			}
			hivCareForms.add(adherence);

			if (patientAge != null && patientAge > adultStartingAge)
				psychoSocialForms.add(fichePsychosocialeAdulte);
			if (patientAge != null && patientAge <= adultStartingAge)
				psychoSocialForms.add(fichePsychosocialePediatrique);

			otherForms.add(vaccination);
			otherForms.add(rapportDarretDuProgrammeSoinsEtTraitementVIHOrSIDA);
			otherForms.add(imagerie);
			otherForms.add(homeVisit);
			
			if(patientAge == null)
			{
				primaryCareForms.add(soinsDeSantePrimairePremiereConsultation);
				primaryCareForms.add(soinsDeSantePrimaireConsultation);
				primaryCareForms.add(soinsDeSantePrimairePremiereConsultationPediatrique);
				primaryCareForms.add(soinsDeSantePrimaireConsultationPediatrique);
				labForms.add(ordonnanceMedicale);
				labForms.add(ordonnancepediatrique);
				hivCareForms.add(saisiePremiereVisiteAdult);
				hivCareForms.add(visiteDeSuivi);
				hivCareForms.add(saisiePremiereVisitePediatrique);
				hivCareForms.add(visiteDeSuiviPediatrique);
				psychoSocialForms.add(fichePsychosocialeAdulte);
				psychoSocialForms.add(fichePsychosocialePediatrique);
				if (StringUtils.isNotBlank(patientSex) && "F".equals(patientSex)) {
					obygnForms.add(ficheDePremiereConsultationOBGYN);
					obygnForms.add(ficheDeConsultationOBGYN);
					obygnForms.add(ficheDeTravailEtDaccouchement);
				}
			}

			model.put("primaryCareForms", primaryCareForms);
			model.put("labForms", labForms);
			model.put("obygnForms", obygnForms);
			model.put("hivCareForms", hivCareForms);
			model.put("psychoSocialForms", psychoSocialForms);
			model.put("otherForms", otherForms);
		}
	}
}
