package org.openmrs.module.isanteplus.deploy.bundle;

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

import org.openmrs.Location;

import org.openmrs.api.LocationService;
import org.openmrs.module.isanteplus.wrapper.Facility;
import org.openmrs.module.metadatadeploy.sync.ObjectSynchronization;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Synchronization operation to sync locations with a CSV copy of the SEDISH Master Facility List
 */
@Component
public class LocationMflSynchronization implements ObjectSynchronization<Location> {

	@Autowired
	private LocationService locationService;

	/**
	 * @see org.openmrs.module.metadatadeploy.sync.ObjectSynchronization#fetchAllExisting()
	 */
	@Override
	public List<Location> fetchAllExisting() {
		return locationService.getAllLocations(true);
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.sync.ObjectSynchronization#getObjectSyncKey(org.openmrs.OpenmrsObject)
	 */
	@Override
	public Object getObjectSyncKey(Location obj) {
		return new Facility(obj).getSiteCode();
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.sync.ObjectSynchronization#updateRequired(org.openmrs.OpenmrsObject, org.openmrs.OpenmrsObject)
	 */
	@Override
	public boolean updateRequired(Location incoming, Location existing) {
		Facility facility1 = new Facility(incoming);
		Facility facility2 = new Facility(existing);

		boolean objectsMatch = OpenmrsUtil.nullSafeEquals(incoming.getName(), existing.getName())
				&& OpenmrsUtil.nullSafeEquals(facility1.getStateProvince(), facility2.getStateProvince())
				&& OpenmrsUtil.nullSafeEquals(facility1.getCityVillage(), facility2.getCityVillage())
				&& OpenmrsUtil.nullSafeEquals(facility1.getCountry(), facility2.getCountry())
				&& OpenmrsUtil.nullSafeEquals(facility1.getLatitude(), facility2.getLatitude())
				&& OpenmrsUtil.nullSafeEquals(facility1.getLongitude(), facility2.getLongitude())
				&& OpenmrsUtil.nullSafeEquals(facility1.getCategory(), facility2.getCategory())
				&& OpenmrsUtil.nullSafeEquals(facility1.getSiteCode(), facility2.getSiteCode())
				&& OpenmrsUtil.nullSafeEquals(facility1.getIsanteSiteCode(), facility2.getIsanteSiteCode())
				/*&& OpenmrsUtil.nullSafeEquals(facility1.getDbSite(), facility2.getDbSite())
				&& OpenmrsUtil.nullSafeEquals(facility1.getDbVersion(), facility2.getDbVersion())
				&& OpenmrsUtil.nullSafeEquals(facility1.getIncphr(), facility2.getIncphr())
				&& OpenmrsUtil.nullSafeEquals(facility1.getIpAddress(), facility2.getIpAddress())*/
				&& OpenmrsUtil.nullSafeEquals(facility1.getNetwork(), facility2.getNetwork())
				&& OpenmrsUtil.nullSafeEquals(facility1.getLocationType(), facility2.getLocationType());
				/*&& OpenmrsUtil.nullSafeEquals(facility1.getOldClinicName(), facility2.getOldClinicName())
				&& OpenmrsUtil.nullSafeEquals(facility1.getHostname(), facility2.getHostname());*/

		return !objectsMatch;
	}
}