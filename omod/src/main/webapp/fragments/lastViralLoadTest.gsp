<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.lastViralLoadTest") } </h3>
    </div>
    
    <div class="info-body">
    	<% if (lastViralLoadTest != null) { %>
    		${ ui.message("general.value") } : ${lastViralLoadTest.valueNumeric}
    		${ ui.message("ActiveLists.date") } : ${lastViralLoadTest.obsDatetime}
    	<% } %>
    </div>
</div>