package org.openmrs.module.isanteplus.deploy.bundle;

import org.openmrs.Location;
import org.openmrs.LocationAttributeType;
import org.openmrs.customdatatype.datatype.FreeTextDatatype;
import org.openmrs.customdatatype.datatype.RegexValidatedTextDatatype;
import org.openmrs.module.isanteplus.deploy.bundle.LocationMflCsvSource;
import org.openmrs.module.isanteplus.deploy.bundle.LocationMflSynchronization;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.metadatadeploy.source.ObjectSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.locationAttributeType;

@Component
//@Requires({ CommonMetadata.class })
public class LocationBundle extends AbstractMetadataBundle {
	
	@Autowired
	private LocationMflSynchronization mflSynchronization;
	
	public static final class _LocationAttributeType {
		public static final String CATEGORY = "c539128d-252c-4b6a-9fa8-f706dfcad1d9";
		public static final String TYPE = "dc14c848-8e83-409a-b465-3f77f74e9385";
		public static final String ARRONDISSEMENT = "aba4699b-0dec-4194-9a11-31060b231737";
		public static final String ISANTESITECODE =  "0e52924e-4ebb-40ba-9b83-b198b532653b"; 
		public static final String SITECODE = "6242bf19-207e-4076-9d28-9290525b8ed9";
		public static final String NETWORK = "3001b17e-a153-4efd-816c-e5a52f0555d3";
		public static final String INCPHR = "940f161c-26a7-4ed2-b5d7-62643265ee0b";
		public static final String DBSITE = "f1fd9532-619d-4697-b196-f01d88348f7f";
		public static final String IPADDRESS = "992ca041-599a-4a00-b240-6df5dc0f79ec";
		public static final String DBVERSION = "21a98921-4b6e-4b85-8271-686258c2c83c";
		public static final String OLDCLINICNAME = "128e432a-8201-48d7-a1e7-76ecaecf98e1";
		public static final String HOSTNAME = "a7ede00c-c1b1-4324-b84b-caa58fe9791c";
		
}
	
	@Override
	public void install() throws Exception {
		install(true);
	}
	
	public void install(boolean full) throws Exception {
		if (full) {
			ObjectSource<Location> source = new LocationMflCsvSource("isanteplus_site_list_20170911.csv");
			sync(source, mflSynchronization);
        }
	}

}
