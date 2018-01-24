<% ui.includeJavascript("isanteplus", "vis.min.js") %>
<% ui.includeCss("isanteplus", "vis.min.css") %>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.weightGraph") }</h3>
    </div>
    
    <div class="info-body">
    	<div id="weightGraph"></div>
    	<script type="text/javascript">
    		var items = ${items};
    		var options = ${options};
  			var weightGraph = new vis.Graph2d(weightGraphContainer, new vis.DataSet(items), options);
  			jQuery(function() {
  				if(items == undefined || items.length <= 0) {
  					jQuery("#weightGraph").hide();
  					alert('Non trouve');
  				} else {
  					document.getElementById('weightGraph').style.display = "block";
  					jQuery("#weightGraph").show();
  					alert(items);
  					
  				}
  				jQuery(".vis-point").tooltip();
			    weightGraphContainer.addEventListener('mouseover', onMouseover);
		    });
		    
		    function onMouseover(event) {
			  	var properties = weightGraph.getEventProperties(event);
			  	
				if(jQuery(event.target).is("rect")) {
			  		jQuery(event.target).attr("title", "Weight: " + properties.value.toString() + "kg At: " + properties.time);
			  	}
			}
			
  		</script>
    </div>
</div>