<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplus.vaccination") ])
%>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientVaccination.names}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isanteplus.vaccination') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<% if(encounterUuid == null) { %>
	${ ui.message('isanteplus.immunization.message') }
<% } %>