<ul class="navList">
	<li>
		<a id="WTAGEINF" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.WTAGEINF") }</a>
	</li>
	<li>
		<a id="LENAGEINF" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.LENAGEINF") }</a>
	</li>
	<li>
		<a id="WTLENINF" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.WTLENINF") }</a>
	</li>
	<li>
		<a id="HCAGEINF" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.HCAGEINF") }</a>
	</li>
	<%if (patientPropts.age.years >= 2) {%>
		<li>
			<a id="WTSTAT" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.WTSTAT") }</a>
		</li>
		<li>
			<a id="WTAGE" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.WTAGE") }</a>
		</li>
		<li>
			<a id="STATAGE" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.STATAGE") }</a>
		</li>
		<li>
			<a id="BMIAGE" class="growthChartCDCLink" href="#">${ ui.message("isantepluspatientdashboard.BMIAGE") }</a>
		</li>
	<%}%>
</ul>
<br />

