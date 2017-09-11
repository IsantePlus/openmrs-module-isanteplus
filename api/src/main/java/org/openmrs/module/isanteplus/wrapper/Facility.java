
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

package org.openmrs.module.isanteplus.wrapper;

import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.module.isanteplus.deploy.bundle.LocationBundle;

/**
 * A facility wrapper for a location
 */
public class Facility extends AbstractCustomizableWrapper<Location, LocationAttribute> {

	/**
	 * Creates a new facility wrapper for a location
	 * @param location the location
	 */
	public Facility(Location location) {
		super(location);
	}

	/**
	 * Gets the country
	 * @return the country
	 */
	public String getCountry() {
		return target.getCountry();
	}

	/**
	 * Gets the CityVillage
	 * @return the CityVillage
	 */
	public String getCityVillage() {
		return target.getCityVillage();
	}
	/**
	 * Gets the province
	 * @return the province
	 */
	public String getStateProvince() {
		return target.getStateProvince();
	}
	/**
	 * Gets The name of the faciliy
	 */
	public String getName() {
		return target.getName();
	}
	/**
	 * Gets the Latitude of the facility
	 */
	public String getLatitude() {
		return target.getLatitude();
	}
	/**
	 * Gets the longitude
	 * @return
	 */
	public String getLongitude() {
		return target.getLongitude();
	}
	/**
	 * Gets the master facility Arrondissement
	 * @return the getArrondissement
	 */
	public String getArrondissement() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.ARRONDISSEMENT);
	}
	
	/**
	 * Gets the master facility code (MSPP CODE)
	 * @return the code
	 */
	public String getSiteCode() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.SITECODE);
	}
	/**
	 * Gets the master facility code(old iSante Code)
	 * @return the getIsanteSiteCode
	 */
	public String getIsanteSiteCode() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.ISANTESITECODE);
	}

	/**
	 * Gets the facility CATEGORY
	 * @return the category
	 */
	public String getCategory() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.CATEGORY);
	}
	public String getLocationType() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.TYPE);
	}
	public String getNetwork() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.NETWORK);
	}
	
	/*
	public String getIncphr() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.INCPHR);
	}
	public String getDbSite() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.DBSITE);
	}
	public String getIpAddress() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.IPADDRESS);
	}
	public String getDbVersion() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.DBVERSION);
	}
	public String getOldClinicName() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.OLDCLINICNAME);
	}
	public String getHostname() {
		return (String) getAsAttribute(LocationBundle._LocationAttributeType.HOSTNAME);
	}*/
	
}
