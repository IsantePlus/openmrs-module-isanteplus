var moduleResourceRootPath = "../ms/uiframework/resource/isantepluspatientdashboard";
var bmiAgeRevPath = moduleResourceRootPath + "/cdc/csv/bmiagerev.csv";
var hcageinfPath = moduleResourceRootPath + "/cdc/csv/hcageinf.csv";
var lenageinfPath = moduleResourceRootPath + "/cdc/csv/lenageinf.csv";
var statagePath = moduleResourceRootPath + "/cdc/csv/statage.csv";
var wtagePath = moduleResourceRootPath + "/cdc/csv/wtage.csv";
var wtageinfPath = moduleResourceRootPath + "/cdc/csv/wtageinf.csv";
var wtleninfPath = moduleResourceRootPath + "/cdc/csv/wtleninf.csv";
var wtstatPath = moduleResourceRootPath + "/cdc/csv/wtstat.csv";
var growthChartCurveColors = {
	"P3" : "#5B57A6",
	"P5" : "#EEA616",
	"P10" : "#363463",
	"P25" : "#231F20",
	"P50" : "#007fff",
	"P75" : "#a1d030",
	"P90" : "cyan",
	"P95" : "#009384",
	"P97" : "red",
	"patient" : "#F26522",
	"Pub3" : "#808080",
	"Pub5" : "#808080",
	"Pub10" : "#808080",
	"Pub25" : "#808080",
	"Pub50" : "#808080",
	"Pub75" : "#808080",
	"Pub90" : "#808080",
	"Pub95" : "#808080",
	"Pub97" : "#808080",
	"Diff3" : "#808080",
	"Diff5" : "#808080",
	"Diff10" : "#808080",
	"Diff25" : "#808080",
	"Diff25" : "#808080",
	"Diff75" : "#808080",
	"Diff90" : "#808080",
	"Diff95" : "#808080",
	"Diff97" : "#808080",
	"P85" : "#4B0082"
};

function loadCSVIntoJson(csv, verticleScaler) {
	var lines = csv.split("\n");
	var result = [];
	var headers = lines[0].replace("\r", "").split(",");

	if (csv !== undefined) {
		for (var i = 1; i < lines.length; i++) {
			var obj = {};
			var currentline = lines[i].replace("\r", "").split(",");

			for (var j = 0; j < headers.length; j++) {
				obj[headers[j]] = currentline[j];
			}

			if (obj[verticleScaler] !== undefined)
				result.push(obj);
		}
		return JSON.stringify(result);
	}
}

function getFileContentFromServer(filePath) {
	var fetchedContent;

	jQuery.ajax({
		type : "GET",
		async : false,
		url : filePath,
		success : function(data) {
			fetchedContent = data;
		}
	});
	return fetchedContent;
}

function renderPatient_ChartJS_LineGraph_WeightAgeInf(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(wtageinfPath), "Agemos");

	drawInfantWeightOrHeightForAgeChartJSGraph(fetchedGrowthChartData,
			patientPropts, elementId, axisLabelNames);
}

function render_ChartJS_weightForAgeTwoToTwentyYears(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(wtagePath), "Agemos");

	drawTwoToTwentyWeightOrHeightForAgeChartJSGraph(patientPropts,
			fetchedGrowthChartData, elementId, axisLabelNames);
}

function render_ChartJS_statureForAgeTwoToTwentyYears(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(statagePath), "Agemos");

	drawTwoToTwentyWeightOrHeightForAgeChartJSGraph(patientPropts,
			fetchedGrowthChartData, elementId, axisLabelNames);
}

function drawTwoToTwentyWeightOrHeightForAgeChartJSGraph(patientPropts,
		fetchedGrowthChartData, elementId, axisLabelNames) {
	if (patientPropts.gender != undefined && patientPropts.age.years >= 2) {
		var data = setupBasicGrowthChatMeta(fetchedGrowthChartData,
				patientPropts, "Agemos", true);

		drawLineChartJSGraph(elementId, data, axisLabelNames);
	}
}

function renderPatient_ChartJS_LineGraph_HeightAgeInf(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(hcageinfPath), "Agemos");

	drawInfantWeightOrHeightForAgeChartJSGraph(fetchedGrowthChartData,
			patientPropts, elementId, axisLabelNames);
}

