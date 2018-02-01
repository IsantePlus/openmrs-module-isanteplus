package org.openmrs.module.isanteplus.vis;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		if (startDate != null)
			opts.put("start", startDate);
		if (endDate != null)
			opts.put("end", endDate);
		opts.put("shaded", shaded);
		opts.put("clickToUse", true);

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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
			Date date = (Date) weightsItems.getJSONObject(i).get("measureDate");

			coordinate.put("x", date != null ? sdf.format(date) : "");
			coordinate.put("y", weightsItems.getJSONObject(i).getLong("weight"));
			jsonArrayItems.put(coordinate);
		}

		return jsonArrayItems;
	}

	public static JSONArray getGrowthChartsItems(JSONArray growthChartPatientRestrictedData) {
		JSONArray jsonArrayItems = new JSONArray();

		return jsonArrayItems;
	}
	
	public static JSONArray getBmisGraphsItems(JSONArray bmisItems) {
		JSONArray jsonArrayItems = new JSONArray();
		for (int i = 0; i < bmisItems.length(); i++) {
			JSONObject coordinate = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
			Date date = (Date) bmisItems.getJSONObject(i).get("measureDate");

			coordinate.put("x", date != null ? sdf.format(date) : "");
			coordinate.put("y", bmisItems.getJSONObject(i).getLong("bmivalues"));
			jsonArrayItems.put(coordinate);
		}

		return jsonArrayItems;
	}
}
