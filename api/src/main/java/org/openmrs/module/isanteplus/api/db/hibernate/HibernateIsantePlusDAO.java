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
package org.openmrs.module.isanteplus.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.isanteplus.api.db.IsantePlusDAO;
import org.openmrs.module.isanteplus.mapped.FormHistory;

/**
 * It is a default implementation of {@link IsantePlusDAO}.
 */
public class HibernateIsantePlusDAO implements IsantePlusDAO {
	protected final Log log = LogFactory.getLog(this.getClass());

	private SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public FormHistory getFormHistory(Integer formHistoryId) {
		return (FormHistory) getSessionFactory().getCurrentSession().get(FormHistory.class, formHistoryId);
	}

	@Override
	public FormHistory getFormHistoryByUuid(String formHistoryUuid) {
		return (FormHistory) getSessionFactory().getCurrentSession()
				.createQuery("from FormHistory fh where fh.uuid = :uuid").setString("uuid", formHistoryUuid)
				.uniqueResult();
	}

	@Override
	public void deleteFormHistory(FormHistory formHistory) {
		/*getSessionFactory().getCurrentSession().delete(formHistory);*/
		Encounter encounter = formHistory.getEncounter();
		Context.getEncounterService().voidEncounter(encounter, "delete encounter");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FormHistory> getAllFormHistory() {
		return getSessionFactory().getCurrentSession().createCriteria(FormHistory.class).list();
	}
	
	/* this method was added to resolve slow issue*/
	@Override
	@SuppressWarnings("unchecked")
	public List<FormHistory> getAllFormHistory(Patient patient) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(FormHistory.class)
				.createAlias("patient", "p").add(Restrictions.eq("p.patientId", patient.getPatientId()));
		Criteria crit1 = crit.createCriteria("encounter");
		   crit1.addOrder(Order.desc("encounterDatetime"));
		return crit.list();
	}
	
	/* this method was added to resolve slow issue*/
	@Override
	@SuppressWarnings("unchecked")
	public List<FormHistory> getAllFormHistory(Visit visit) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(FormHistory.class)
				.createAlias("visit", "v").add(Restrictions.eq("v.visitId", visit.getVisitId()));
		Criteria crit1 = crit.createCriteria("encounter");
		   crit1.addOrder(Order.desc("encounterDatetime"));
		return crit.list();
	}
	
	

	@Override
	@SuppressWarnings("unchecked")
	public List<Encounter> getAllEncounters() {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class).list();
	}

	@Override
	public FormHistory saveFormHistory(FormHistory formHistory) {
		sessionFactory.getCurrentSession().saveOrUpdate(formHistory);
		return formHistory;
	}

	@Override
	public ComponentState getAppframeworkComponentState(String componentSateId) {
		ComponentState componentSate = (ComponentState) getSessionFactory().getCurrentSession()
				.createQuery("from ComponentState cs where cs.componentId = :component_id")
				.setString("component_id", componentSateId).uniqueResult();

		return componentSate;
	}

	@Override
	public ComponentState saveOrUpdateComponentState(ComponentState componentState) {
		sessionFactory.getCurrentSession().saveOrUpdate(componentState);
		return componentState;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FormHistory> getFormHistoryByEncounterId(Integer encounterId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(FormHistory.class)
				.createAlias("encounter", "e").add(Restrictions.eq("e.encounterId", encounterId));

		return crit.list();
	}
	@Override
	public SessionFactory getSessionFactoryResult() {
		return sessionFactory;
	}
}