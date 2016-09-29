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
    		
    		if(selectedHistory.length > 0) {
	    		jQuery.ajax({
					type: "POST",
					url: "${ ui.actionLink('deleteSelectedFormHistory') }",
					data: {selectedFormHistory: selectedHistory},
					dataType: "json",
					success: function(data) {
						location.reload();
					}
	    		});
    		}
    	});
    	
    	jQuery("#select_all_form_history").change(function(event) {
    		jQuery(".delete_form_history").prop("checked", jQuery("#select_all_form_history").is(":checked"));
    		event.preventDefault();
    	});
    });
</script>

${ ui.includeFragment("isanteplus", "isantePlusForms") }
<br />
<div id="forms_history_section">
	<div id="">
		<h3>${ ui.message("isanteplus.formsHistory") } (${ ui.message("isanteplus.formsHistory.filled") })</h3>
		<table>
			<tr>
				<th>${ ui.message("isanteplus.formsHistory.creationDate") }</th>
				<th>${ ui.message("isanteplus.formName") }</th>
				<th>${ ui.message("isanteplus.formsHistory.formStatus") }</th>
				<th>${ ui.message("isanteplus.formsHistory.provider") }</th>
				<th>${ ui.message("isanteplus.formsHistory.lastModification") }</th>
				<th>${ ui.message("isanteplus.formsHistory.dataEntry") }</th>
				<th>${ ui.message("general.remove") } <input type="checkbox" id="select_all_form_history" /></th>
			</tr>
			<% allFormHistory.each { %>
				<tr>
					<td>${ ui.format(it.date) }</td>
					<td><a href="/${appName}/htmlformentryui/htmlform/viewEncounterWithHtmlForm.page?patientId=${ ui.format(it.encounter.patient.uuid) }&encounter=${ ui.format(it.encounter.uuid) }" target="_blank">${ ui.format(it.encounter.form.name) }</a></td>
					<td>${ ui.format(it.formStatus) }</td>
					<td>${ ui.format(it.provider) }</td>
					<td>${ ui.format(it.encounter.encounterDatetime) }</td>
					<td>${ ui.format(it.enteredBy) }</td>
					<td><input type="checkbox" class="delete_form_history" id="${ ui.format(it.uuid) }"></td>
				</tr>
			<% } %>
		</table>
		<input type="button" id="delete_selected_formHistory" value="${ ui.message("isanteplus.formsHistory.deleteSelected") }" />
	</div>
</div>