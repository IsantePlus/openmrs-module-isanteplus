<%
    ui.includeJavascript("registrationapp", "continuityOfCare.js")
%>

<script type="text/javascript">
    jq(document).ready(function() {
        if (!isCCDAvailable()) {
            jq('#ccd-section').hide();
        } else {
            jq('#ccd-date').text(getCCDDate());
        }
    });
</script>

<div id="ccd-section" class="info-section">
    <div class="info-header">
        <i class="icon-user"></i>
        <h3>${ ui.message("isanteplus.registration.patient.continuityOfCare.label") }</h3>
    </div>
    <div class="info-body summary-section">
        <div id="ccd-fragment">
            <div>
                <h3>${ ui.message("isanteplus.registration.patient.continuityOfCare.document.date") }</h3>
                <p id="ccd-date" class="left"></p>
            </div>
            <div>
                <h3></h3>
                <p class="left">${ ui.message("isanteplus.registration.patient.continuityOfCare.info") }</p>
            </div>
        </div>
        <div id="buttons">
            <button type="button" class="confirm" onclick="viewCCD()">
                ${ ui.message("isanteplus.registration.patient.continuityOfCare.document.view") }
            </button>
            <button class="submitButton right" onclick="importCCD()")>
                <i class="icon-download"></i>
                ${ ui.message("isanteplus.registration.patient.continuityOfCare.document.import") }
            </button>
        </div>
    </div>

    <div style="display:none" id="ccd-import-dialog" class="dialog">
        <div class="dialog-header">
            ${ui.message("isanteplus.registration.patient.continuityOfCare.document.import.dialog.label")}
        </div>
        <div class="dialog-content">
            <p>
                ${ui.message("isanteplus.registration.patient.continuityOfCare.document.import.dialog.message")}
            </p>
            <br/>
            <div class="buttons">
                <button class="confirm right">${ui.message("emr.yes")}</button>
                <button class="cancel">${ui.message("emr.no")}</button>
            </div>
        </div>
    </div>
</div>
