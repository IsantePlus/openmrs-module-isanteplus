<%
    ui.includeJavascript("uicommons", "angular.min.js")
%>

<script type="text/javascript">
    
</script>

<style>
	#delete_selected_formHistory{
		float: right;
	}
</style>

<div id="forms_history_section">
	<div id="">
		<h3>${ ui.message("isantepluspatientdashboard.formsHistory.categorised") }</h3>
		<p>
			<b>${ ui.message("isantepluspatientdashboard.formsHistory.categorised.care") }</b>
		</p>
		<p>
			<b>${ ui.message("isantepluspatientdashboard.formsHistory.categorised.lab") }</b>
		</p>
		<p>
			<b>${ ui.message("isantepluspatientdashboard.formsHistory.categorised.gyn") }</b>
		</p>
		<p>
			<b>${ ui.message("isantepluspatientdashboard.formsHistory.categorised.hiv") }</b>
		</p>
		<p>
			<b>${ ui.message("isantepluspatientdashboard.formsHistory.categorised.transfer") }</b>
		</p>
	</div>
	<div id="">
		<h3>${ ui.message("isantepluspatientdashboard.formsHistory.filled") }</h3>
		<p>
			<table>
				<tr>
					<th>${ ui.message("Encounter.form") }</th>
					<th>${ ui.message("isantepluspatientdashboard.formsHistory.lastModification") }</th>
					<th>${ ui.message("general.remove") }</th>
				</tr>
			</table>
			<input type="button" id="delete_selected_formHistory" value="${ ui.message("isantepluspatientdashboard.deleteSelected") }" />
		</p>
	</div>
</div>
${allFormHistory}