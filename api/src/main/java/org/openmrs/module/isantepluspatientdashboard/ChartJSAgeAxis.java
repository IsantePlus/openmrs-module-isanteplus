package org.openmrs.module.isantepluspatientdashboard;

public class ChartJSAgeAxis {

	private Integer startAge;

	private Integer lastAge;

	private Integer ageDifference;

	private AgeUnit ageUnit;

	public ChartJSAgeAxis(Integer startAge, Integer lastAge, Integer ageDifference, AgeUnit ageUnit) {
		setAgeDifference(ageDifference);
		setAgeUnit(ageUnit);
		setStartAge(startAge);
		setLastAge(lastAge);
	}

	public Integer getStartAge() {
		return startAge;
	}

	public void setStartAge(Integer startAge) {
		this.startAge = startAge;
	}

	public Integer getLastAge() {
		return lastAge;
	}

	public void setLastAge(Integer lastAge) {
		this.lastAge = lastAge;
	}

	public Integer getAgeDifference() {
		return ageDifference;
	}

	public void setAgeDifference(Integer ageDifference) {
		this.ageDifference = ageDifference;
	}

	public AgeUnit getAgeUnit() {
		return ageUnit;
	}

	public void setAgeUnit(AgeUnit ageUnit) {
		this.ageUnit = ageUnit;
	}

}
