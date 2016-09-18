<%  if (patientHasAnActiveVisit) {  %>
	<div class="info-section">
	    <div class="info-header">
	        <h3>${ ui.message("isantepluspatientdashboard.iSantePlusForms") } </h3>
	    </div>
	    
	    <div class="info-body">
	    	<h4 id="togglePrimaryCareForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.primaryCare") }</h4>
	    	<ul id="primaryCareForms">
			    <% primaryCareForms.each { %>
			    	<li><a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a></li>
				<% } %>
			</ul>
		 	
		 	<h4 id="toggleLabForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.lab") }</h4>
	    	<ul id="labForms">
			    <% labForms.each { %>
			    	<li><a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a></li>
			 	<% } %>
			</ul>
		 	
		 	<%  if (showObygnForms) {  %>
			 	<h4 id="toggleObygnForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.obygn") }</h4>
		    	<ul id="obygnForms">
				    <% obygnForms.each { %>
				    	<li><a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a></li>
					 <% } %>
				</ul>
			<% } %>
		 	
		 	<h4 id="toggleHivCareForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.hivCare") }</h4>
	    	<ul id="hivCareForms">
			    <% hivCareForms.each { %>
			    	<li><a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a></li>
			 	<% } %>
			 </ul>
		 	
		 	<h4 id="togglePsychoSocialForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.psychoSocial") }</h4>
	    	<ul id="psychoSocialForms">
			    <% psychoSocialForms.each { %>
			    	<li><a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a></li>
			 	<% } %>
			</ul>
		 	
		 	<h4 id="toggleOtherForms" style="cursor:pointer;">${ ui.message("isantepluspatientdashboard.isanteForms.categories.other") }</h4>
	    	<ul id="otherForms">
			    <% otherForms.each { %>
			    	<li><a href="${ ui.format(it.url) }">${ ui.format(it.name) }</a></li>
				<% } %>
			</ul>
	    </div>
	</div>
<% } %>

<script type="text/javascript">
	jQuery("#psychoSocialForms, #labForms, #obygnForms, #hivCareForms, #otherForms").hide();
	
	jQuery(function() {
    	jQuery("#togglePrimaryCareForms").click(function(event) {
    		jQuery("#primaryCareForms").toggle();
    	});
    	
    	jQuery("#toggleLabForms").click(function(event) {
    		jQuery("#labForms").toggle();
    	});
    	
    	jQuery("#toggleObygnForms").click(function(event) {
    		jQuery("#obygnForms").toggle();
    	});
    	
    	jQuery("#toggleHivCareForms").click(function(event) {
    		jQuery("#hivCareForms").toggle();
    	});
    	
    	jQuery("#togglePsychoSocialForms").click(function(event) {
    		jQuery("#psychoSocialForms").toggle();
    	});
    	
    	jQuery("#toggleOtherForms").click(function(event) {
    		jQuery("#otherForms").toggle();
    	});
    });
</script>