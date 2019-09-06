<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplus.diagnosis.label") ])
%>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isanteplus.diagnosis.label') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.diagnosis.label") }</h3>
    </div>
    <table>
    	<tr><th>${ ui.message("isanteplus.diagnoses.label") }</th><th>${ ui.message("isanteplus.diagnosis.startedDate") }</th><th>${ ui.message("isanteplus.diagnosisStatusActive") }</th><th>${ ui.message("isanteplus.diagnosisStatusResolved") }</th></tr>
    <div class="info-body">
    	<% allDiagnosis.each { %>
    		<tr>
    			<td>${ui.format(it.obs.valueCoded)}</td>
	    		<td>${ui.format(it.obs.obsDatetime)}</td>
	    		<td>
	    			<center>
	    			<% if(it.obs.concept.conceptId == 6042) { %>
	    				
	    				    ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "active", "label": "", "formFieldName": "active", "checked": "checked" ]) }
	    				
	    			<% } %>
	    			</center>
	    		</td>
	    		<td>
	    			<center>
		    			<% if(it.obs.concept.conceptId == 6097) { %>
		    				
		    				    ${ ui.includeFragment("uicommons", "field/checkbox", [ "id": "resolved", "label": "", "formFieldName": "resolved", "checked": "checked" ]) }
		    				
		    			<% } %>
	    			</center>
	    			
	    		</td>
    		</tr>
    	<% } %>
    </div>
</div>