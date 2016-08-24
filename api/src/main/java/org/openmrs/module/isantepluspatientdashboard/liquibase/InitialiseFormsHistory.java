package org.openmrs.module.isantepluspatientdashboard.liquibase;

import java.sql.SQLException;

import org.openmrs.module.isantepluspatientdashboard.api.impl.IsantePlusPatientDashboardServiceImpl;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

/**
 * This liquibase custom change class executes as the module is installed once
 * and goes through the current database visits and persists form history out of
 * it. <br />
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
	public void execute(Database database) throws CustomChangeException {
		JdbcConnection connection = (JdbcConnection) database.getConnection();

		// Hacking service, looks like at this stage module's services are not
		// yet ready
		try {
			new IsantePlusPatientDashboardServiceImpl().runInitialHistoryCreatorAgainstDB(connection);
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
