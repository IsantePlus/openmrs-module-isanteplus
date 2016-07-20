package org.openmrs.module.isantepluspatientdashboard;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;

public class IsantePlusConstants {

	public static Concept VIRAL_LOAD_CONCEPT = Context.getConceptService().getConcept(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID));
}
