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
package org.openmrs.module.isanteplus;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.FormService;
import org.openmrs.api.PatientService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.emrapi.utils.MetadataUtil;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.htmlformentryui.HtmlFormUtil;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.module.isanteplus.deploy.bundle.IsantePlusMetadataBundle;
import org.openmrs.module.metadatadeploy.api.MetadataDeployService;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.util.PrivilegeConstants;
import org.openmrs.util.RoleConstants;

/**
 * This class contains the logic that is run every time this module is either
 * started or stopped.
 */
public class ISantePlusActivator implements ModuleActivator {

	protected Log log = LogFactory.getLog(getClass());

	/**
	 * @see ModuleActivator#willRefreshContext()
	 */
	public void willRefreshContext() {
		log.info("Refreshing iSantePlus Patient Dashboard Module");
	}

	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	public void contextRefreshed() {
		log.info("iSantePlus Patient Dashboard Module refreshed");
	}

	/**
	 * @see ModuleActivator#willStart()
	 */
	public void willStart() {
		log.info("Starting iSantePlus Patient Dashboard Module");
	}

	/**
	 * @see ModuleActivator#started()
	 */
	public void started() {
		Context.getService(IsantePlusService.class).toggleRecentVitalsSection(
				new IsantePlusManager().getToogleMostRecentVitalsExtension());
		AppFrameworkService appFrameworkService = Context.getService(AppFrameworkService.class);
		PatientService patientService = Context.getPatientService();
		
		try {
			
			//Running some functions that are part of the referencedemodata module activator to remove the dependency on demo data
			log.info("Setting Admin as unknown provider");
			linkAdminAccountToAProviderIfNecessary();
			
			log.info("Creating scheduler user and global properties");
			createSchedulerUserAndGPs();
			
			//End referencedemodata functions
			
			log.info("Updating OpenMRS ID to iSantePlus ID");
			changeOpenmrsIdName(patientService);
			
			log.info("Installing Metadata Packages");
			installMetadataPackages();
			
			log.info("Installing Metadata Bundles");
			installMetadataBundles();
			
			log.info("Installing iSantePlus Forms");
			loadIsantePlusHtmlForms();
			
			//Disable the following apps
			appFrameworkService.disableApp("appointmentschedulingui.homeApp");
			appFrameworkService.disableApp("appointmentschedulingui.schedulingAppointmentApp");
			appFrameworkService.disableApp("appointmentschedulingui.requestAppointmentApp");
			appFrameworkService.disableApp("coreapps.latestObsForConceptList");
			appFrameworkService.disableApp("coreapps.mostRecentVitals");
			appFrameworkService.disableApp("coreapps.obsAcrossEncounters");
			appFrameworkService.disableApp("coreapps.obsGraph");
			appFrameworkService.disableApp("coreapps.relationships");
			appFrameworkService.disableApp("registrationapp.basicRegisterPatient");
			appFrameworkService.disableApp("referenceapplication.personalRelationships");
			appFrameworkService.disableApp("referenceapplication.registrationapp.registerPatient");
			appFrameworkService.disableApp("reportingui.reports");
			
			//Disable the following extensions
			appFrameworkService.disableExtension("appointmentschedulingui.tab");
			appFrameworkService.disableExtension("org.openmrs.module.appointmentschedulingui.firstColumnFragments.patientDashboard.patientAppointments");
			appFrameworkService.disableExtension("chartsearch.chartSearchLink");
			
			// Active Auto close visits after 24 hours
			activeAutoCloseVisits();

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("iSantePlus Module started");
	}

	/**
	 * Load in the iSantePlus htmlforms that are defiend in the IsantePlusConstants.ISANTEPLUS_FORMFILE_NAMES
	 * Uses the HtmlFormEntry module
	 * @throws Exception
	 */
	private void loadIsantePlusHtmlForms() throws Exception {
		try {
			ResourceFactory resourceFactory = ResourceFactory.getInstance();
			FormService formService = Context.getFormService();

			for (String fileName : IsantePlusConstants.ISANTEPLUS_FORMFILE_NAMES) {
				HtmlFormUtil.getHtmlFormFromUiResource(resourceFactory, formService,
						Context.getService(HtmlFormEntryService.class),
						"isanteplus:htmlforms/" + fileName);
			}
		} catch (Exception e) {
			if (ResourceFactory.getInstance().getResourceProviders() == null) {
				log.error("Unable to load HTML forms--this error is expected when running component tests");
			} else {
				throw e;
			}
		}
	}
	
	/**
	 * Install the metadata packages using the MetadataSharing Module
	 * @throws Exception
	 */
	private void installMetadataPackages() throws Exception {

        //We need to first install the iSantePlus concept source metadata bundle
		MetadataUtil.setupSpecificMetadata(getClass().getClassLoader(), "iSantePlus_Concept_Source");
		
		//Then we install the rest of the metadata. We do this because there's an error when mapping concepts to a transient concepts source in the database
		MetadataUtil.setupSpecificMetadata(getClass().getClassLoader(), "iSantePlus_Registration_Concepts");

        Context.flushSession();

    }

	/**
	 * Installs metadataBundles from multiple providers all found in the IsantePlusMetadataBundle.class
	 * Uses the MetadataDeploy Module
	 */
    private void installMetadataBundles() throws Exception{

        MetadataDeployService deployService = Context.getService(MetadataDeployService.class);

        //Deploy metadata bundle if the ConfigurableGlobalProperties.METADATA_LAST_UPDATED_DATE is not equal to IsantePlusConstants.METADATA_LAST_UPDATED_DATE
        String metadataLastUpdatedDate = Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.METADATA_LAST_UPDATED_DATE);
        if (!IsantePlusConstants.METADATA_LAST_UPDATED_DATE.equals(metadataLastUpdatedDate)) {
        	log.info("installing isanteplus metadata bundle");
        	deployService.installBundle(Context.getRegisteredComponents(IsantePlusMetadataBundle.class).get(0));
        } else {
        	log.info("isanteplus metadata bundle not installed");
        }
        

    }
    
