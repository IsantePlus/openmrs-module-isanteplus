iSantePlus OpenMRS Module
=====================================

Overview
-------------------------------------
IsantePlus is a Migration from the Haitian isante EMR to OpenMRS, this is an OpenMRS instance that is customised to meet Haiti national EMR needs


Included Configurable Features
-------------------------------------
- [x] Configurable iSantePlus UI customisations
- [x] IsantePlus Forms
- [x] Visit's Form History
- [x] Patient's Form History
- [x] Growth Charts
- [x] Last Viral Load Test
- [x] Weight's Graph
- [x] Lab History
- [x] IsantePlus Most Recent Vitals
- [x] Drugs History
- [x] BMI Graph
- [x] Among others


Installation Requirements & Guide
-------------------------------------
- Download OpenMRS Reference Application 2.5 modules from: 
https://sourceforge.net/projects/openmrs/files/releases/OpenMRS_2.5/openmrs-2.5-modules.zip/download
- Unpack this zip file and copy all modules into the {OPENMRS_HOME}/modules folder
- Download the OpenMRS Platform 2.0.5 from [source](https://sourceforge.net/projects/openmrs/files/releases/OpenMRS_Platform_2.0.5/openmrs.war/download)
- Download the following module and upgrade the existing module:
    - [htmlformentry-3.3.2](https://modules.openmrs.org/modulus/api/releases/1558/download/htmlformentry-3.3.2.omod)
- Download, build and install this module to integrate with the above mentioned packages
- Run the integrated package as a normal OpenMRS reference Application using https://wiki.openmrs.org/x/BANGBQ as a sample guide


Configurable Global Properties for non IsantePlus Implementations
-------------------------------------
| Name  | Default Value | Description |
| ------------- | ------------- | ------------- |
| isanteplus.adultStartingAge | 14 | Adult starting age in years, OpenMRS uses 13, Haiti uses 14 |
| isanteplus.enabledIsantePlusUI | true | Set to 'false' to disable iSantePlus custom user interface |
| isanteplus.formIdsToExcludeFromHistory | 1,2,3,4,5 | Forms IDs to exclude from form History |
