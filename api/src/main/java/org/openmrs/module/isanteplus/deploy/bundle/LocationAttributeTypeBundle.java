package org.openmrs.module.isanteplus.deploy.bundle;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

@Component
public class LocationAttributeTypeBundle extends AbstractMetadataBundle {

    @Override
    public void install() throws Exception {
    	install(LocationAttributeTypes.SITECODE);
        install(LocationAttributeTypes.CATEGORY);
        install(LocationAttributeTypes.TYPE);
        install(LocationAttributeTypes.NETWORK);
        install(LocationAttributeTypes.INCPHR);
        install(LocationAttributeTypes.DBSITE);
        install(LocationAttributeTypes.IPADDRESS);
        install(LocationAttributeTypes.DBVERSION);
        install(LocationAttributeTypes.OLDCLINICNAME);
        install(LocationAttributeTypes.HOSTNAME);
    }

}