function renderPatient_ChartJS_LineGraph_LengthAgeInf(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(lenageinfPath), "Agemos");

	if (patientPropts.gender != undefined) {
		var labels = [];
		var p3Values = [];
		var p5Values = [];
		var p10Values = [];
		var p25Values = [];
		var p50Values = [];
		var p75Values = [];
		var p90Values = [];
		var p95Values = [];
		var p97Values = [];
		var patientValues = [];
		var filtered_fetchedGrowthChartData = [];
		var chartYears = [];
		var pub3Values = [];
		var pub5Values = [];
		var pub10Values = [];
		var pub25Values = [];
		var pub50Values = [];
		var pub75Values = [];
		var pub90Values = [];
		var pub95Values = [];
		var pub97Values = [];
		var diff3Values = [];
		var diff5Values = [];
		var diff10Values = [];
		var diff5Values = [];
		var diff25Values = [];
		var diff50Values = [];
		var diff75Values = [];
		var diff90Values = [];
		var diff95Values = [];
		var diff97Values = [];

		fetchedGrowthChartData = JSON.parse(fetchedGrowthChartData);

		for (i = 0; i < fetchedGrowthChartData.length; i++) {
			if (fetchedGrowthChartData[i].Sex == patientPropts.gender) {
				filtered_fetchedGrowthChartData.push(fetchedGrowthChartData[i]);
			}
		}
		filtered_fetchedGrowthChartData.sort(function(a, b) {
			return a.Agemos - b.Agemos;
		});
		for (i = 0; i < filtered_fetchedGrowthChartData.length; i++) {
			var label = Math.round(filtered_fetchedGrowthChartData[i].Agemos);

			if (jQuery.inArray(label, labels) < 0) {
				labels.push(label);
				p3Values.push(filtered_fetchedGrowthChartData[i].P3);
				p5Values.push(filtered_fetchedGrowthChartData[i].P5);
				p10Values.push(filtered_fetchedGrowthChartData[i].P10);
				p25Values.push(filtered_fetchedGrowthChartData[i].P25);
				p50Values.push(filtered_fetchedGrowthChartData[i].P50);
				p75Values.push(filtered_fetchedGrowthChartData[i].P75);
				p90Values.push(filtered_fetchedGrowthChartData[i].P90);
				p95Values.push(filtered_fetchedGrowthChartData[i].P95);
				p97Values.push(filtered_fetchedGrowthChartData[i].P97);
				pub3Values.push(fetchedGrowthChartData[i].Pub3);
				pub5Values.push(fetchedGrowthChartData[i].Pub5);
				pub10Values.push(fetchedGrowthChartData[i].Pub10);
				pub25Values.push(fetchedGrowthChartData[i].Pub25);
				pub50Values.push(fetchedGrowthChartData[i].Pub50);
				pub75Values.push(fetchedGrowthChartData[i].Pub75);
				pub90Values.push(fetchedGrowthChartData[i].Pub90);
				pub95Values.push(fetchedGrowthChartData[i].Pub95);
				pub97Values.push(fetchedGrowthChartData[i].Pub97);
				diff3Values.push(fetchedGrowthChartData[i].Diff3);
				diff5Values.push(fetchedGrowthChartData[i].Diff5);
				diff10Values.push(fetchedGrowthChartData[i].Diff10);
				diff25Values.push(fetchedGrowthChartData[i].Diff25);
				diff50Values.push(fetchedGrowthChartData[i].Diff50);
				diff75Values.push(fetchedGrowthChartData[i].Diff75);
				diff90Values.push(fetchedGrowthChartData[i].Diff90);
				diff95Values.push(fetchedGrowthChartData[i].Diff95);
				diff97Values.push(fetchedGrowthChartData[i].Diff97);
			}
		}
		var datasets = [ generate_ChartJS_dataset("P3", p3Values),
				generate_ChartJS_dataset("P5", p5Values),
				generate_ChartJS_dataset("P10", p10Values),
				generate_ChartJS_dataset("P25", p25Values),
				generate_ChartJS_dataset("P50", p50Values),
				generate_ChartJS_dataset("P75", p75Values),
				generate_ChartJS_dataset("P90", p90Values),
				generate_ChartJS_dataset("P95", p95Values),
				generate_ChartJS_dataset("P97", p97Values),
				generate_ChartJS_dataset("Pub3", pub3Values),
				generate_ChartJS_dataset("Pub5", pub5Values),
				generate_ChartJS_dataset("Pub10", pub10Values),
				generate_ChartJS_dataset("Pub25", pub25Values),
				generate_ChartJS_dataset("Pub50", pub50Values),
				generate_ChartJS_dataset("Pub75", pub75Values),
				generate_ChartJS_dataset("Pub90", pub90Values),
				generate_ChartJS_dataset("Pub95", pub95Values),
				generate_ChartJS_dataset("Pub97", pub97Values),
				generate_ChartJS_dataset("Diff3", diff3Values),
				generate_ChartJS_dataset("Diff5", diff5Values),
				generate_ChartJS_dataset("Diff10", diff10Values),
				generate_ChartJS_dataset("Diff25", diff25Values),
				generate_ChartJS_dataset("Diff50", diff50Values),
				generate_ChartJS_dataset("Diff75", diff75Values),
				generate_ChartJS_dataset("Diff90", diff90Values),
				generate_ChartJS_dataset("Diff95", diff95Values),
				generate_ChartJS_dataset("Diff97", diff97Values) ];

		var data = {
			labels : labels,
			datasets : datasets
		}

		drawLineChartJSGraph(elementId, data, axisLabelNames);
	}
}

