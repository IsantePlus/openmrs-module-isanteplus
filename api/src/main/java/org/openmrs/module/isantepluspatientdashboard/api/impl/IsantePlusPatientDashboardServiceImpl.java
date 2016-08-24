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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.openmrs.module.isantepluspatientdashboard.IsantePlusGlobalProps;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.api.db.IsantePlusPatientDashboardDAO;
import org.openmrs.module.isantepluspatientdashboard.liquibase.InitialiseFormsHistory;
import org.openmrs.module.isantepluspatientdashboard.mapped.FormHistory;

import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

/**
 * It is a default implementation of {@link IsantePlusPatientDashboardService}.
 */
public class IsantePlusPatientDashboardServiceImpl extends BaseOpenmrsService
		implements IsantePlusPatientDashboardService {

	protected final Log log = LogFactory.getLog(this.getClass());

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		IsantePlusGlobalProps isantePlusConstants = new IsantePlusGlobalProps();
		Concept viralLoadConcept = isantePlusConstants.VIRAL_LOAD_CONCEPT == null
				? Context.getConceptService().getConceptByName("HIV VIRAL LOAD")
				: isantePlusConstants.VIRAL_LOAD_CONCEPT;
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
		Concept headCircumferenceConcept = new IsantePlusGlobalProps().HEAD_CIRCUMFERENC_CONCEPT;
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
	 * 
	 * @param connection,
	 *            this should only be passed on from
	 *            {@link InitialiseFormsHistory}
	 * @throws SQLException
	 * @throws DatabaseException
	 * TODO this should instead loop through encounters instead of visits
	 */
	public void runInitialHistoryCreatorAgainstDB(JdbcConnection connection) throws DatabaseException, SQLException {
		List<Visit> visits = Context.getVisitService().getAllVisits();
		String daemonUserSelectSQL = "SELECT user_id FROM users WHERE uuid = 'A4F30A1B-5EB9-11DF-A648-37A07F9C90FB'";
		ResultSet rs = connection.createStatement().executeQuery(daemonUserSelectSQL);
		String userId = null;

		if (rs.next()) {
			userId = rs.getString("user_id");
		}

		for (Visit visit : visits) {
			visit = sortVisitUsingEncounterDateForItsEncounters(visit);
			if (visit != null) {
				for (Encounter encounter : visit.getEncounters()) {
					List<FormHistory> finishedFormHistories = new ArrayList<FormHistory>();

					if (encounter != null && encounter.getForm() != null
							&& visit.getVisitId().equals(encounter.getVisit().getVisitId())) {
						FormHistory formHistory = createBasicFormHistoryObject(encounter, false);

						if (!formHistoryExist(formHistory, finishedFormHistories)) {
							String sql = createSQLInsertQueryFromFormHistory(formHistory,
									StringUtils.isNotBlank(userId) ? Integer.parseInt(userId) : 1);

							if (connection != null && StringUtils.isNotBlank(sql)) {
								connection.createStatement().executeUpdate(sql);
							} else {
								formHistory = saveFormHistory(formHistory);
							}
							finishedFormHistories.add(formHistory);
							log.info("Successfully saved: " + formHistory + " for Encounter: "
									+ formHistory.getEncounter());
						}
					}
				}
			}
		}
	}

	private String createSQLInsertQueryFromFormHistory(FormHistory formHistory, Integer userId) {
		String sql = null;

		if (formHistory != null && userId != null) {
			sql = "INSERT INTO isantepluspatientdashboard_form_history ("
					+ (formHistory.getVisit() != null ? "visit_id ," : "")
					+ "encounter_id, creator, date_created, voided, uuid) VALUES("
					+ (formHistory.getVisit() != null ? formHistory.getVisit().getVisitId() + "," : "")
					+ formHistory.getEncounter().getEncounterId() + ", " + userId + ", '"
					+ sdf.format(formHistory.getDateCreated()) + "', " + (formHistory.isVoided() ? 1 : 0) + ", '"
					+ formHistory.getUuid() + "')";
		}

		return sql;
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

	/**
	 * 
	 * @param encounter,
	 *            required for every form history since a every encounter is
	 *            submitted through a form
	 * @param contextUp,
	 *            defines whether the OpenMRS {@link Context} is accessible
	 * @return the created formHistory object
	 */
	@Override
	public FormHistory createBasicFormHistoryObject(Encounter encounter, boolean contextUp) {
		FormHistory formHistory = null;

		if (encounter != null) {
			formHistory = new FormHistory(encounter);

			if (contextUp)
				formHistory.setCreator(Context.getAuthenticatedUser());
			formHistory.setDateCreated(new Date());
			formHistory.setVisit(encounter.getVisit());
			formHistory.setVoided(false);
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

	/**
	 * Excludes all default reference application forms and probably only
	 * returns iSante forms
	 * 
	 * Assumes that default form names are not renamed
	 * 
	 * TODO: Is this really what iSantePlus intends to only be listed?
	 */
	@Override
	public List<FormHistory> getOnlyIsanteFormHistories() {
		List<FormHistory> allFormhistories = getAllFormHistory();
		List<FormHistory> iSanteFormHistories = new ArrayList<FormHistory>();

		if (allFormhistories != null && allFormhistories.size() > 0) {
			for (FormHistory history : allFormhistories) {
				if (!"Vitals".equals(history.getEncounter().getForm().getName())
						&& !"Visit Note".equals(history.getEncounter().getForm().getName())
						&& !"Admission (Simple)".equals(history.getEncounter().getForm().getName())
						&& !"Discharge (Simple)".equals(history.getEncounter().getForm().getName())
						&& !"Transfer Within Hospital (Simple)".equals(history.getEncounter().getForm().getName())) {
					iSanteFormHistories.add(history);
				}
			}
		}

		return iSanteFormHistories;
	}

	@Override
	public List<FormHistory> getOnlyIsanteFormHistoriesByVisit(Visit visit) {
		return filterHistoriesByVisit(getOnlyIsanteFormHistories(), visit);
	}

	private List<FormHistory> filterHistoriesByVisit(List<FormHistory> history, Visit visit) {
		List<FormHistory> histories = new ArrayList<FormHistory>();

		for (FormHistory h : history) {
			if (h.getVisit() != null && visit != null && h.getVisit().getVisitId().equals(visit.getVisitId())) {
				histories.add(h);
			}
		}
		return histories;
	}

	@Override
	public List<FormHistory> getAllFormHistoryForAPatient(Patient patient) {
		List<FormHistory> histories = new ArrayList<FormHistory>();

		for (FormHistory h : getAllFormHistory()) {
			if (patient != null && h.getEncounter() != null && h.getEncounter().getPatient() != null
					&& h.getEncounter().getPatient().getPatientId().equals(patient.getPatientId())) {
				histories.add(h);
			}
		}
		return histories;
	}

	@Override
	public List<FormHistory> getAllFormHistoryByVisit(Visit visit) {
		return filterHistoriesByVisit(getAllFormHistory(), visit);
	}
}