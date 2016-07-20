<% ui.includeJavascript("isantepluspatientdashboard", "vis.min.js") %>
<% ui.includeCss("isantepluspatientdashboard", "vis.min.css") %>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.weightGraph") }</h3>
    </div>
    
    <div class="info-body">
    	<div id="weightGraph"></div>
    	<script type="text/javascript">
    		var items = ${items};
    		var options = ${options};
  			var weightGraphContainer = document.getElementById('weightGraph');
  			var weightGraph = new vis.Graph2d(weightGraphContainer, new vis.DataSet(items), options);
  		</script>
    </div>
</div>