function renderPatient_ChartJS_LineGraph_WeightLengthInf(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(wtleninfPath), "Length");

	if (patientPropts.gender != undefined) {
		var data = setupBasicGrowthChatMeta(fetchedGrowthChartData,
				patientPropts, "Length", false);

		drawLineChartJSGraph(elementId, data, axisLabelNames);
	}
}

function renderPatient_ChartJS_LineGraph_WeightStature(patientChartData,
		elementId, patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(wtstatPath), "Height");

	if (patientPropts.gender != undefined) {
		var data = setupBasicGrowthChatMeta(fetchedGrowthChartData,
				patientPropts, "Height");
		var p85Values = [];
		var filtered_fetchedGrowthChartData = filterFetchedGrowthChartData(
				fetchedGrowthChartData, patientPropts, "Height", false);

		for (i = 0; i < filtered_fetchedGrowthChartData.length; i++) {
			p85Values.push(filtered_fetchedGrowthChartData[i].P85);
		}

		data.datasets.push(generate_ChartJS_dataset("P85", p85Values));
		drawLineChartJSGraph(elementId, data, axisLabelNames);
	}
}

function renderPatient_ChartJS_LineGraph_bmiAge(patientChartData, elementId,
		patientPropts, axisLabelNames) {
	var fetchedGrowthChartData = loadCSVIntoJson(
			getFileContentFromServer(bmiAgeRevPath), "Agemos");

	if (patientPropts.gender != undefined && patientPropts.age.years >= 2) {
		var data = setupBasicGrowthChatMeta(fetchedGrowthChartData,
				patientPropts, "Agemos", true);
		var p85Values = [];
		var filtered_fetchedGrowthChartData = filterFetchedGrowthChartData(
				fetchedGrowthChartData, patientPropts, "Agemos", true);

		for (i = 0; i < filtered_fetchedGrowthChartData.length; i++) {
			p85Values.push(filtered_fetchedGrowthChartData[i].P85);
		}

		data.datasets.push(generate_ChartJS_dataset("P85", p85Values));
		drawLineChartJSGraph(elementId, data, axisLabelNames);
	}
}

function drawInfantWeightOrHeightForAgeChartJSGraph(fetchedGrowthChartData,
		patientPropts, elementId, axisLabelNames) {
	if (patientPropts.gender != undefined) {
		var data = setupBasicGrowthChatMeta(fetchedGrowthChartData,
				patientPropts, "Agemos");

		drawLineChartJSGraph(elementId, data, axisLabelNames);
	}
}

