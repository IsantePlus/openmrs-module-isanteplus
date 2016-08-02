<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isantepluspatientdashboard.growthCharts") ])
%>

<% ui.includeJavascript("isantepluspatientdashboard", "Chart.min.js") %>
<% ui.includeJavascript("isantepluspatientdashboard", "growthCharts.js") %>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isantepluspatientdashboard.growthCharts') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<div class="standards-provider-tabs">
	<ul>
	    <li>
	        <a href="#cdc" >
	             ${ ui.message("isantepluspatientdashboard.cdc") }
	        </a>
	    </li>
	    <li>
	    	<a href="#who">
	        	${ ui.message("isantepluspatientdashboard.who") }
	        </a>
	    </li>
    </ul>
    
    <div id="cdc">
    	${ ui.includeFragment("isantepluspatientdashboard", "cdcGrowthCharts") }
    	<div id="cdc_growth_charts-container">
    		<canvas id="cdc_growth_charts"></canvas>
    	</div>
    </div>
    <div id="who">
    	${ ui.includeFragment("isantepluspatientdashboard", "whoGrowthCharts") }
    	<div id="who_growth_charts-container">
    		<canvas id="who_growth_charts"></canvas>
    	</div>
    </div>
</div>

<script type="text/javascript">
    jQuery(function() {
    	jQuery(".standards-provider-tabs").tabs();
    	
    	var patientPropts = ${patientPropts};
    	var chartAxisLabels = ${chartAxisLabels}
    	//TODO support these below
		var wtageinfPatient;
		var hcageinfPatient;
		var wtagePatient;
		var statagePatient;
		var wtleninfPatient;
		var lenageinfPatient;
		var wtstatPatient;
		var bmiAgeRevPatient;
		var whoWeightForAgePatient;
    
    	jQuery('#WTAGEINF').trigger('click');
    	
    	jQuery("#WTAGEINF").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		renderPatient_ChartJS_LineGraph_WeightAgeInf(wtageinfPatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.WTAGEINF_x, y: chartAxisLabels.WTAGEINF_y});
    		indentifySelectedCdcLink("WTAGEINF");
    		
    		event.preventDefault();
    	});
    	jQuery("#LENAGEINF").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		renderPatient_ChartJS_LineGraph_LengthAgeInf(lenageinfPatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.LENAGEINF_x, y: chartAxisLabels.LENAGEINF_y});
    		indentifySelectedCdcLink("LENAGEINF");
    		
    		event.preventDefault();
    	});
    	jQuery("#WTLENINF").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		renderPatient_ChartJS_LineGraph_WeightLengthInf(wtleninfPatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.WTLENINF_x, y: chartAxisLabels.WTLENINF_y});
    		indentifySelectedCdcLink("WTLENINF");
    		
    		event.preventDefault();
    	});
    	jQuery("#HCAGEINF").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		renderPatient_ChartJS_LineGraph_HeightAgeInf(hcageinfPatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.HCAGEINF_x, y: chartAxisLabels.HCAGEINF_y});
    		indentifySelectedCdcLink("HCAGEINF");
    		
    		event.preventDefault();
    	});
    	jQuery("#WTSTAT").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		renderPatient_ChartJS_LineGraph_WeightStature(wtstatPatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.WTSTAT_x, y: chartAxisLabels.WTSTAT_y});
    		indentifySelectedCdcLink("WTSTAT");
    		
    		event.preventDefault();
    	});
    	jQuery("#WTAGE").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		render_ChartJS_weightForAgeTwoToTwentyYears(wtagePatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.WTAGE_x, y: chartAxisLabels.WTAGE_y});
    		indentifySelectedCdcLink("WTAGE");
    		
    		event.preventDefault();
    	});
    	jQuery("#STATAGE").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		render_ChartJS_statureForAgeTwoToTwentyYears(statagePatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.STATAGE_x, y: chartAxisLabels.STATAGE_y});
    		indentifySelectedCdcLink("STATAGE");
    		
    		event.preventDefault();
    	});
    	jQuery("#BMIAGE").click(function(event) {
    		resetChartJSCanvas("cdc_growth_charts");
    		renderPatient_ChartJS_LineGraph_bmiAge(bmiAgeRevPatient,"cdc_growth_charts", patientPropts, {x: chartAxisLabels.BMIAGE_x, y: chartAxisLabels.BMIAGE_y});
    		indentifySelectedCdcLink("BMIAGE");
    		
    		event.preventDefault();
    	});
    	
    	jQuery("#WFA").click(function(event) {
    		resetChartJSCanvas("who_growth_charts");
    		renderMoHPatient_ChartJS_LineGraph_WeightForAge(whoWeightForAgePatient,"who_growth_charts", patientPropts, {x: chartAxisLabels.WFA_x, y: chartAxisLabels.WFA_y});
    		indentifySelectedCdcLink("WFA", "who");
    		
    		event.preventDefault();
    	});
    	
    	jQuery("#WFL").click(function(event) {
    		resetChartJSCanvas("who_growth_charts");
    		renderMoHPatient_ChartJS_LineGraph_WeightForLength(whoWeightForAgePatient,"who_growth_charts", patientPropts, {x: chartAxisLabels.WFL_x, y: chartAxisLabels.WFL_y});
    		indentifySelectedCdcLink("WFL", "who");
    		
    		event.preventDefault();
    	});
    	
    	jQuery("#BFA").click(function(event) {
    		resetChartJSCanvas("who_growth_charts");
    		renderMoHPatient_ChartJS_LineGraph_BMIForAge(whoWeightForAgePatient,"who_growth_charts", patientPropts, {x: chartAxisLabels.BFA_x, y: chartAxisLabels.BFA_y});
    		indentifySelectedCdcLink("BFA", "who");
    		
    		event.preventDefault();
    	});
    	
    	jQuery("#HCFA").click(function(event) {
    		resetChartJSCanvas("who_growth_charts");
    		renderMoHPatient_ChartJS_LineGraph_HeadCircumferenceForAge(whoWeightForAgePatient,"who_growth_charts", patientPropts, {x: chartAxisLabels.HCFA_x, y: chartAxisLabels.HCFA_y});
    		indentifySelectedCdcLink("HCFA", "who");
    		
    		event.preventDefault();
    	});
    	
    	jQuery("#LHFA").click(function(event) {
    		resetChartJSCanvas("who_growth_charts");
    		renderMoHPatient_ChartJS_LineGraph_LengthForAge(whoWeightForAgePatient,"who_growth_charts", patientPropts, {x: chartAxisLabels.LHFA_x, y: chartAxisLabels.LHFA_y});
    		indentifySelectedCdcLink("LHFA", "who");
    		
    		event.preventDefault();
    	});
    	
	});
</script>