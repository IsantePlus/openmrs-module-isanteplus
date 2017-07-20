package org.openmrs.module.isanteplus.deploy.bundle;

import org.openmrs.customdatatype.datatype.FreeTextDatatype;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;


public class LocationAttributeTypes {
	
	public static LocationAttributeTypeDescriptor SITECODE = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "6242bf19-207e-4076-9d28-9290525b8ed9"; }
		public String name() { return "siteCode"; }
		public String description() { return "The site code of this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor CATEGORY = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "c539128d-252c-4b6a-9fa8-f706dfcad1d9"; }
		public String name() { return "category"; }
		public String description() { return "The category of this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};

	public static LocationAttributeTypeDescriptor TYPE = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "dc14c848-8e83-409a-b465-3f77f74e9385"; }
		public String name() { return "type"; }
		public String description() { return "The type of this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor NETWORK = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "3001b17e-a153-4efd-816c-e5a52f0555d3"; }
		public String name() { return "network"; }
		public String description() { return "The network where this location in"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor INCPHR = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "940f161c-26a7-4ed2-b5d7-62643265ee0b"; }
		public String name() { return "inCPHR"; }
		public String description() { return ""; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor DBSITE = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "f1fd9532-619d-4697-b196-f01d88348f7f"; }
		public String name() { return "dbSite"; }
		public String description() { return "The DB site for this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor IPADDRESS = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "992ca041-599a-4a00-b240-6df5dc0f79ec"; }
		public String name() { return "ipAddress"; }
		public String description() { return "The IP address for this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor DBVERSION = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "21a98921-4b6e-4b85-8271-686258c2c83c"; }
		public String name() { return "dbVersion"; }
		public String description() { return "The DB version for this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor OLDCLINICNAME = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "128e432a-8201-48d7-a1e7-76ecaecf98e1"; }
		public String name() { return "oldClinicName"; }
		public String description() { return "The old name of this location"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	public static LocationAttributeTypeDescriptor HOSTNAME = new LocationAttributeTypeDescriptor() {
		public String uuid() { return "a7ede00c-c1b1-4324-b84b-caa58fe9791c"; }
		public String name() { return "hostname"; }
		public String description() { return "The name of the server in the site"; }
		public Class<?> datatype() { return FreeTextDatatype.class; }
	};
	
	
}