function setupBasicGrowthChatMeta(fetchedGrowthChartData, patientPropts,
		verticleScaler, labelsOrHolizontalIsInYears) {
	var labels = [];
	var p3Values = [];
	var p5Values = [];
	var p10Values = [];
	var p25Values = [];
	var p50Values = [];
	var p75Values = [];
	var p90Values = [];
	var p95Values = [];
	var p97Values = [];
	var patientValues = [];
	var filtered_fetchedGrowthChartData = filterFetchedGrowthChartData(
			fetchedGrowthChartData, patientPropts, verticleScaler);

	for (i = 0; i < filtered_fetchedGrowthChartData.length; i++) {
		var label = labelsOrHolizontalIsInYears == true ? Math
				.round(filtered_fetchedGrowthChartData[i][verticleScaler] / 12)
				: Math
						.round(filtered_fetchedGrowthChartData[i][verticleScaler]);

		if (jQuery.inArray(label, labels) < 0) {
			labels.push(label);
			p3Values.push(filtered_fetchedGrowthChartData[i].P3);
			p5Values.push(filtered_fetchedGrowthChartData[i].P5);
			p10Values.push(filtered_fetchedGrowthChartData[i].P10);
			p25Values.push(filtered_fetchedGrowthChartData[i].P25);
			p50Values.push(filtered_fetchedGrowthChartData[i].P50);
			p75Values.push(filtered_fetchedGrowthChartData[i].P75);
			p90Values.push(filtered_fetchedGrowthChartData[i].P90);
			p95Values.push(filtered_fetchedGrowthChartData[i].P95);
			p97Values.push(filtered_fetchedGrowthChartData[i].P97);
		}
	}
	var datasets = [ generate_ChartJS_dataset("P3", p3Values),
			generate_ChartJS_dataset("P5", p5Values),
			generate_ChartJS_dataset("P10", p10Values),
			generate_ChartJS_dataset("P25", p25Values),
			generate_ChartJS_dataset("P50", p50Values),
			generate_ChartJS_dataset("P75", p75Values),
			generate_ChartJS_dataset("P90", p90Values),
			generate_ChartJS_dataset("P95", p95Values),
			generate_ChartJS_dataset("P97", p97Values) ];

	return {
		labels : labels,
		datasets : datasets
	};
}

function filterFetchedGrowthChartData(fetchedGrowthChartData, patientPropts,
		verticleScaler, labelsOrHolizontalIsInYears) {
	var filtered_fetchedGrowthChartData = [];
	var chartYears = [];

	fetchedGrowthChartData = JSON.parse(fetchedGrowthChartData);

	for (i = 0; i < fetchedGrowthChartData.length; i++) {
		if (fetchedGrowthChartData[i].Sex == patientPropts.gender) {
			if (labelsOrHolizontalIsInYears == true) {
				var year = Math
						.round(fetchedGrowthChartData[i][verticleScaler] / 12);

				if (jQuery.inArray(year, chartYears) < 0) {
					chartYears.push(year);
					filtered_fetchedGrowthChartData
							.push(fetchedGrowthChartData[i]);
				}
			} else {
				filtered_fetchedGrowthChartData.push(fetchedGrowthChartData[i]);
			}
		}
	}

	filtered_fetchedGrowthChartData.sort(function(a, b) {
		return a[verticleScaler] - b[verticleScaler];
	});

	return filtered_fetchedGrowthChartData;
}

function drawLineChartJSGraph(elementId, data, axisLabelNames) {
	var ctx = document.getElementById(elementId);
	var myChart = new Chart(ctx, {
		type : 'line',
		data : data,
		options : {
			scales : {
				xAxes: [{
	            	scaleLabel: {
					        display: true,
					        labelString: axisLabelNames.x
					      }
	            }],
				yAxes : [ {
					ticks : {
						beginAtZero : false
					},
					scaleLabel: {
				        display: true,
				        labelString: axisLabelNames.y
				      }
				} ]
			}
		}
	});
}

function generate_ChartJS_dataset(labelHackCode, dataValues) {
	return {
		"label" : labelHackCode,
		"data" : dataValues,
		"borderColor" : growthChartCurveColors[labelHackCode],
		"borderWidth" : ((labelHackCode == "patient") ? 2 : 1)
	};
}

function resetChartJSCanvas(canvasElementId) {
	$('#' + canvasElementId).remove();
	$('#' + canvasElementId + "-container").append(
			'<canvas id="' + canvasElementId + '"><canvas>');
}

function indentifySelectedCdcLink(linkElementId) {
	jQuery(".growthChartCDCLink").css({
		color : "",
		"font-weight" : "",
		"font-size" : ""
	});
	jQuery("#" + linkElementId).css({
		color : "#363463",
		"font-weight" : "bold",
		"font-size" : "115%"
	});
}
