<div class="info-section">
    <div class="info-header">
    	<i class="icon-vitals"></i>
        <h3>${ ui.message("coreapps.clinicianfacing.vitals") } </h3>
    </div>
    
    <div class="info-body">
    	<% vitals.each { %>
	    	<fieldset>
		        <h3>${ ui.format(it.label) }</h3>
		        <p class="left"><span class="obs-field"><span class=
		        "value">${ ui.format(it.value) }</span><span class="append-to-value"> ${ ui.format(it.unit) }</span></span></p>
		    </fieldset>
		 <% } %>   
    </div>
</div>
