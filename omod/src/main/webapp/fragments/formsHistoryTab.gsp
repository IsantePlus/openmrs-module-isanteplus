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
    });
</script>

<div id="forms_history_section">
	<div id="">
		<h3>${ ui.message("isantepluspatientdashboard.formsHistory.filled") }</h3>
		<table>
			<tr>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.creationDate") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formName") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.formStatus") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.provider") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.lastModification") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.dataEntry") }</th>
				<th>${ ui.message("general.remove") }</th>
			</tr>
			<% allFormHistory.each { %>
				<tr>
					<td>${ ui.format(it.date) }</td>
					<td><a href="../../htmlformentryui/htmlform/viewEncounterWithHtmlForm.page?patientId=${ ui.format(it.encounter.patient.uuid) }&visitId=${ ui.format(it.visit.visitId) }&encounter=${ ui.format(it.encounter.uuid) }" target="_blank">${ ui.format(it.encounter.form.name) }</a></td>
					<td>${ ui.format(it.formStatus) }</td>
					<td>${ ui.format(it.provider) }</td>
					<td>${ ui.format(it.encounter.encounterDatetime) }</td>
					<td>${ ui.format(it.enteredBy) }</td>
					<td><input type="checkbox" class="delete_form_history" id="${ ui.format(it.uuid) }"></td>
				</tr>
			<% } %>
		</table>
		<input type="button" id="delete_selected_formHistory" value="${ ui.message("isantepluspatientdashboard.formsHistory.deleteSelected") }" />
	</div>
</div>