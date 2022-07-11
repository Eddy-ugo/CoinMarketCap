@FilterDataTest
Feature: Filter Test with Data Exraction

  Background: 
    Given I open the url

  @ExtractDataToExcel
  Scenario: Extract data to excel
    Then Select Show rows dropdown value to "20"
    Then Capture the page content to the excel file "excel.xlsx" and sheet "coinmarketData"
    
    
  @PerformFilter
   Scenario: Perform custom filter
   And I click on filters
   And Select "PoW" from algorithm
   And Click 1 more Filter button
   And I select Mineable
   And I select All cryptocurrencies
   And I select coins
   And I click Price
   And I set min and max value to "100" and "10000"
   And I click Apply Filter
   And I click Show results