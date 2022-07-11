Test suite requires a configuration file src/test/java/com/harvester_automation/config/Application.properties
### First step to setup maven and run "mvn install" command in the root folder of the project
### This project framework consists of both UI and api part integrated together
### This Project uses the maven with cucumber, junit library
### To interact with API we are using REST ASSURED library
### Project is well modularized and contains page object, utils, helper class
### Test can be run from either running TestRunner.java or feature file directly
### feature files are available inside src/test/resources/features
### To run the specific test we need to specify tags = "" some tag name inside the TestRunner.java
### There are 2 tests inside UIFilterAndExtractTest.feature as of now.
	- Extract first 20 filtered data from table in the Homepage
	- Perform custom filter
### There is 1 Test in ApiConversionTest.feature which does
    -  Guatemalan Quetzal -> GBP -> Doge coin conversion
   i.e. 
       convert "10000000" Guatemalan Quetzal to British pounds
       convert the amount received in GBP to doge coin
     - The conversion output are printed on the console like below: 
       Guatemalan Quetzal ->GBP : value is 1082572.6
       GBP->Doge coin conversion : value is 20004987
### The extracted data from the UI are saved inside the excel.xlsx 
### The application url is specified in the src/test/java/Application.properties
### Also, we can run the test on multiple browser by passing the browser name as a value in method commonAPIHelper.initiateBrowser("chrome");



