<input type="checkbox" class="managerOption" id="${manager.visitFormHistoryExtensionId}" ${manager.visitFormHistoryExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableVisitFormHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.patientFormHistoryExtensionId}" ${manager.patientFormHistoryExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enablePatientFormHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.growthChartsExtensionId}" ${manager.growthChartsExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableGrowthCharts') }<br />
<input type="checkbox" class="managerOption" id="${manager.lastViralLoadTestExtensionId}" ${manager.lastViralLoadTestExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableLastViralLoadTest') }<br />
<input type="checkbox" class="managerOption" id="${manager.lastDrugsExtensionId}" ${manager.lastDrugsExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableLastDrugs') }<br />
<input type="checkbox" class="managerOption" id="${manager.weightsGraphExtensionId}" ${manager.weightsGraphExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableWeightsGraph') }<br />
<input type="checkbox" class="managerOption" id="${manager.labHistoryExtensionId}" ${manager.labHistoryExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableLabHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.isantePlusFormsExtensionId}" ${manager.isantePlusFormsExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableIsantePlusForms') }<br />
<input type="checkbox" class="managerOption" id="${manager.mostRecentVitalsExtensionId}" ${manager.mostRecentVitalsExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableMostRecentVitals') }<br />
<input type="checkbox" class="managerOption" id="${manager.drugsHistoryExtensionId}" ${manager.drugsHistoryExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableDrugsHistory') }<br />
<input type="checkbox" class="managerOption" id="${manager.bmiGraphExtensionId}" ${manager.bmiGraphExtensionChecked}> ${ ui.message('isanteplus.manageIsantePlusPatientDashboard.enableBmiGraph') }<br />
	
<input type="button" id="submitIsantePlusManagerForm" value="${ ui.message('general.save') }">

<script type="text/javascript">
	 jQuery(function() {
	 	jQuery("#submitIsantePlusManagerForm").click(function(event) {
	 		var data = {};
	 		
	 		jQuery('.managerOption').each(function() {
				data[jQuery(this).attr("id")] = jQuery(this).is(':checked');
			});
			
	 		jQuery.ajax({
				type : "POST",
				data: {"extensions": JSON.stringify(data)},
				url : "${ ui.actionLink('submitIsantePlusManagerForm') }",
				success : function() {
					location.reload();
				}
			});
	 	});
	 });
</script>