<% ui.includeJavascript("isanteplus", "vis.min.js") %>
<% ui.includeCss("isanteplus", "vis.min.css") %>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.viralLoadGraph") }</h3>
    </div>
    
    <div class="info-body">
    	<div id="viralLoadGraph"></div>
    	<script type="text/javascript">
    		var itemsViralLoad = ${itemsViralLoad};
    		var optionsViralLoad = ${optionsViralLoad};
  			var viralLoadGraphContainer = document.getElementById('viralLoadGraph');
  			var viralLoadGraph = new vis.Graph2d(viralLoadGraphContainer, new vis.DataSet(itemsViralLoad), optionsViralLoad);
  		
  			jQuery(function() {
  				if(items == undefined || items.length <= 0) {
  					jQuery("#viralLoadGraph").hide();
  				} else {
  					jQuery("#viralLoadGraph").show();
  				}
  				jQuery(".vis-point").tooltip();
			    viralLoadGraphContainer.addEventListener('mouseover', onMouseover1);
		    });
		    
		   function onMouseover1(event1) {
			  	var properties1 = viralLoadGraph.getEventProperties(event1);
			  	
				if(jQuery(event1.target).is("rect")) {
			  		jQuery(event1.target).attr("title", "Charge virale: " + properties1.value.toString() + "   At: " + properties1.time);
			  		
			  	}
			} 
  		</script>
    </div>
</div>