<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplus.labHistory") ])
%>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isanteplus.labHistory') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.labHistory") }</h3>
    </div>
    <table>
    	<tr><th>${ ui.message("isanteplus.testName") }</th><th>Date</th><th>${ ui.message("isanteplus.testResult") }</th><th>${ ui.message("isanteplus.minimumValue") }</th><th>${ ui.message("isanteplus.maximumValue") }</th></tr>
    <div class="info-body">
    	<% labresult.each { %>
    		<tr>
	    		<td>${ui.format(it.obs.concept)}</td>
	    		<td>${ui.format(it.obs.obsDatetime)}</td>
	    		<td>${ui.format(it.obs.valueNumeric)} ${ui.format(it.obs.valueCoded)} ${ui.format(it.obs.valueText)} <% if(it.obs.valueNumeric > 0) { %> ${ui.format(it.conceptNumeric.units)} <% } %></td>
    			<td><% if(it.obs.valueNumeric > 0) { %> ${ui.format(it.conceptNumeric.lowNormal)} <% } %></td>
    			<td> <% if(it.obs.valueNumeric > 0) { %>   ${ui.format(it.conceptNumeric.hiNormal)} <% } %></td>
    		</tr>
    	<% } %>
    </div>
</div>