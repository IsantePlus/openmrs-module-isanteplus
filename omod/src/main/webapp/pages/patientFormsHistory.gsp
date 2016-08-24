<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isantepluspatientdashboard.formsHistory") ])
%>

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isantepluspatientdashboard.formsHistory') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

${ ui.includeFragment("isantepluspatientdashboard", "formsHistory") }
