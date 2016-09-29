package org.openmrs.module.isanteplus;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;

public class IsantePlusGlobalProps {

	public Concept VIRAL_LOAD_CONCEPT = Context.getConceptService().getConcept(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID));

	/**
	 * CIEL Head Circumference Concept seems to be of id: 5314, see:
	 * http://www.maternalconceptlab.com/search.php?q=id:5314&source=openmrs
	 */
	public Concept HEAD_CIRCUMFERENC_CONCEPT = Context.getConceptService().getConcept(Context.getAdministrationService()
			.getGlobalProperty(ConfigurableGlobalProperties.MIDUPPER_ARM_CIRCUMFERENCE_CONCEPTID));

	public Concept FORMNEEDSREVIEW_CONCEPT = Context.getConceptService().getConcept(Integer.parseInt(Context
			.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.FORMNEEDSREVIEW_CONCEPTID)));
	public Concept FORMSTATUS_CONCEPT = Context.getConceptService().getConcept(Integer.parseInt(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.FORMSTATUS_CONCEPTID)));
	public Concept YES_CONCEPT = Context.getConceptService().getConcept(Integer.parseInt(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.YES_CONCEPTID)));
	public Concept NO_CONCEPT = Context.getConceptService().getConcept(Integer
			.parseInt(Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.NO_CONCEPTID)));
	public Concept COMPLETED_CONCEPT = Context.getConceptService().getConcept(Integer.parseInt(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.COMPLETED_CONCEPTID)));
	public Concept INCOMPLETE_CONCEPT = Context.getConceptService().getConcept(Integer.parseInt(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.INCOMPLETE_CONCEPTID)));

	public boolean EXCLUDE_DEFAULT_OPENMRSFORMHISTORY = "true".equals(Context.getAdministrationService()
			.getGlobalProperty(ConfigurableGlobalProperties.EXCLUDE_DEFAULT_OPENMRS_FORMHISTORY));

}
