<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.lastViralLoadTest") } </h3>
    </div>
    
    <div class="info-body">
    	<% if (lastViralLoadTest != null) { %>
    		${ ui.message("general.value") } : <b>
    		<% if (lastViralLoadTest.valueNumeric != null) { %> ${lastViralLoadTest.valueNumeric} <% } %>
    		<% if (lastViralLoadTest.valueCoded != null) { %>
    		<% if (lastViralLoadTest.valueCoded.conceptId == 1301) { %> ${ ui.message("isanteplus.viralLoad.detected") } <% } %> 
    		<% if (lastViralLoadTest.valueCoded.conceptId == 1302) { %> ${ ui.message("isanteplus.viralLoad.notDetected") } <% } %> <% } %></b>
    		<% if (lastViralLoadTest.valueCoded.conceptId == 1306) { %> ${ ui.message("isanteplus.viralLoad.notDetected") } <% } %> <% } %></b>,
    		${ ui.message("ActiveLists.date") } : <b>${lastViralLoadTest.obsDatetime}</b>
    	<% } %>
    </div>
</div>

