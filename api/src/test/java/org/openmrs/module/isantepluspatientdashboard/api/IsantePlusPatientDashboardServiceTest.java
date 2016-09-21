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

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.IsantePlusObs;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${IsantePlusPatientDashboardService}}.
 */
public class IsantePlusPatientDashboardServiceTest extends BaseModuleContextSensitiveTest {

	@Test
	public void shouldSetupContext() {
		assertNotNull(Context.getService(IsantePlusPatientDashboardService.class));
	}

	@Test
	public void test_getPatientAgeInvocations() {
		Patient patient = Context.getPatientService().getAllPatients().get(0);
		Calendar birthday = Calendar.getInstance();

		Assert.assertFalse(patient.getAge() < 1);
		patient.setBirthdate(birthday.getTime());
		Assert.assertFalse(patient.getAge() == 1);
		Assert.assertEquals(new Integer(0), patient.getAge());
		Assert.assertEquals(new Integer(0),
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInMonths(patient));
		Assert.assertEquals(new Integer(0),
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInDays(patient));
		birthday.add(Calendar.MONTH, -4);
		patient.setBirthdate(birthday.getTime());

		Assert.assertEquals(new Integer(4),
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInMonths(patient));

		birthday.add(Calendar.DAY_OF_YEAR, -31);
		patient.setBirthdate(birthday.getTime());

		Assert.assertEquals(new Integer(5),
				Context.getService(IsantePlusPatientDashboardService.class).getPatientAgeInMonths(patient));
	}

	@Test
	public void test_gettingObsAnswerUnitsAndRangeForeaxampleForLabHistory() {
		IsantePlusObs o = new IsantePlusObs(Context.getService(IsantePlusPatientDashboardService.class)
				.getLatestWeightForPatient(Context.getPatientService().getPatient(7)));

		Assert.assertEquals("kg", o.getConceptNumeric().getUnits());
		// HiAbsolute and LowAbsolute NOT Set for test data
		// Assert.assertEquals(Double.valueOf(250.0),
		// o.getConceptNumeric().getHiAbsolute());
		// Assert.assertEquals(Double.valueOf(0.0),
		// o.getConceptNumeric().getLowAbsolute());
	}
}
