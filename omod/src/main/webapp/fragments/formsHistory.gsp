<style>
	#delete_selected_formHistory{
		float: right;
	}
</style>

<script type="text/javascript">

	function returnuuidsOfSeletedHistory() {
	   	var selectedHistoryUuids = [];
	    	
		jq('.delete_form_history:checked').each(function() {
			var selectedId = jq(this).attr("id");
				
			selectedHistoryUuids.push(selectedId);
		});
		return selectedHistoryUuids;
    }
    	
    jQuery(function() {
    	jQuery("#delete_selected_formHistory").click(function(event) {
    		var selectedHistory = returnuuidsOfSeletedHistory();
    		var message = "${ ui.message("coreapps.patientDashBoard.deleteEncounter.successMessage") }";
    		var question = "${ ui.message("coreapps.patientDashBoard.deleteEncounter.message") }";
    		if(selectedHistory.length > 0) {
    			if (confirm(question)){
		    		jQuery.ajax({
						type: "POST",
						url: "${ ui.actionLink('deleteSelectedFormHistory') }",
						data: {selectedFormHistory: selectedHistory},
						dataType: "json",
						success: function(data) {
							alert(message);
							location.reload();
						}
		    		});
	    		}
	    	
    		}
    	});
    	
    	jQuery("#select_all_form_history").change(function(event) {
    		jQuery(".delete_form_history").prop("checked", jQuery("#select_all_form_history").is(":checked"));
    		event.preventDefault();
    	});
    });
    jQuery(document).ready(function() {
	    jQuery(".tabs").tabs({active: 1});
	});
	
	
	
</script>

${ ui.includeFragment("isanteplus", "isantePlusForms") }
<br />
<div id="forms_history_section">
	<div id="">
		<h3>${ ui.message("isanteplus.formsHistory") } (${ ui.message("isanteplus.formsHistory.filled") })</h3>
		<table>
			<tr>
				<th>${ ui.message("isanteplus.formsHistory.visitDate") }</th>
				<th>${ ui.message("isanteplus.formName") }</th>
				<th>${ ui.message("isanteplus.formsHistory.formStatus") }</th>
				<th>${ ui.message("isanteplus.formsHistory.provider") }</th>
				<th>${ ui.message("isanteplus.formsHistory.creationDate") }</th>
				<th>${ ui.message("isanteplus.formsHistory.lastModification") }</th>
				<th>${ ui.message("isanteplus.formsHistory.dataEntry") }</th>
				<th><i class="viewEncounter view-action icon-file-alt" data-mode="view" title="${ ui.message("isanteplus.encounter.view") }"></i></th>
				<th>${ ui.message("general.remove") } <input type="checkbox" id="select_all_form_history" /></th>
			</tr>
			<% allFormHistory.each { %>
				<tr> <!-- class="icon-file-alt" to view encounter-->
					<td>${ ui.format(it.encounter.visit.startDatetime) }</td>
					<td>
					<a href="/${appName}/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId=${ ui.format(it.encounter.patient.uuid) }&encounterId=${ ui.format(it.encounter.uuid) }" target="_blank">${ ui.format(it.encounter.form.name) }</a>
					</td>
					<td>${ ui.format(it.formStatus) }</td>
					<td>${ ui.format(it.provider) }</td>
					<td>${ ui.format(it.encounter.dateCreated) }</td>
					<td>${ ui.format(it.dateChanged) }</td>
					<td>${ ui.format(it.enteredBy) }</td>
					<td><a href="/${appName}/htmlformentryui/htmlform/viewEncounterWithHtmlForm.page?patientId=${ ui.format(it.encounter.patient.uuid) }&encounter=${ ui.format(it.encounter.uuid) }">
					<i class="viewEncounter view-action icon-file-alt" data-mode="view" title="${ ui.message("isanteplus.encounter.view") }"></i>
					</a></td>
					<td><input type="checkbox" class="delete_form_history" id="${ ui.format(it.uuid) }"></td>
				</tr>
			<% } %>
		</table>
		<input type="button" id="delete_selected_formHistory" value="${ ui.message("isanteplus.formsHistory.deleteSelected") }" />
	</div>
</div>