    /**
     * Makes the admin a provider of type UNKNOWN provider
     * This is originally from the referencedemodata module activator.
     */
    private void linkAdminAccountToAProviderIfNecessary() throws Exception{

		try {
			//If unknown provider isn't yet linked to admin, then do it
			Context.addProxyPrivilege(PrivilegeConstants.GET_PROVIDERS);
			Context.addProxyPrivilege(PrivilegeConstants.GET_PERSONS);
			Context.addProxyPrivilege(PrivilegeConstants.MANAGE_PROVIDERS);
			ProviderService ps = Context.getProviderService();
			Person adminPerson = Context.getPersonService().getPerson(1);
			Collection<Provider> possibleProvider = ps.getProvidersByPerson(adminPerson);
			if (possibleProvider.size() == 0) {
				List<Provider> providers = ps.getAllProviders(false);

				Provider provider;
				if (providers.size() == 0) {
					provider = new Provider();
					provider.setIdentifier("admin");
				} else {
					provider = providers.get(0);
				}
				provider.setPerson(adminPerson);
				ps.saveProvider(provider);
			}
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.GET_PROVIDERS);
			Context.removeProxyPrivilege(PrivilegeConstants.GET_PERSONS);
			Context.removeProxyPrivilege(PrivilegeConstants.MANAGE_PROVIDERS);
		}
	}

    /**
     * Create a scheduler username and global properties with a random string generator for a password
     */
    private void createSchedulerUserAndGPs() throws Exception{
        UserService us = Context.getUserService();
        
        // Create a random password for the scheduler with 5 upper case, 5 lower case and 5 numbers to suit the OpenMRS password requirements
        char[] possibleCharactersAZ = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).toCharArray();
        char[] possibleCharactersaz = (new String("abcdefghijklmnopqrstuvwxyz")).toCharArray();
        char[] possibleCharacters10 = (new String("0123456789")).toCharArray();
        String SCHEDULER_PASSWORD = RandomStringUtils.random( 5, 0, possibleCharactersAZ.length-1, false, false, possibleCharactersAZ, new SecureRandom() ) +
        		RandomStringUtils.random( 5, 0, possibleCharactersaz.length-1, false, false, possibleCharactersaz, new SecureRandom() ) +
        		RandomStringUtils.random( 5, 0, possibleCharacters10.length-1, false, false, possibleCharacters10, new SecureRandom() );
        
        if (us.getUserByUsername(IsantePlusConstants.SCHEDULER_USERNAME) == null) {
            Person person = Context.getPersonService().getPerson(1);
            //Apparently admin has no name, set it to pass validation
            person.addName(new PersonName("super", null, "user"));
            Role superuserRole = us.getRole(RoleConstants.SUPERUSER);
            setupUser(IsantePlusConstants.SCHEDULER_USER_UUID, IsantePlusConstants.SCHEDULER_USERNAME, person,
            		SCHEDULER_PASSWORD, superuserRole);
            AdministrationService adminService = Context.getAdministrationService();
            GlobalProperty usernameGP = adminService.getGlobalPropertyObject("scheduler.username");
            if (usernameGP == null) {
                usernameGP = new GlobalProperty("scheduler.username", IsantePlusConstants.SCHEDULER_USERNAME);
            } else {
                usernameGP.setPropertyValue(IsantePlusConstants.SCHEDULER_USERNAME);
            }
            adminService.saveGlobalProperty(usernameGP);

            GlobalProperty passwordGP = adminService.getGlobalPropertyObject("scheduler.password");
            if (passwordGP == null) {
                passwordGP = new GlobalProperty("scheduler.password", SCHEDULER_PASSWORD);
            } else {
                passwordGP.setPropertyValue(SCHEDULER_PASSWORD);
            }
            adminService.saveGlobalProperty(passwordGP);
        }
    }

    /**
     * Change the OpenMRD Id name to iSantePlus ID if it has already been loaded. 
     * @param patientService
     */
    public void changeOpenmrsIdName(PatientService patientService) throws Exception{

        PatientIdentifierType openmrsIdType = patientService.getPatientIdentifierTypeByName("OpenMRS ID");
        if (openmrsIdType != null) {
        	openmrsIdType.setName("iSantePlus ID");
        	patientService.savePatientIdentifierType(openmrsIdType);
        }
    }

    /**
     * Setup a user in the system. Currently used for creating the scheduler user
     * @param uuid
     * @param username
     * @param person
     * @param password
     * @param roles
     * @return
     */
    private User setupUser(String uuid, String username, Person person, String password, Role... roles) {
		UserService userService = Context.getUserService();

		User user = userService.getUserByUuid(uuid);
		if (user == null) {
			user = new User();
			user.setUuid(uuid);
			user.setRoles(new HashSet<Role>());
		}
		user.setUsername(username);
		user.setPerson(person);

		user.getRoles().clear();
        for (Role role : roles) {
            // we try to grant some module-defined roles without first verifying those modules/roles exist.
            if (role != null) {
                user.addRole(role);
            }
        }
		user = userService.createUser(user, password);

		return user;
	}
    // Adding visit type Facility Visit and Patient externe in visits.autoCloseVisitType property
    private void activeAutoCloseVisits() throws Exception {
    	
    	String visits = "Facility Visit,Patient externe";
    	String visitsType = Context.getAdministrationService().getGlobalProperty("visits.autoCloseVisitType");
    	if(visitsType != visits)
    	{
    		Context.getAdministrationService().setGlobalProperty("visits.autoCloseVisitType", visits);
    	}
    }
    
	/**
	 * @see ModuleActivator#willStop()
	 */
	public void willStop() {
		Context.getService(IsantePlusService.class).toggleRecentVitalsSection(null);

		log.info("Stopping iSantePlus Patient Dashboard Module");
	}

	/**
	 * @see ModuleActivator#stopped()
	 */
	public void stopped() {
		log.info("iSantePlus Patient Dashboard Module stopped");
	}

}
