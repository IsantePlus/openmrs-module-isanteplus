<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.lastDrugs") } </h3>
    </div>
    
    <div class="info-body">
    	<% lastDrugs.each { %>
    	<tr>
    		<td>${ ui.message("general.value") } : <b>${ui.format(it.valueCoded)}</b><td>
    		<td>${ ui.message("ActiveLists.date") } : <b>${ui.format(it.obsDatetime)}</b><td>
    	<tr>
    	<% } %>
    </div>
</div>