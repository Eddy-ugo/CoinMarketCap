@CoinMarketCap
Feature: Conversion Test

  @ConversionTest
  Scenario: Guatemalan Quetzal ->GBP - GBP->Doge coin conversion
    Given I initialize request using "apiURL" base url
    And I add following headers to the request
      | X-CMC_PRO_API_KEY | d57fb08e-e548-4ac9-8dde-fd99b4e938f4 |
      | Accept            | application/json                     |
    And I add following parameters the the request
      | ParameterName | ParameterValue |
      | convert       | GBP            |
      | amount        |       10000000 |
      | symbol        | GTQ            |
    And I submit the "GET" request with "" end url
    Then I verify status code of the response is 200
    Then I get value for "data.quote.GBP.price" attribute and save it to runtime
    Given I initialize request using "apiURL" base url
    And I add following headers to the request
      | X-CMC_PRO_API_KEY | d57fb08e-e548-4ac9-8dde-fd99b4e938f4 |
      | Accept            | application/json                     |
    And I add following parameters the the request
      | ParameterName | ParameterValue       |
      | convert       | DOGE                 |
      | amount        | data.quote.GBP.price |
      | symbol        | GBP                  |
    And I submit the "GET" request with "" end url
    Then I verify status code of the response is 200
    Then I get value for "data.quote.DOGE.price[0]" attribute print in the console
