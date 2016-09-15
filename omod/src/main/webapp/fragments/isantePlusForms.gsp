<%  if (patientHasAnActiveVisit) {  %>
	<div class="info-section">
	    <div class="info-header">
	        <h3>${ ui.message("isantepluspatientdashboard.iSantePlusForms") } </h3>
	    </div>
	    
	    <div class="info-body">
	    	<h4 id="togglePrimaryCareForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.primaryCare") }</h4>
	    	<p id="primaryCareForms">
		    	<% primaryCare.each { %>
		    		<a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a> <br />
			 	<% } %>
		 	</p>
		 	<h4 id="toggleLabForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.lab") }</h4>
	    	<p id="labForms">
		    	<% labForms.each { %>
		    		<a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a><br />
			 	<% } %>
		 	</p>
		 	<h4 id="togglePsychoSocialForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.psychoSocial") }</h4>
	    	<p id="psychoSocialForms">
		    	<% psychoSocialForms.each { %>
		    		<a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a><br />
			 	<% } %>
		 	</p>
	    </div>
	</div>
<% } %>

<script type="text/javascript">
	jQuery("#psychoSocialForms, #labForms").hide();
	
	jQuery(function() {
    	jQuery("#togglePrimaryCareForms").click(function(event) {
    		jQuery("#primaryCareForms").toggle();
    	});
    	
    	jQuery("#toggleLabForms").click(function(event) {
    		jQuery("#labForms").toggle();
    	});
    	
    	jQuery("#togglePsychoSocialForms").click(function(event) {
    		jQuery("#psychoSocialForms").toggle();
    	});
    });
</script>