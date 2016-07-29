<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isantepluspatientdashboard.growthCharts") ])
%>

<% ui.includeJavascript("isantepluspatientdashboard", "Chart.min.js") %>
<% ui.includeJavascript("isantepluspatientdashboard", "growthCharts.js") %>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patient.getPersonName().getFullName()}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
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
    </div>
    <div id="who">
    	${ ui.includeFragment("isantepluspatientdashboard", "whoGrowthCharts") }
    </div>
</div>

<script type="text/javascript">
    jQuery(function() {
    	jQuery(".standards-provider-tabs").tabs();
    	jQuery(".standards-provider-tabs-cdc").tabs();
    	
    	var wtageinfCDC = loadCSVIntoJson(getFileContentFromServer(wtageinfPath));
		var wtageinfPatient;//TODO support this
    	render_ChartJS_LineGraph_WeightAgeInf(wtageinfCDC, wtageinfPatient,"cdc_growth_charts", jQuery(".cdc_growth_charts_sex:checked:first").val());
    
    	jQuery('input.cdc_growth_charts_sex').on('change', function() {
		    jQuery('input.cdc_growth_charts_sex').not(this).prop('checked', !jQuery(this).is(':checked'));
		    
		    render_ChartJS_LineGraph_WeightAgeInf(wtageinfCDC, wtageinfPatient,"cdc_growth_charts", jQuery(".cdc_growth_charts_sex:checked:first").val());
		});
	});
</script>