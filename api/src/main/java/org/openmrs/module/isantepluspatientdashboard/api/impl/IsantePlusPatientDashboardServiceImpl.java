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
import java.util.Arrays;
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
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.isantepluspatientdashboard.AgeUnit;
import org.openmrs.module.isantepluspatientdashboard.ChartJSAgeAxis;
import org.openmrs.module.isantepluspatientdashboard.ConfigurableGlobalProperties;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusGlobalProps;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusPatientDashboardConstants;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusPatientDashboardManager;
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

	private Set<Obs> getWeightConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, "concept.weight", 5089);
	}

	@SuppressWarnings({ "deprecation" })
	private Set<Obs> getHeadCircumferenceConceptObsForAPatient(Patient patient) {
		Concept headCircumferenceConcept = new IsantePlusGlobalProps().HEAD_CIRCUMFERENC_CONCEPT;
		return headCircumferenceConcept != null
				? Context.getObsService().getObservations(patient, headCircumferenceConcept, false) : null;
	}

	private Set<Obs> getHeightConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, "concept.height", 5090);
	}

	private Set<Obs> getTemperatureConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, ConfigurableGlobalProperties.TEMPERATURE_CONCEPTID, 5088);
	}

	private Set<Obs> getPulseConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, ConfigurableGlobalProperties.PULSE_CONCEPTID, 5087);
	}

	private Set<Obs> getRespiratoryRateConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, ConfigurableGlobalProperties.RESPIRATORYRATE_CONCEPTID, 5242);
	}

	private Set<Obs> getBloodPressureConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, ConfigurableGlobalProperties.BLOODPRESSURE_CONCEPTID, 5086);
	}

	private Set<Obs> getBloodOxygenSaturationConceptObsForAPatient(Patient patient) {
		return getObsFromConceptForPatient(patient, ConfigurableGlobalProperties.BLOODOXYGENSATURATION_CONCEPTID, 5092);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Obs getRecentObsFromSet(Set<Obs> obsSet) {
		List<Obs> obsList = new ArrayList(obsSet);

		sortObsListByObsDateTime(obsList);

		return obsList != null && obsList.size() > 0 ? obsList.get(obsList.size() - 1) : null;
	}

	@Override
	public Obs getLatestHeightForPatient(Patient patient) {
		return getRecentObsFromSet(getHeightConceptObsForAPatient(patient));
	}

	@Override
	public Obs getLatestWeightForPatient(Patient patient) {
		return getRecentObsFromSet(getWeightConceptObsForAPatient(patient));
	}

	@Override
	public Obs getLatestPulseForPatient(Patient patient) {
		return getRecentObsFromSet(getPulseConceptObsForAPatient(patient));
	}

	@Override
	public Obs getLatestRespiratoryRateForPatient(Patient patient) {
		return getRecentObsFromSet(getRespiratoryRateConceptObsForAPatient(patient));
	}

	@Override
	public Obs getLatestBloodPressureForPatient(Patient patient) {
		return getRecentObsFromSet(getBloodPressureConceptObsForAPatient(patient));
	}

	@Override
	public Obs getLatestBloodOxygenSaturationForPatient(Patient patient) {
		return getRecentObsFromSet(getBloodOxygenSaturationConceptObsForAPatient(patient));
	}

	@Override
	public Obs getLatestTemperatureForPatient(Patient patient) {
		return getRecentObsFromSet(getTemperatureConceptObsForAPatient(patient));
	}

	@SuppressWarnings("deprecation")
	private Set<Obs> getObsFromConceptForPatient(Patient patient, String gpCodeForConcept, Integer conceptId) {
		return Context.getObsService().getObservations(patient,
				Context.getConceptService().getConcept(
						StringUtils.isNotBlank(Context.getAdministrationService().getGlobalProperty(gpCodeForConcept))
								? Integer.parseInt(
										Context.getAdministrationService().getGlobalProperty(gpCodeForConcept))
								: conceptId),
				false);
	}

	@Override
	public double roundAbout(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
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

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
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
	 * Should only be run by {@link InitialiseFormsHistory}, This has evolve
	 * from looping through encounters instead of visits
	 * 
	 * @param connection,
	 *            this should only be passed on from
	 *            {@link InitialiseFormsHistory}
	 * @throws SQLException
	 * @throws DatabaseException
	 * 
	 */
	public void runInitialHistoryCreatorAgainstDB(JdbcConnection connection) throws DatabaseException, SQLException {
		String daemonUserSelectSQL = "SELECT user_id FROM users WHERE uuid = 'A4F30A1B-5EB9-11DF-A648-37A07F9C90FB'";
		String encounterSelectSQL = "SELECT encounter_id FROM encounter WHERE form_id  IS NOT NULL";

		ResultSet rs = connection.createStatement().executeQuery(daemonUserSelectSQL);
		String userId = null;
		ResultSet encounteRs = connection.createStatement().executeQuery(encounterSelectSQL);

		if (rs.next()) {
			userId = rs.getString("user_id");
		}

		while (encounteRs.next()) {
			if (StringUtils.isNotBlank(encounteRs.getString("encounter_id"))) {
				List<FormHistory> finishedFormHistories = new ArrayList<FormHistory>();
				Integer encounterId = Integer.parseInt(encounteRs.getString("encounter_id"));

				if (encounterId != null) {

					FormHistory formHistory = createBasicFormHistoryObject(
							Context.getEncounterService().getEncounter(encounterId), false);

					if (!formHistoryExist(formHistory, finishedFormHistories)) {
						String sql = createSQLInsertQueryFromFormHistory(formHistory,
								StringUtils.isNotBlank(userId) ? Integer.parseInt(userId) : 1);

						if (connection != null && StringUtils.isNotBlank(sql)) {
							connection.createStatement().executeUpdate(sql);
						}
						finishedFormHistories.add(formHistory);
						log.info(
								"Successfully saved: " + formHistory + " for Encounter: " + formHistory.getEncounter());
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
	 */
	@Override
	public List<FormHistory> getOnlyIsanteFormHistories() {
		List<FormHistory> allFormhistories = getAllFormHistory();
		List<FormHistory> iSanteFormHistories = new ArrayList<FormHistory>();
		String excludableFormIds = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.FORMIDS_WHOSE_HISTORY_TOBEEXCLUDED);
		List<String> formIdsToBeMissed = StringUtils.isNotBlank(excludableFormIds)
				? Arrays.asList(excludableFormIds.replaceAll("\\s", "").split(",")) : null;
		List<Integer> formIdsToBeExcluded = new ArrayList<Integer>();

		if (formIdsToBeMissed != null && formIdsToBeMissed.size() > 0) {
			for (String s : formIdsToBeMissed)
				formIdsToBeExcluded.add(Integer.valueOf(s));
		}
		if (allFormhistories != null && allFormhistories.size() > 0) {
			for (FormHistory history : allFormhistories) {
				if (!formIdsToBeExcluded.contains(history.getEncounter().getForm().getFormId())) {
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

		for (FormHistory h : new IsantePlusGlobalProps().EXCLUDE_DEFAULT_OPENMRSFORMHISTORY
				? getOnlyIsanteFormHistories() : getAllFormHistory()) {
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

	@SuppressWarnings("deprecation")
	@Override
	public List<Obs> getLabsHistory(Patient patient) {
		// TESTS ORDERED = 1271
		List<Obs> labHistory = new ArrayList<Obs>();
		Integer labConceptId = 1271;
		Concept testsOrdered = Context.getConceptService().getConcept(labConceptId);

		for (Obs obs : Context.getObsService().getObservations(patient, testsOrdered, false)) {
			if (obs != null) {
				Integer result = Integer.parseInt(obs.getValueCoded().toString());
				Concept resultTest = Context.getConceptService().getConcept(result);

				for (Obs obs1 : Context.getObsService().getObservations(patient, resultTest, false)) {
					labHistory.add(obs1);
				}
			}
		}
		return labHistory;
	}

	@Override
	public ComponentState getAppframeworkComponentState(String componentSateId) {
		return dao.getAppframeworkComponentState(componentSateId);
	}

	/**
	 * 
	 * @param extensions,
	 *            of format {extensionId: true/false}
	 */
	@Override
	public void updateComponentStates(JSONObject extensions) {
		IsantePlusPatientDashboardManager manager = new IsantePlusPatientDashboardManager();

		if (extensions != null && extensions.length() > 0) {
			ComponentState growthCharts = getAppframeworkComponentState(manager.getGrowthChartsExtensionId());
			ComponentState LabHistory = getAppframeworkComponentState(manager.getLabHistoryExtensionId());
			ComponentState lastViralLoad = getAppframeworkComponentState(manager.getLastViralLoadTestExtensionId());
			ComponentState patientFormHistory = getAppframeworkComponentState(
					manager.getPatientFormHistoryExtensionId());
			ComponentState visitFormHistory = getAppframeworkComponentState(manager.getVisitFormHistoryExtensionId());
			ComponentState weightsGraph = getAppframeworkComponentState(manager.getWeightsGraphExtensionId());
			ComponentState isantePlusForms = getAppframeworkComponentState(manager.getIsantePlusFormsExtensionId());
			ComponentState mostRecentVitals = getAppframeworkComponentState(manager.getMostRecentVitalsExtensionId());
			ComponentState coreAppsMostRecentVitals = getAppframeworkComponentState(
					IsantePlusPatientDashboardConstants.DEFAULT_MOSTRECENTVITALS_EXTENSIONPOINT_ID);

			if (growthCharts != null && extensions.has(manager.getGrowthChartsExtensionId())) {
				growthCharts.setEnabled(extensions.getBoolean(manager.getGrowthChartsExtensionId()));
				saveOrUpdateComponentState(growthCharts);
			}
			if (LabHistory != null && extensions.has(manager.getLabHistoryExtensionId())) {
				LabHistory.setEnabled(extensions.getBoolean(manager.getLabHistoryExtensionId()));
				saveOrUpdateComponentState(LabHistory);
			}
			if (lastViralLoad != null && extensions.has(manager.getLastViralLoadTestExtensionId())) {
				lastViralLoad.setEnabled(extensions.getBoolean(manager.getLastViralLoadTestExtensionId()));
				saveOrUpdateComponentState(lastViralLoad);
			}
			if (patientFormHistory != null && extensions.has(manager.getPatientFormHistoryExtensionId())) {
				patientFormHistory.setEnabled(extensions.getBoolean(manager.getPatientFormHistoryExtensionId()));
				saveOrUpdateComponentState(patientFormHistory);
			}
			if (visitFormHistory != null && extensions.has(manager.getVisitFormHistoryExtensionId())) {
				visitFormHistory.setEnabled(extensions.getBoolean(manager.getVisitFormHistoryExtensionId()));
				saveOrUpdateComponentState(visitFormHistory);
			}
			if (weightsGraph != null && extensions.has(manager.getWeightsGraphExtensionId())) {
				weightsGraph.setEnabled(extensions.getBoolean(manager.getWeightsGraphExtensionId()));
				saveOrUpdateComponentState(weightsGraph);
			}
			if (isantePlusForms != null && extensions.has(manager.getIsantePlusFormsExtensionId())) {
				isantePlusForms.setEnabled(extensions.getBoolean(manager.getIsantePlusFormsExtensionId()));
				saveOrUpdateComponentState(isantePlusForms);
			}
			// enabling mostRecentVitals should disable coreAppsMostRecentVitals
			// and vice-versa
			if (mostRecentVitals != null && extensions.has(manager.getMostRecentVitalsExtensionId())) {
				mostRecentVitals.setEnabled(extensions.getBoolean(manager.getMostRecentVitalsExtensionId()));
				coreAppsMostRecentVitals.setEnabled(!mostRecentVitals.getEnabled());
				saveOrUpdateComponentState(isantePlusForms);
				saveOrUpdateComponentState(coreAppsMostRecentVitals);
			}
		}
	}

	@Override
	public ComponentState saveOrUpdateComponentState(ComponentState componentState) {
		return dao.saveOrUpdateComponentState(componentState);
	}

}