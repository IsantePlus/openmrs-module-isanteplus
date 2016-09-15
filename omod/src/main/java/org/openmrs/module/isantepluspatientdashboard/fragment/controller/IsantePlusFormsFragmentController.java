package org.openmrs.module.isantepluspatientdashboard.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.FormService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.patient.PatientDomainWrapper;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusHtmlForm;
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

		activeVisit = adtService.getActiveVisit(patient, visitLocation);
		model.put("patientHasAnActiveVisit", activeVisit != null ? true : false);

		if (activeVisit != null) {
			IsantePlusHtmlForm adherence = new IsantePlusHtmlForm("Adherence.xml", resourceFactory, formService,
					htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm analyseDeLaboratoire = new IsantePlusHtmlForm("AnalyseDeLaboratoire.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
			IsantePlusHtmlForm ficheDeConsultationOBGYN = new IsantePlusHtmlForm("FicheDeConsultationOBGYN.xml",
					resourceFactory, formService, htmlFormEntryService, patient, activeVisit.getVisit());
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
			IsantePlusHtmlForm soinsDeSanteSrimaireConsultation = new IsantePlusHtmlForm(
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

			primaryCareForms.add(saisiePremiereVisiteAdult);
			primaryCareForms.add(saisiePremiereVisitePediatrique);
			primaryCareForms.add(fichePsychosocialeAdulte);
			primaryCareForms.add(fichePsychosocialePediatrique);
			labForms.add(analyseDeLaboratoire);
			psychoSocialForms.add(visiteDeSuivi);
			psychoSocialForms.add(visiteDeSuiviPediatrique);
			model.put("primaryCare", primaryCareForms);
			model.put("labForms", labForms);
			model.put("obygnForms", obygnForms);
			model.put("hivCareForms", hivCareForms);
			model.put("psychoSocialForms", psychoSocialForms);
		}
	}
}
