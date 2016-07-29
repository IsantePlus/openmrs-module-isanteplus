var moduleResourceRootPath = "../ms/uiframework/resource/isantepluspatientdashboard";
var cdcBmiAgeRevPath = moduleResourceRootPath + "/cdc/csv/bmiagerev.csv";
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
	"patient" : "#F26522"
};

function loadCSVIntoJson(csv) {
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

			if (obj.Agemos !== undefined)
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

function render_ChartJS_LineGraph_WeightAgeInf(fetchedGrowthChartData,
		patientChartData, elementId, sex) {
	var labels = []
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
	var sexFiltered_fetchedGrowthChartData = [];

	fetchedGrowthChartData = JSON.parse(fetchedGrowthChartData);
	for (i = 0; i < fetchedGrowthChartData.length; i++) {
		if (fetchedGrowthChartData[i].Sex === sex) {
			sexFiltered_fetchedGrowthChartData.push(fetchedGrowthChartData[i]);
		}
	}

	sexFiltered_fetchedGrowthChartData.sort(function(a, b) {
		return a.Agemos - b.Agemos;
	});

	for (i = 0; i < sexFiltered_fetchedGrowthChartData.length; i++) {
		labels.push(sexFiltered_fetchedGrowthChartData[i].Agemos);
		p3Values.push(sexFiltered_fetchedGrowthChartData[i].P3);
		p5Values.push(sexFiltered_fetchedGrowthChartData[i].P5);
		p10Values.push(sexFiltered_fetchedGrowthChartData[i].P10);
		p25Values.push(sexFiltered_fetchedGrowthChartData[i].P25);
		p50Values.push(sexFiltered_fetchedGrowthChartData[i].P50);
		p75Values.push(sexFiltered_fetchedGrowthChartData[i].P75);
		p90Values.push(sexFiltered_fetchedGrowthChartData[i].P90);
		p95Values.push(sexFiltered_fetchedGrowthChartData[i].P95);
		p97Values.push(sexFiltered_fetchedGrowthChartData[i].P97);
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

	var ctx = document.getElementById(elementId);
	var myChart = new Chart(ctx, {
		type : 'line',
		data : {
			labels : labels,
			datasets : datasets
		},
		options : {
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
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
		"borderWidth" : 1
	};
}
