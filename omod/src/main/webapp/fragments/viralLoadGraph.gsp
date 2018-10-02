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
			    viralLoadGraphContainer.addEventListener('mouseover', onMouseover);
		    });
		    
		    function onMouseover(event) {
			  	var properties = viralLoadGraph.getEventProperties(event);
			  	
				if(jQuery(event.target).is("rect")) {
			  		jQuery(event.target).attr("title", "Charge virale: " + properties.value.toString() + "   At: " + properties.time);
			  		
			  	}
			}
  		</script>
    </div>
</div>