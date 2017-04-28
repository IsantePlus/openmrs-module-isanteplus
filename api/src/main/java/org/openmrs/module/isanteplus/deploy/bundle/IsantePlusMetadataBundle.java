/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.isanteplus.deploy.bundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.module.coreapps.CoreAppsConstants;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.haiticore.metadata.HaitiAddressBundle;
import org.openmrs.module.haiticore.metadata.HaitiEncounterTypeBundle;
import org.openmrs.module.haiticore.metadata.HaitiPersonAttributeTypeBundle;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.openmrs.module.isanteplus.IsantePlusConstants;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

;

/**
 * Core iSantPlus metadata bundle
 */
@Component
@Requires( {
		HaitiEncounterTypeBundle.class,
		HaitiPersonAttributeTypeBundle.class,
        //HaitiPatientIdentifierTypeBundle.class, -->This is a placeholder
		HaitiAddressBundle.class
} )
public class IsantePlusMetadataBundle extends AbstractMetadataBundle {

	protected Log log = LogFactory.getLog(getClass());

 	public static final class Packages {
		
	}

	public static final String DEFAULT_LOCALE = "fr";
    private static final String REGISTRATION_ENCOUNTER_NAME = "Enregistrement de patient";


	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {

        log.info("Install Metadata Sharing Packages");

        //installMetadataSharingPackage("HUM_Metadata-57.zip", IsantePlusConstants.PIH_REGISTRATION_CONCEPTS_METADATA_PACKAGE_UUID);

		log.info("Setting Global Properties");

		Map<String, String> properties = new LinkedHashMap<String, String>();
		
		// OpenMRS Core
		properties.put(OpenmrsConstants.GLOBAL_PROPERTY_LOCALE_ALLOWED_LIST, "fr, ht, en");
		properties.put(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE, DEFAULT_LOCALE);


		// EMR API
		// extra patient identifiers
		// properties.put(EmrApiConstants.GP_EXTRA_PATIENT_IDENTIFIER_TYPES, HaitiPatientIdentifierTypes.DOSSIER_NUMBER.uuid() + "," + HaitiPatientIdentifierTypes.HIVEMR_V1.uuid());
		// primary identifier type now installed via metadata mappings
		// properties.put(EmrApiConstants.PRIMARY_IDENTIFIER_TYPE, HaitiPatientIdentifierTypes.ZL_EMR_ID.name());

        // Core Apps
		// Adding a default location
        //properties.put(CoreAppsConstants.GP_DEFAULT_PATIENT_IDENTIFIER_LOCATION, MirebalaisLocations.MIREBALAIS_CDI_PARENT.uuid());

        setGlobalProperties(properties);


	}
}