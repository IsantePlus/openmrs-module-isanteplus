package org.openmrs.module.isantepluspatientdashboard;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;

public class IsantePlusConstants {

	public static Concept VIRAL_LOAD_CONCEPT = Context.getConceptService().getConcept(
			Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID));

	/**
	 * CIEL Head Circumference Concept seems to be of id: 5314, see:
	 * http://www.maternalconceptlab.com/search.php?q=id:5314&source=openmrs
	 */
	public static Concept HEAD_CIRCUMFERENC_CONCEPT = Context.getConceptService().getConcept(Context
			.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.HEAD_CIRCUMFERENCE_CONCEPTID));
}
