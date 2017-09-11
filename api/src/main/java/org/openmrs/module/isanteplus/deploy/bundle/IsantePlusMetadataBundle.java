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
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.haiticore.metadata.bundles.HaitiAddressBundle;
import org.openmrs.module.haiticore.metadata.bundles.HaitiEncounterTypeBundle;
import org.openmrs.module.haiticore.metadata.bundles.HaitiPersonAttributeTypeBundle;
import org.openmrs.module.haiticore.metadata.bundles.HaitiPatientIdentifierTypeBundle;
import org.openmrs.module.haiticore.metadata.bundles.HaitiSedishMpiPatientIdentifierTypeBundle;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.openmrs.module.metadatamapping.MetadataSet;
import org.openmrs.module.metadatamapping.api.MetadataMappingService;
import org.openmrs.module.isanteplus.IsantePlusConstants;
import org.openmrs.module.isanteplus.ConfigurableGlobalProperties;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

;

/**
 * Core iSantPlus metadata bundle that installs the metadata from the Haiti Core module and sets the global properties
 */
@Component
@Requires( {
		HaitiEncounterTypeBundle.class,
		HaitiPersonAttributeTypeBundle.class,
	    HaitiPatientIdentifierTypeBundle.class,
	    HaitiSedishMpiPatientIdentifierTypeBundle.class,
		HaitiAddressBundle.class
} )
public class IsantePlusMetadataBundle extends AbstractMetadataBundle {

	protected Log log = LogFactory.getLog(getClass());
	
	private MetadataMappingService metadataMappingService;

	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {

		log.info("Setting Global Properties");

		Map<String, String> properties = new LinkedHashMap<String, String>();
		
		// Set Global properties
		// Set the locales that are allowed
		properties.put(OpenmrsConstants.GLOBAL_PROPERTY_LOCALE_ALLOWED_LIST, "fr, ht, en, en_us, en_gb");
		// Set the default locale
		properties.put(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE, IsantePlusConstants.DEFAULT_LOCALE);
		// Set the name layout from "short" to "givenfamily" to only ask for two names
		properties.put(OpenmrsConstants.GLOBAL_PROPERTY_LAYOUT_NAME_FORMAT, "givenfamily");
		
		// Set the isanteplus metadata last updated date
		properties.put(ConfigurableGlobalProperties.METADATA_LAST_UPDATED_DATE, IsantePlusConstants.METADATA_LAST_UPDATED_DATE);
		
		// Set Registration Core global properties for integration with SEDISH MPI demo
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_LOCAL_MPI_IDENTIFIERTYPEMAP_ECID, IsantePlusConstants.HaitiCore_ECID_UUID + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_ECID_UNIVERSAL_IDENTIFIER + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_ECID_UNIVERSAL_IDENTIFIER_TYPE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_LOCAL_MPI_IDENTIFIERTYPEMAP_iSantePlus_Code_National, IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_NATIONAL + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_iSantePlus_Code_National_UNIVERSAL_IDENTIFIER + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_iSantePlus_Code_National_UNIVERSAL_IDENTIFIER_TYPE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_LOCAL_MPI_IDENTIFIERTYPEMAP_iSantePlus_ST_Code, IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_ST + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_iSantePlus_ST_Code_UNIVERSAL_IDENTIFIER + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_iSantePlus_ST_Code_UNIVERSAL_IDENTIFIER_TYPE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_LOCAL_MPI_IDENTIFIERTYPEMAP_iSantePlus_ID, IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_ISANTEPLUS_ID + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_iSantePlus_ID_UNIVERSAL_IDENTIFIER + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_iSantePlus_ID_UNIVERSAL_IDENTIFIER_TYPE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_LOCAL_MPI_IDENTIFIERTYPEMAP_M2Sys_Fingerprint_Registration_ID, IsantePlusConstants.HaitiCore_BIOMETRIC_UUID + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_M2Sys_Fingerprint_Registration_ID_UNIVERSAL_IDENTIFIER + ":" + ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_M2Sys_Fingerprint_Registration_ID_UNIVERSAL_IDENTIFIER_TYPE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PASSWORD, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PASSWORD_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PDQ_ENDPOINT, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PDQ_ENDPOINT_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PDQ_IDENTIFIERTYPEUUIDLIST, IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_ISANTEPLUS_ID + "," + IsantePlusConstants.HaitiCore_ECID_UUID + "," + IsantePlusConstants.HaitiCore_BIOMETRIC_UUID + "," + IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_NATIONAL + "," + IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_ST);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PDQ_PORT, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PDQ_PORT_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PERSONIDENTIFIERTYPEUUID, IsantePlusConstants.HaitiCore_ECID_UUID);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PIX_ENDPOINT, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PIX_ENDPOINT_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PIX_IDENTIFIERTYPEUUIDLIST, IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_ISANTEPLUS_ID + "," + IsantePlusConstants.HaitiCore_ECID_UUID + "," + IsantePlusConstants.HaitiCore_BIOMETRIC_UUID + "," + IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_NATIONAL + "," + IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_ST);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PIX_PORT, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_PIX_PORT_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_RECEIVINGAPPLICATION, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_RECEIVINGAPPLICATION_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_RECEIVINGFACILITY, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_RECEIVINGFACILITY_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_SENDINGAPPLICATION, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_SENDINGAPPLICATION_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_SENDINGFACILITY, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_SENDINGFACILITY_VALUE);
		properties.put(ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_USERNAME, ConfigurableGlobalProperties.REGISTRATIONCORE_MPI_USERNAME_VALUE);

		// Set XDS-sender global properties for integration with the SEDISH SHR demo
		properties.put(ConfigurableGlobalProperties.XDSSENDER_REPOSITORY_ENDPOINT, ConfigurableGlobalProperties.XDSSENDER_REPOSITORY_ENDPOINT_VALUE);
		properties.put(ConfigurableGlobalProperties.XDSSENDER_REPOSITORY_USERNAME, ConfigurableGlobalProperties.XDSSENDER_REPOSITORY_USERNAME_VALUE);
		properties.put(ConfigurableGlobalProperties.XDSSENDER_REPOSITORY_PASSWORD, ConfigurableGlobalProperties.XDSSENDER_REPOSITORY_PASSWORD_VALUE);

        setGlobalProperties(properties);
        
        // Install extraPatientIdentifierTypes as metadatamapping metadata set members
        metadataMappingService = Context.getService(MetadataMappingService.class);

        MetadataSet extraPatientIdTypesSet = metadataMappingService.getMetadataItem(MetadataSet.class, EmrApiConstants.EMR_METADATA_SOURCE_NAME, EmrApiConstants.GP_EXTRA_PATIENT_IDENTIFIER_TYPES);

        PatientIdentifierType patientIdentifierTypeCodeSt = Context.getPatientService().getPatientIdentifierTypeByUuid(IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_ST);
        PatientIdentifierType patientIdentifierTypeCodeNational = Context.getPatientService().getPatientIdentifierTypeByUuid(IsantePlusConstants.PATIENT_IDENTIFIER_TYPE_UUID_CODE_NATIONAL);

        metadataMappingService.saveMetadataSetMember(extraPatientIdTypesSet, patientIdentifierTypeCodeSt);
        metadataMappingService.saveMetadataSetMember(extraPatientIdTypesSet, patientIdentifierTypeCodeNational);

	}
}
