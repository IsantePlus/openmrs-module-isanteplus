<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.allergy") } </h3>
    </div>
     <div class="info-body">
    	<% if (allergy != null) { %>
    		<table>
    			<tr>
	    			<th>${ ui.message("isanteplus.allergy.name") }</th>
	    			<th>${ ui.message("isanteplus.allergy.date") }</th>
    			</tr>
	    		<% allergy.each { %>
		    		<tr>
			    		<td>${ui.format(it.valueText)}</td>
			    		<td>${ui.format(it.obsDatetime)}</td>
		    		</tr>
	    		<% } %>
    		</table>
    	<% } %>
    </div>
</div>