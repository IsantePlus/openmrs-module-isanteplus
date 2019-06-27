package org.openmrs.module.isanteplus;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Obs;

public class IsantePlusVital {
	private String label;

	private String value;

	private String unit;

	public String getLabel() {
		return label;
	}

	public IsantePlusVital(String label, Obs obs, String unit) {
		setLabel(label);
		setValue(obs != null ? String.valueOf(obs.getValueNumeric()) : "____");
		setUnit(unit);
	}

	public IsantePlusVital(String label, Double value, String unit) {
		setLabel(StringUtils.isNotBlank(label) ? label : "");
		setValue(value != null ? String.valueOf(value) : "____");
		setUnit(StringUtils.isNotBlank(unit) ? unit : "");
	}

	public IsantePlusVital(String label, String value, String unit) {
		setLabel(StringUtils.isNotBlank(label) ? label : "");
		setValue(StringUtils.isNotBlank(value) ? value : "____");
		setUnit(StringUtils.isNotBlank(unit) ? unit : "");
	}

	public IsantePlusVital() {
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
