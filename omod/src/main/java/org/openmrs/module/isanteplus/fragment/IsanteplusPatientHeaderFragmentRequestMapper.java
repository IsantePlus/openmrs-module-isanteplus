package org.openmrs.module.isanteplus.fragment;

import org.openmrs.ui.framework.fragment.FragmentRequest;
import org.openmrs.ui.framework.fragment.FragmentRequestMapper;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Component
public class IsanteplusPatientHeaderFragmentRequestMapper implements FragmentRequestMapper  {
	
	protected final Log log = LogFactory.getLog(getClass());
	 
	   /**
	    * Implementations should call {@link FragmentRequest#setProviderNameOverride(String)} and
	    * {@link FragmentRequest#setFragmentIdOverride(String)}, and return true if they want to remap a request,
	    * or return false if they didn't remap it.
	    *
	    * @param request may have its providerNameOverride and pageNameOverride set
	    * @return true if this page was mapped (by overriding the provider and/or page), false otherwise
	    */
	   public boolean mapRequest(FragmentRequest request) {
	      log.info(request.toString());
	      if (request.getProviderName().equals("coreapps")) {
	         if (request.getFragmentId().equals("patientHeader")) {
	            // change to the custom fragment provided by the module
	            request.setProviderNameOverride("isanteplus");
	            request.setFragmentIdOverride("isanteplusPatientHeader"); // no need to specify this if the name of the fragment is the same as the one being over
	            log.info(request.toString());
	                return true; 
	         }
	      }
	      // no override happened 
	      return false;
	   }


}
