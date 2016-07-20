<% ui.includeJavascript("isantepluspatientdashboard", "Chart.min.js") %>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isantepluspatientdashboard.weightGraph") }</h3>
    </div>
    
    <div class="info-body">
    	<canvas id="weightsChart" width="50" height="50"></canvas>
		<script type="text/javascript">
			var weightsValues = ${weightsValues}
			var weightsLabels = ${weightsLabels}
			var chartDataSchema = {
        		labels: weightsLabels,
        		datasets: [{
            		label: '${ ui.message("isantepluspatientdashboard.weightGraph.toggleWeights") }',
            		data: weightsValues,
            		backgroundColor: '#51A351',
            		borderColor: '#363463',
            		borderWidth: 1
        		}]
    		}
		
			var weightsChart = new Chart(document.getElementById("weightsChart"), {
    			type: 'line',
    			data: chartDataSchema,
    			options: {
        			scales: {
            			yAxes: [{
                			ticks: {
                    			beginAtZero:true
                			}
            			}]
        			}
    			}
			});
		</script>

    </div>
</div>