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
package org.openmrs.module.isantepluspatientdashboard.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusConstants;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.api.db.IsantePlusPatientDashboardDAO;

/**
 * It is a default implementation of {@link IsantePlusPatientDashboardService}.
 */
public class IsantePlusPatientDashboardServiceImpl extends BaseOpenmrsService
		implements IsantePlusPatientDashboardService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private IsantePlusPatientDashboardDAO dao;

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(IsantePlusPatientDashboardDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return the dao
	 */
	public IsantePlusPatientDashboardDAO getDao() {
		return dao;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public Obs getLastViralLoadTestResultObsForPatient(Patient patient) {
		Concept viralLoadConcept = IsantePlusConstants.VIRAL_LOAD_CONCEPT == null
				? Context.getConceptService().getConceptByName("HIV VIRAL LOAD")
				: IsantePlusConstants.VIRAL_LOAD_CONCEPT;
		List<Obs> viralLoadObs = new ArrayList(
				Context.getObsService().getObservations(patient, viralLoadConcept, false));

		Collections.sort(viralLoadObs, new Comparator<Obs>() {
			public int compare(Obs o1, Obs o2) {
				return o1.getObsDatetime().compareTo(o2.getObsDatetime());
			}
		});

		return viralLoadObs != null && viralLoadObs.size() > 0 ? viralLoadObs.get(viralLoadObs.size() - 1) : null;
	}
	
	@Override
	@SuppressWarnings({ "deprecation" })
	public JSONArray getPatientWeights(Patient patient) {
		// weight concept 5089
		JSONArray weightsJson = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		Integer weightConceptId = StringUtils
				.isNotBlank(Context.getAdministrationService().getGlobalProperty("concept.weight"))
						? Integer.parseInt(Context.getAdministrationService().getGlobalProperty("concept.weight"))
						: 5089;
		Concept weight = Context.getConceptService().getConcept(weightConceptId);

		for (Obs obs : Context.getObsService().getObservations(patient, weight, false)) {
			if (obs != null) {
				JSONObject json = new JSONObject();

				json.put("weight", obs.getValueNumeric());
				json.put("date", obs.getDateChanged() == null ? sdf.format(obs.getDateCreated())
						: sdf.format(obs.getDateChanged()));
				weightsJson.put(json);
			}
		}
		return weightsJson;
	}
}