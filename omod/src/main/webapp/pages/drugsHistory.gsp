<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("Drugs History") ])
%>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('drugs history') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.drugsHistory") }</h3>
    </div>
    <table>
    	<tr><th>${ ui.message("Medicaments") }</th><th>${ ui.message("Date") }</th></tr>
    <div class="info-body">
    	<% drugname.each { %>
    		<tr>
	    		<td>${ui.format(it.valueCoded)}</td>
	    		<td>${ui.format(it.obsDatetime)}</td>
	    		
    		</tr>
    	<% } %>
    	
    </div>
</div>