package hooks;

import java.io.IOException;

import io.cucumber.java.Before;
import stepDefinitions.stepDefinition;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenerio() throws IOException {
		stepDefinition m = new stepDefinition();
		if (stepDefinition.getPlaceId == null) {
			m.add_place_payload_with("Test", "Test Address 12", "Test");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("Test", "GetPlaceAPI");
		}

	}
}
