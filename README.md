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
- Download OpenMRS Reference Application 2.3.1 modules from: https://sourceforge.net/projects/openmrs/files/releases/OpenMRS_2.3.1/openmrs-2.3.1-modules.zip/download
- Download OpenMRS Platform 1.11.5 or 1.11.6
- Replace some Extracted downloaded 2.3.1 modules with uiframework-3.6 and appui-1.4 and leave the rest of the modules
- Download and install this module to intergrate with the above mentioned packages
- Run the integrated package as a normal OpenMRS reference Application using https://wiki.openmrs.org/x/BANGBQ as a sample guide


Configurable Global Properties for non IsantePlus Implementations
-------------------------------------
| Name  | Default Value | Description |
| ------------- | ------------- | ------------- |
| isanteplus.adultStartingAge | 14 | Adult starting age in years, OpenMRS uses 13, Haiti uses 14 |
| isanteplus.enabledIsantePlusUI | true | Set to 'false' to disable iSantePlus custom user interface |
| isanteplus.formIdsToExcludeFromHistory | 1,2,3,4,5 | Forms IDs to exclude from form History |
