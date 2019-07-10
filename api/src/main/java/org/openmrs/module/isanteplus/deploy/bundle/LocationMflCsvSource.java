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

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.module.isanteplus.deploy.bundle.LocationBundle._LocationAttributeType;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.metadatadeploy.source.AbstractCsvResourceSource;

import java.io.IOException;

/**
 * Location source from Master Facility CSV resource
 */
public class LocationMflCsvSource extends AbstractCsvResourceSource<Location> {

	private LocationAttributeType category, locationType, isanteSiteCode, siteCode, network;
	

	/**
	 * Constructs a new location source
	 * @param csvFile the csv resource path
	 */
	
	public LocationMflCsvSource(String csvFile) throws IOException {
		super(csvFile, true);
		this.siteCode = MetadataUtils.existing(LocationAttributeType.class, _LocationAttributeType.SITECODE);
		this.isanteSiteCode = MetadataUtils.existing(LocationAttributeType.class, _LocationAttributeType.ISANTESITECODE);
		this.category = MetadataUtils.existing(LocationAttributeType.class, _LocationAttributeType.CATEGORY);
		this.locationType = MetadataUtils.existing(LocationAttributeType.class, _LocationAttributeType.TYPE);
		this.network = MetadataUtils.existing(LocationAttributeType.class, _LocationAttributeType.NETWORK);
	}
    
	/**
	 * @see org.openmrs.module.metadatadeploy.source.AbstractCsvResourceSource#parseLine(String[])
	 */
	@Override
	public Location parseLine(String[] line) {
		String departement = line[0];
		String commune = line[1];
		String sectionCommunale = line[2];
		String name = line[3];
		String categorySite = line[4];
		String typeLocation = line[5];
		String siteCod = line[6];
		String oldSideCode = line [7];
		String siteNetwork = line[8];
		String lat = line[9];
		String lng = line[10];
		
		

		Location location = new Location();
		location.setAddress3(sectionCommunale);;
		location.setStateProvince(departement);
		location.setName(name);
		location.setLatitude(lat);
		location.setLongitude(lng);
		location.setCountry("Haiti");
		location.setCityVillage(commune);
		
		setAsAttribute(location, siteCode, siteCod);
		setAsAttribute(location, isanteSiteCode, oldSideCode);
		setAsAttribute(location, category, categorySite);
		setAsAttribute(location, locationType, typeLocation);
		setAsAttribute(location, network, siteNetwork);

		return location;
	}

	/**
	 * Adds a value as an attribute if it's not blank
	 * @param location the location
	 * @param type the attribute type
	 * @param value the value
	 */
	protected void setAsAttribute(Location location, LocationAttributeType type, String value) {
		if (StringUtils.isNotBlank(value)) {
			LocationAttribute attr = new LocationAttribute();
			attr.setAttributeType(type);
			attr.setValue(value.trim());
			attr.setOwner(location);
			location.addAttribute(attr);
		}
	}
}