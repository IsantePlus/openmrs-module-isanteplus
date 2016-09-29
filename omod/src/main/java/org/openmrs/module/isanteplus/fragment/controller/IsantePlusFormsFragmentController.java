package org.openmrs.module.isanteplus.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.Patient;
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
			@SpringBean("formService") FormService formService) {
		VisitDomainWrapper activeVisit = (VisitDomainWrapper) config.getAttribute("activeVisit");
		Location visitLocation = adtService.getLocationThatSupportsVisits(sessionContext.getSessionLocation());
		Integer patientAge = patient.getAge();
		String patientSex = patient.getGender();

		activeVisit = adtService.getActiveVisit(patient, visitLocation);
		model.put("patientHasAnActiveVisit", activeVisit != null ? true : false);
		model.put("showObygnForms", StringUtils.isNotBlank(patientSex) && patientSex.equals("F"));
		if (activeVisit != null) {
			IsantePlusHtmlForm adherence = new IsantePlusHtmlForm("Adherence.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm analyseDeLaboratoire = new IsantePlusHtmlForm("AnalyseDeLaboratoire.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm ficheDeConsultationOBGYN = new IsantePlusHtmlForm("FicheDeConsultationOBGYN.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm ficheDePremiereConsultationOBGYN = new IsantePlusHtmlForm(
					"FicheDePremiereConsultationOBGYN.xml", resourceFactory, formService, htmlFormEntryService, patient,
					activeVisit.getVisit());
			IsantePlusHtmlForm ficheDeTravailEtDaccouchement = new IsantePlusHtmlForm(
					"FicheDeTravailEtDaccouchement.xml", resourceFactory, formService, htmlFormEntryService, patient,
					activeVisit.getVisit());
			IsantePlusHtmlForm fichePsychosocialeAdulte = new IsantePlusHtmlForm("FichePsychosocialeAdulte.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm fichePsychosocialePediatrique = new IsantePlusHtmlForm(
					"FichePsychosocialePediatrique.xml", resourceFactory, formService, htmlFormEntryService, patient,
					activeVisit.getVisit());
			IsantePlusHtmlForm ordonnanceMedicale = new IsantePlusHtmlForm("OrdonnanceMedicale.xml", resourceFactory,
					formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm ordonnancepediatrique = new IsantePlusHtmlForm("Ordonnancepediatrique.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm rapportDarretDuProgrammeSoinsEtTraitementVIHOrSIDA = new IsantePlusHtmlForm(
					"RapportDarretDuProgrammeSoinsEtTraitementVIHOrSIDA.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm saisiePremiereVisiteAdult = new IsantePlusHtmlForm("SaisiePremiereVisiteAdult.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm saisiePremiereVisitePediatrique = new IsantePlusHtmlForm(
					"SaisiePremiereVisitePediatrique.xml", resourceFactory, formService, htmlFormEntryService, patient,
					activeVisit.getVisit());
			IsantePlusHtmlForm soinsDeSantePrimaireConsultationPediatrique = new IsantePlusHtmlForm(
					"SoinsDeSantePrimaireConsultationPediatrique.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultation = new IsantePlusHtmlForm(
					"SoinsDeSantePrimairePremiereConsultation.xml", resourceFactory, formService, htmlFormEntryService,
					patient, activeVisit.getVisit());
			IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultationPediatrique = new IsantePlusHtmlForm(
					"SoinsDeSantePrimairePremiereConsultationPediatrique.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm soinsDeSantePrimaireConsultation = new IsantePlusHtmlForm(
					"SoinsDeSanteSrimaireConsultation.xml", resourceFactory, formService, htmlFormEntryService, patient,
					activeVisit.getVisit());
			IsantePlusHtmlForm vaccination = new IsantePlusHtmlForm("Vaccination.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm visiteDeSuivi = new IsantePlusHtmlForm("VisiteDeSuivi.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm visiteDeSuiviPediatrique = new IsantePlusHtmlForm("VisiteDeSuiviPediatrique.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
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
				if (patientAge > adultStartingAge)
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

			model.put("primaryCareForms", primaryCareForms);
			model.put("labForms", labForms);
			model.put("obygnForms", obygnForms);
			model.put("hivCareForms", hivCareForms);
			model.put("psychoSocialForms", psychoSocialForms);
			model.put("otherForms", otherForms);
		}
	}
}
