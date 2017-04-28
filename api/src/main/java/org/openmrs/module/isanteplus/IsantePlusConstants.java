package org.openmrs.module.isanteplus;

/**
 * This file must always be updated every time apps or/and extensions are added
 * or edited to /src/main/webapp/isantepluspatientdashboard_app.json and
 * /src/main/webapp/isantepluspatientdashboard_extension.json
 * 
 * @author k-joseph
 * 
 */
public class IsantePlusConstants {

	public static String VISITFORMHISTORY_EXTENSIONPOINT_ID = "isanteplus.formHistory";

	public static String LABHISTORY_EXTENSIONPOINT_ID = "isanteplus.labHistory";

	public static String FORMHISTORY_EXTENSIONPOINT_ID = "isanteplus.formsHistory";

	public static String GROWTHCHARTS_EXTENSIONPOINT_ID = "isanteplus.growthCharts";

	public static String WEIGHTSGRAPH_EXTENSIONPOINT_ID = "isanteplus.patientDashboard.secondColumnFragments.weightGraph";

	public static String LASTVIRALLOADTEST_EXTENSIONPOINT_ID = "isanteplus.patientDashboard.secondColumnFragments.lastViralLoadTest";

	public static String ISANTEFORMS_EXTENSIONPOINT_ID = "isanteplus.patientDashboard.firstColumnFragments.isantePlusForms";

	public static String DEFAULT_MOSTRECENTVITALS_APP_EXTENSIONPOINT_ID = "coreapps.mostRecentVitals";

	public static String MOSTRECENTVITALS_EXTENSIONPOINT_ID = "isanteplus.patientDashboard.firstColumnFragments.mostRecentVitals";

	/**
	 * Add a name whenever a new iSante form file is added or one existing is
	 * renamed
	 */
	private static String ISANTEPLUS_FORMFILE_NAMES_SEPARATEDBYCOMMAS = "Adh.xml,Lab.xml,OBGYN.xml,FOBGYN.xml,Tacc.xml,PsyA.xml,PsyP.xml,OrdM.xml,Ordpd.xml,ArretVIH.xml,PVisitAdult.xml,PVisitPed.xml,ConsPed.xml,PCons.xml,PConsPed.xml,Cons.xml,Vacc.xml,VisitSuivi.xml,VisitSuiviPed.xml,Imagerie.xml,patientRegistration.xml";

	public static String[] ISANTEPLUS_FORMFILE_NAMES = ISANTEPLUS_FORMFILE_NAMES_SEPARATEDBYCOMMAS.split(",");

	public static final String DRUGSHISTORY_EXTENSIONPOINT_ID = "isanteplus.drugsHistory";

	public static String BMIGRAPH_EXTENSIONPOINT_ID = "isanteplus.patientDashboard.secondColumnFragments.bmiGraph";
	
	public static final String PIH_REGISTRATION_CONCEPTS_METADATA_PACKAGE_UUID = "2ffa55cc-4dcd-49fc-ac4f-e3406cfce788";

}
