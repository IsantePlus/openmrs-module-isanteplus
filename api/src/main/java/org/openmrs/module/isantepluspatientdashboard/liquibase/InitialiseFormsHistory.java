package org.openmrs.module.isantepluspatientdashboard.liquibase;

import org.openmrs.api.context.Context;
import org.openmrs.module.isantepluspatientdashboard.api.IsantePlusPatientDashboardService;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

/**
 * This liquibase custom change class executes as the module is installed once
 * and goes through the current database visits and persists form history out of
 * it. <br />
 * TODO, for future form history from now this mmodule should use AOP to write a
 * solution thereof
 * 
 * @author k-joseph
 *
 */
public class InitialiseFormsHistory implements CustomTaskChange {

	@Override
	public String getConfirmationMessage() {
		return "Successfully Initialised of created form history for all exisitng patient's visits in the database";
	}

	@Override
	public void setFileOpener(ResourceAccessor arg0) {
	}

	@Override
	public void setUp() throws SetupException {
	}

	@Override
	public ValidationErrors validate(Database arg0) {
		return null;
	}

	@Override
	public void execute(Database arg0) throws CustomChangeException {
		Context.getService(IsantePlusPatientDashboardService.class).runInitialHistoryCreatorAgainstDB();
	}

}
