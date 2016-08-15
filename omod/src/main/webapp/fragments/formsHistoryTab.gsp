<style>
	#delete_selected_formHistory{
		float: right;
	}
</style>

<div id="forms_history_section">
	<div id="">
		<h3>${ ui.message("isantepluspatientdashboard.formsHistory.filled") }</h3>
		<table>
			<tr>
				<th>${ ui.message("Encounter.form") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.lastModification") }</th>
				<th>${ ui.message("isantepluspatientdashboard.formsHistory.formStatus") }</th>
				<th>${ ui.message("general.remove") }</th>
			</tr>
			<% allFormHistory.each { %>
				<tr>
					<td><a href="../../htmlformentryui/htmlform/viewEncounterWithHtmlForm.page?patientId=${ ui.format(it.encounter.patient.uuid) }&visitId=${ ui.format(it.visit.visitId) }&encounter=${ ui.format(it.encounter.uuid) }" target="_blank">${ ui.format(it.encounter.form.name) } - ${ ui.format(it.encounter.form.version) }</a></td>
					<td>${ ui.format(it.encounter.encounterDatetime) }</td>
					<td></td>
					<td><input type="checkbox" class="delete_form_history" id="${ ui.format(it.uuid) }"></td>
				</tr>
			<% } %>
		</table>
		<input type="button" id="delete_selected_formHistory" value="${ ui.message("isantepluspatientdashboard.formsHistory.deleteSelected") }" />
	</div>
</div>