Feature: Validating Place API's

Background: Generate the token 
      Given I have the endpoint as "MTU_AUTH_TOKEN"
      When I send the "Post" request with basic auth to "generate auth"
      | description | key          | value              |
      | username    | mtu_username |                    |
      | password    | mtu_password |                    |
      | formParam   | grant_type   | client_credentials |
      Then I should see the response status code as "200"
      And I should see the following information in response
      | Description | JsonPath        |
      | Token       | JSON_PATH_TOKEN |
      And I set the "Bearer Token"



@AddPlace
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "POST" https request
	Then the API call is success with status code is 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify that place_Id created maps to "<name>" using "GetPlaceAPI"
	
	
Examples: 
		|name     | language  | address   			 |
		|AAhouse  | English   |World Cross Center|
#		|BBhouse  |Germany    |World Trade Center   |

@DeletePlace
Scenario: Verify if Delete Place functionality is working
	Given Delete Place payload
	When user calls "DeletePlaceAPI" with "POST" https request
	Then the API call is success with status code is 200
	And "status" in response body is "OK"
		
		