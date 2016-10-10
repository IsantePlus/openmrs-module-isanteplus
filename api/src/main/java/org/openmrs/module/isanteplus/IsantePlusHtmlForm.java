package org.openmrs.module.isanteplus;

import java.io.IOException;

import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.FormService;
import org.openmrs.module.htmlformentry.HtmlForm;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.htmlformentryui.HtmlFormUtil;
import org.openmrs.ui.framework.WebConstants;
import org.openmrs.ui.framework.resource.ResourceFactory;

public class IsantePlusHtmlForm {
	private HtmlForm htmlForm;

	private String name;

	private String url;

	public IsantePlusHtmlForm(String definitionUiResource, ResourceFactory resourceFactory, FormService formService,
			HtmlFormEntryService htmlFormEntryService, Patient patient, Visit visit) {
		try {
			setHtmlForm(HtmlFormUtil.getHtmlFormFromUiResource(resourceFactory, formService, htmlFormEntryService,
					"isanteplus:htmlforms/" + definitionUiResource));
			if (getHtmlForm() != null)
				setName(getHtmlForm().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (patient != null && visit != null) {
			setUrl("/" + WebConstants.WEBAPP_NAME
					+ "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?patientId=" + patient.getUuid()
					+ "&visitId=" + visit.getVisitId() + "&definitionUiResource=isanteplus:htmlforms/"
					+ definitionUiResource);
		}
	}

	public HtmlForm getHtmlForm() {
		return htmlForm;
	}

	public void setHtmlForm(HtmlForm htmlForm) {
		this.htmlForm = htmlForm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
