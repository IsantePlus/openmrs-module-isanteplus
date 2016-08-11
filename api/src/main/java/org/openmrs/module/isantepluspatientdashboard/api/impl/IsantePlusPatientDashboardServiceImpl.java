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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.isantepluspatientdashboard.AgeUnit;
import org.openmrs.module.isantepluspatientdashboard.ChartJSAgeAxis;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusConstants;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.api.db.IsantePlusPatientDashboardDAO;
import org.openmrs.module.isantepluspatientdashboard.liquibase.InitialiseFormsHistory;
import org.openmrs.module.isantepluspatientdashboard.mapped.FormHistory;

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

		sortObsListByObsDateTime(viralLoadObs);

		return viralLoadObs != null && viralLoadObs.size() > 0 ? viralLoadObs.get(viralLoadObs.size() - 1) : null;
	}

	@Override
	public JSONArray getPatientWeights(Patient patient) {
		JSONArray weightsJson = new JSONArray();

		for (Obs obs : getWeightConceptObsForAPatient(patient)) {
			if (obs != null) {
				JSONObject json = new JSONObject();

				json.put("weight", obs.getValueNumeric());
				json.put("measureDate", getObservationDate(obs));
				json.put("birthDate", patient.getBirthdate());
				weightsJson.put(json);
			}
		}
		return weightsJson;
	}

	@Override
	public JSONArray getPatientHeights(Patient patient) {
		JSONArray heightsJson = new JSONArray();

		for (Obs obs : getHeightConceptObsForAPatient(patient)) {
			if (obs != null) {
				JSONObject json = new JSONObject();

				json.put("height", obs.getValueNumeric());
				json.put("measureDate", getObservationDate(obs));
				json.put("birthDate", patient.getBirthdate());
				heightsJson.put(json);
			}
		}
		return heightsJson;
	}

	@Override
	public JSONArray getPatientHeadCircumferences(Patient patient) {
		JSONArray headCircumference = new JSONArray();
		Set<Obs> headCircumferenceConceptObsForAPatient = getHeadCircumferenceConceptObsForAPatient(patient);

		if (headCircumferenceConceptObsForAPatient != null) {
			for (Obs obs : headCircumferenceConceptObsForAPatient) {
				if (obs != null) {
					JSONObject json = new JSONObject();

					json.put("headCircumference", obs.getValueNumeric());
					json.put("measureDate", getObservationDate(obs));
					json.put("birthDate", patient.getBirthdate());
					headCircumference.put(json);
				}
			}
		}
		return headCircumference;
	}

	@SuppressWarnings({ "deprecation" })
	private Set<Obs> getWeightConceptObsForAPatient(Patient patient) {
		// weight concept 5089
		Integer weightConceptId = StringUtils
				.isNotBlank(Context.getAdministrationService().getGlobalProperty("concept.weight"))
						? Integer.parseInt(Context.getAdministrationService().getGlobalProperty("concept.weight"))
						: 5089;
		return Context.getObsService().getObservations(patient, Context.getConceptService().getConcept(weightConceptId),
				false);
	}

	@SuppressWarnings({ "deprecation" })
	private Set<Obs> getHeadCircumferenceConceptObsForAPatient(Patient patient) {
		Concept headCircumferenceConcept = IsantePlusConstants.HEAD_CIRCUMFERENC_CONCEPT;
		return headCircumferenceConcept != null
				? Context.getObsService().getObservations(patient, headCircumferenceConcept, false) : null;
	}

	@SuppressWarnings({ "deprecation" })
	private Set<Obs> getHeightConceptObsForAPatient(Patient patient) {
		// height concept 5090
		return Context.getObsService().getObservations(patient, Context.getConceptService().getConcept(
				StringUtils.isNotBlank(Context.getAdministrationService().getGlobalProperty("concept.height"))
						? Integer.parseInt(Context.getAdministrationService().getGlobalProperty("concept.height"))
						: 5090),
				false);
	}

	/**
	 * @TODO Wondering whether this doesn't under-look the expected
	 *       understanding of OpenMRS obs date!!!
	 * @param obs
	 * @return
	 */
	private Date getObservationDate(Obs obs) {
		return obs == null ? null
				: (obs.getObsDatetime() != null ? obs.getObsDatetime()
						: (obs.getDateChanged() != null ? obs.getDateChanged() : obs.getDateCreated()));
	}

	@Override
	public Integer getPatientAgeInMonths(Patient patient) {
		if (patient.getBirthdate() == null) {
			return null;
		}
		Date endDate = patient.isDead() ? patient.getDeathDate() : new Date();

		return Months.monthsBetween(new DateTime(patient.getBirthdate()), new DateTime(endDate)).getMonths();
	}

	@Override
	public Integer getPatientAgeInDays(Patient patient) {
		if (patient.getBirthdate() == null) {
			return null;
		}
		Date endDate = patient.isDead() ? patient.getDeathDate() : new Date();

		return Days.daysBetween(new DateTime(patient.getBirthdate()), new DateTime(endDate)).getDays();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray getWeightsAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis) {
		List<Obs> weightsObs = new ArrayList(getWeightConceptObsForAPatient(patient));

		sortObsListByObsDateTime(weightsObs);
		return getConceptObsValuesAtAGivenAgesOfPatient(weightsObs, ageAxis);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray getHeightsAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis) {
		List<Obs> heightsObs = new ArrayList(getHeightConceptObsForAPatient(patient));

		sortObsListByObsDateTime(heightsObs);
		return getConceptObsValuesAtAGivenAgesOfPatient(heightsObs, ageAxis);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray getHeadCircumferenceAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis) {
		Set<Obs> headCircumferenceConceptObsForAPatient = getHeadCircumferenceConceptObsForAPatient(patient);

		if (headCircumferenceConceptObsForAPatient != null) {
			List<Obs> headCircumferenceObs = new ArrayList(headCircumferenceConceptObsForAPatient);
			sortObsListByObsDateTime(headCircumferenceObs);
			return getConceptObsValuesAtAGivenAgesOfPatient(headCircumferenceObs, ageAxis);
		} else
			return null;
	}

	private void sortObsListByObsDateTime(List<Obs> obsList) {
		Collections.sort(obsList, new Comparator<Obs>() {
			public int compare(Obs o1, Obs o2) {
				return o1.getObsDatetime().compareTo(o2.getObsDatetime());
			}
		});
	}

	private JSONArray getConceptObsValuesAtAGivenAgesOfPatient(List<Obs> conceptObsList, ChartJSAgeAxis ageAxis) {
		JSONArray conceptObsForAges = new JSONArray();

		if (conceptObsList != null && ageAxis != null) {
			Integer[] ages = createIntegerArrayByRange(ageAxis.getStartAge(), ageAxis.getLastAge(),
					ageAxis.getAgeDifference());

			for (int i = 0; i < ages.length; i++) {
				JSONObject conceptObsForAge = new JSONObject();
				Double obsValueAtAge = getObsValueAtAGivenAge(conceptObsList, ageAxis.getAgeUnit(), ages[i]);

				if (obsValueAtAge != null) {
					conceptObsForAge.put(Integer.toString(ages[i]), obsValueAtAge);
					conceptObsForAges.put(conceptObsForAge);
				}

			}
		}

		return conceptObsForAges;
	}

	private Integer[] createIntegerArrayByRange(Integer start, Integer end, Integer difference) {
		Integer[] array = new Integer[((end - start) / difference) + 1];
		int i = 1;

		array[0] = start;
		for (int k = start; k <= end; k = k + difference) {
			if (i < array.length) {
				array[i] = k + difference;
				i++;
			}
		}
		return array;
	}

	/*
	 * Currently only supporting two AgeUnits Months and Years which are the
	 * ones required for the isante growth charts
	 */
	private Double getObsValueAtAGivenAge(List<Obs> conceptObsList, AgeUnit ageUnit, Integer atAge) {
		Double obsValueAtAge = null;

		if (conceptObsList != null && conceptObsList.size() > 0 && ageUnit != null && atAge != null) {
			Date birthDate = conceptObsList.get(0).getPerson().getBirthdate();
			Calendar atAgeFromBirth = Calendar.getInstance(Context.getLocale());

			atAgeFromBirth.setTime(birthDate);
			if (ageUnit.equals(AgeUnit.MONTHS)) {
				atAgeFromBirth.add(Calendar.MONTH, atAge);
			} else if (ageUnit.equals(AgeUnit.YEARS)) {
				atAgeFromBirth.add(Calendar.YEAR, atAge);
			}

			for (Obs obs : conceptObsList) {
				Calendar obsDate = Calendar.getInstance(Context.getLocale());

				obsDate.setTime(getObservationDate(obs));
				Integer diffYears = obsDate.get(Calendar.YEAR) - atAgeFromBirth.get(Calendar.YEAR);
				Integer diffMonths = diffYears * 12 + obsDate.get(Calendar.MONTH) - atAgeFromBirth.get(Calendar.MONTH);

				if (ageUnit.equals(AgeUnit.MONTHS) && diffMonths == 0) {
					obsValueAtAge = obs.getValueNumeric();
					break;
				} else if (ageUnit.equals(AgeUnit.YEARS) && diffYears == 0) {
					obsValueAtAge = obs.getValueNumeric();
					break;
				}
			}
		}

		return obsValueAtAge;
	}

	/**
	 * @TODO; use visit paramter to patient to match passed in patient and visit
	 * patient <br />
	 * Get all the encounters for the passed in visit<br />
	 * Each of those encounters has a form's property, sort them bassed on the
	 * forms and encounter dates<br />
	 * Handle FormHistory TODOs to make sure each formHistory entry is actually
	 * manageable and deletable separately and that then write this whole list
	 * to be returned<br />
	 * Use AOP to update forms history table every time a visit is updated<br />
	 * Loading form history page should be handled by htmlformentryui module
	 * which this module should require
	 * 
	 * @param visit
	 * @return
	 */
	public List<FormHistory> getFormsHistory(Visit visit, Patient patient) {

		return null;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Visit sortVisitUsingEncounterDateForItsEncounters(Visit visit) {
		Visit sortedVisit = null;
		Set<Encounter> sortedEncounters = null;

		if (visit != null && visit.getPatient() != null && visit.getPatient().getPerson() != null) {
			Set<Encounter> encounters = visit.getEncounters();

			if (encounters != null && encounters.size() > 0) {
				List<Encounter> encountersList = new ArrayList(visit.getEncounters());

				sortEncountersByDateUpdatedOrCreated(encountersList);
				sortedEncounters = new HashSet<Encounter>(encountersList);
				sortedVisit = visit;
				sortedVisit.setEncounters(sortedEncounters);
			}
		}

		return sortedVisit;
	}

	private void sortEncountersByDateUpdatedOrCreated(List<Encounter> encounterList) {
		Collections.sort(encounterList, new Comparator<Encounter>() {
			public int compare(Encounter e1, Encounter e2) {
				if (e1.getDateChanged() != null && e2.getDateChanged() != null)
					return e1.getDateChanged().compareTo(e2.getDateChanged());
				else
					return e1.getDateCreated().compareTo(e2.getDateCreated());
			}
		});
	}

	/**
	 * Should only be run by {@link InitialiseFormsHistory}
	 */
	public void runInitialHistoryCreatorAgainstDB() {
		List<Visit> visits = Context.getVisitService().getAllVisits();

		for (Visit visit : visits) {
			visit = sortVisitUsingEncounterDateForItsEncounters(visit);
			for (Encounter encounter : visit.getEncounters()) {
				List<FormHistory> finishedFormHistories = new ArrayList<FormHistory>();

				if (encounter != null && encounter.getForm() != null) {
					FormHistory formHistory = createBasicFormHistoryObject(visit, encounter);

					if (formHistoryExist(formHistory, finishedFormHistories)) {
						formHistory = saveFormHistory(formHistory);
						finishedFormHistories.add(formHistory);
						log.info(
								"Successfully saved: " + formHistory + " for Encounter: " + formHistory.getEncounter());
					}
				}
			}
		}
	}

	@Override
	public boolean formHistoryExist(FormHistory formHistory, List<FormHistory> formHistoryLookup) {
		List<FormHistory> fHistoryLookup = formHistoryLookup != null ? formHistoryLookup : getAllFormHistory();

		for (FormHistory fh : fHistoryLookup) {
			if (fh != null && formHistory != null && fh.getEncounter() != null && formHistory.getEncounter() != null
					&& fh.getEncounter().getUuid().equals(formHistory.getEncounter().getUuid())
					&& fh.getEncounter().getPatient().equals(formHistory.getEncounter().getPatient()))
				return true;
		}
		return false;
	}

	private FormHistory createBasicFormHistoryObject(Visit visit, Encounter encounter) {
		FormHistory formHistory = null;

		if (visit != null && encounter != null) {
			formHistory = new FormHistory();
			formHistory.setCreator(Context.getAuthenticatedUser());
			formHistory.setDateCreated(new Date());

			formHistory.setVisit(visit);
		}
		return formHistory;
	}

	@Override
	public FormHistory getFormHistory(Integer formHistoryId) {
		return dao.getFormHistory(formHistoryId);
	}

	@Override
	public FormHistory getFormHistoryByUuid(String formHistoryUuid) {
		return dao.getFormHistoryByUuid(formHistoryUuid);
	}

	@Override
	public void deleteFormHistory(FormHistory formHistory) {
		dao.deleteFormHistory(formHistory);
	}

	@Override
	public List<FormHistory> getAllFormHistory() {
		return dao.getAllFormHistory();
	}

	/**
	 * TODO each form should have one history entry implying unique visit
	 */
	@Override
	public FormHistory saveFormHistory(FormHistory formHistory) {
		return dao.saveFormHistory(formHistory);
	}
}