<% ui.includeJavascript("isanteplus", "vis.min.js") %>
<% ui.includeCss("isanteplus", "vis.min.css") %>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.bmiGraph") }</h3>
    </div>
    
    <div class="info-body">
    	<div id="bmiGraph"></div>
    	<script type="text/javascript">
    		var result = ${items};
    		var dat = ${options};
  			var bmiGraphContainer = document.getElementById('bmiGraph');
  			var bmiGraph = new vis.Graph2d(bmiGraphContainer, new vis.DataSet(result), dat);
  			
  			jQuery(function() {
  				if(result == undefined || result.length <= 0) {
  					jQuery("#bmiGraph").hide();
  				} else {
  					jQuery("#bmiGraph").show();
  				}
  				jQuery(".vis-point").tooltip();
			    bmiGraphContainer.addEventListener('mouseover', onMouseover2);
  				
		    });
		    
		    function onMouseover2(event2) {
			  	var properties2 = bmiGraph.getEventProperties(event2);
			  	
				if(jQuery(event2.target).is("rect")) {
			  		jQuery(event2.target).attr("title", "BMI: " + properties2.value.toString() + "   At: " + properties2.time);
			  		
			  	}
			} 
  		</script>
    </div>
</div>