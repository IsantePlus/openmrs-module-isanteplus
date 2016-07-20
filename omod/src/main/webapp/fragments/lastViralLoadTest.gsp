<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.lastViralLoadTest") } </h3>
    </div>
    
    <div class="info-body">
    	<% if (lastViralLoadTest != null) { %>
    		${ ui.message("general.value") } : <b>${lastViralLoadTest.valueNumeric}</b>,
    		${ ui.message("ActiveLists.date") } : <b>${lastViralLoadTest.obsDatetime}</b>
    	<% } %>
    </div>
</div>