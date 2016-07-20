package org.openmrs.module.isantepluspatientdashboard.vis;

import org.json.JSONArray;
import org.json.JSONObject;

public class VisLineGraphing {

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static JSONObject getOptions(String startDate, String endDate) {
		JSONObject opts = new JSONObject();
		JSONObject shaded = new JSONObject();

		shaded.put("enabled", true);
		//done automatically
		//opts.put("start", startDate);
		//opts.put("end", endDate);
		opts.put("shaded", shaded);

		return opts;
	}

	/**
	 * @param weightsItems
	 * @return
	 */
	public static JSONArray getWeightsGraphsItems(JSONArray weightsItems) {
		JSONArray jsonArrayItems = new JSONArray();

		for (int i = 0; i < weightsItems.length(); i++) {
			JSONObject coordinate = new JSONObject();

			coordinate.put("x", weightsItems.getJSONObject(i).getString("date"));
			coordinate.put("y", weightsItems.getJSONObject(i).getLong("weight"));
			jsonArrayItems.put(coordinate);
		}

		return jsonArrayItems;
	}
}
