<input type="checkbox" class="managerOption" id="${manager.visitFormHistoryExtensionId}" ${manager.visitFormHistoryExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enableVisitFormHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.patientFormHistoryExtensionId}" ${manager.patientFormHistoryExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enablePatientFormHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.growthChartsExtensionId}" ${manager.growthChartsExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enableGrowthCharts') }<br />
<input type="checkbox" class="managerOption" id="${manager.lastViralLoadTestExtensionId}" ${manager.lastViralLoadTestExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enableLastViralLoadTest') }<br />
<input type="checkbox" class="managerOption" id="${manager.weightsGraphExtensionId}" ${manager.weightsGraphExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enableWeightsGraph') }<br />
<input type="checkbox" class="managerOption" id="${manager.labHistoryExtensionId}" ${manager.labHistoryExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enableLabHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.isantePlusFormsExtensionId}" ${manager.isantePlusFormsExtensionChecked}> ${ ui.message('isantepluspatientdashboard.manageIsantePlusPatientDashboard.enableIsantePlusForms') }<br />
	
<input type="button" id="submitIsantePlusPatientDashboardManagerForm" value="${ ui.message('general.save') }">

<script type="text/javascript">
	 jQuery(function() {
	 	jQuery("#submitIsantePlusPatientDashboardManagerForm").click(function(event) {
	 		var data = {};
	 		
	 		jQuery('.managerOption').each(function() {
				data[jQuery(this).attr("id")] = jQuery(this).is(':checked');
			});
			
	 		jQuery.ajax({
				type : "POST",
				data: {"extensions": JSON.stringify(data)},
				url : "${ ui.actionLink('submitIsantePlusPatientDashboardManagerForm') }",
				success : function() {
					location.reload();
				}
			});
	 	});
	 });
</script>