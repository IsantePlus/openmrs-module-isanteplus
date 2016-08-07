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
package org.openmrs.module.isantepluspatientdashboard.api;

import org.json.JSONArray;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.isantepluspatientdashboard.ChartJSAgeAxis;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean
 * which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(IsantePlusPatientDashboardService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface IsantePlusPatientDashboardService extends OpenmrsService {

	Obs getLastViralLoadTestResultObsForPatient(Patient patient);

	JSONArray getPatientWeights(Patient patient);

	JSONArray getPatientHeights(Patient patient);

	Integer getPatientAgeInMonths(Patient patient);

	Integer getPatientAgeInDays(Patient patient);

	JSONArray getHeightsAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis);

	JSONArray getWeightsAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis);

	JSONArray getPatientHeadCircumferences(Patient patient);

	JSONArray getHeadCircumferenceAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis);
}