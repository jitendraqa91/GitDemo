package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	
	
	//Run before @DeletePlace tag
	//Run when place id is null
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		StepDefination st = new StepDefination();
		if(StepDefination.place_id==null)
		{
		st.add_place_payload_with("jitendra", "hindi", "Greater Noida");
		st.user_calls_with_https_request("AddPlaceAPI", "POST");
		st.verify_that_place_id_created_maps_to_using("jitendra", "GetPlaceAPI");
		}
		
	}

}
