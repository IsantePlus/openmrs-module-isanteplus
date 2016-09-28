<% ui.includeJavascript("isantepluspatientdashboard", "vis.min.js") %>
<% ui.includeCss("isantepluspatientdashboard", "vis.min.css") %>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.bmiGraph") }</h3>
    </div>
    
    <div class="info-body">
    	<div id="bmiGraph"></div>
    	<script type="text/javascript">
    		var items = ${items};
    		var options = ${options};
  			var bmiGraphContainer = document.getElementById('bmiGraph');
  			var bmiGraph = new vis.Graph2d(bmiGraphContainer, new vis.DataSet(items), options);
  		</script>
    </div>
</div>