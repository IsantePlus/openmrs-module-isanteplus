<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isantepluspatientdashboard.labHistory") ])
%>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isantepluspatientdashboard.labHistory') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.labHistory") }</h3>
    </div>
    <table>
    	<tr><th>${ ui.message("isantepluspatientdashboard.testName") }</th><th>Date</th><th>${ ui.message("isantepluspatientdashboard.testResult") }</th></tr>
    <div class="info-body">
    	<% labresult.each { %>
    		<tr>
	    		<td>${ui.format(it.concept)}</td>
	    		<td>${ui.format(it.obsDatetime)}</td>
	    		<td>${ui.format(it.valueNumeric)} ${ui.format(it.valueCoded)} ${ui.format(it.valueText)} ${ui.format(it.concept.getUnits())}</td>
    		</tr>
    	<% } %>
    </div>
</div>