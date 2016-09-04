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
package org.openmrs.module.isantepluspatientdashboard.api.db;

import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;
import org.openmrs.module.isantepluspatientdashboard.mapped.FormHistory;

/**
 * Database methods for {@link IsantePlusPatientDashboardService}.
 */
public interface IsantePlusPatientDashboardDAO {

	FormHistory getFormHistory(Integer formHistoryId);

	FormHistory getFormHistoryByUuid(String formHistoryUuid);

	void deleteFormHistory(FormHistory formHistory);

	List<FormHistory> getAllFormHistory();

	FormHistory saveFormHistory(FormHistory formHistory);

	List<Encounter> getAllEncounters();

	ComponentState getAppframeworkComponentState(String componentSateId);

	ComponentState saveOrUpdateComponentState(ComponentState componentState);
}