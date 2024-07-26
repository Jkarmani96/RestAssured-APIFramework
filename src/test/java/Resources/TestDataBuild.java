package Resources;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class TestDataBuild {

	public AddPlace AddPlacePayload(String name, String address, String language) {
		AddPlace place = new AddPlace();
		place.setAccuracy(50);
		place.setName(name);
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress(address);
		place.setWebsite("http://google.com");
		place.setLanguage(language);

		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		place.setTypes(myList);

		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		place.setLocation(loc);

		return place;
	}

	public String deletePlace(String PlaceID) {
		return "{\r\n\"place_id\": \"" + PlaceID + "\"\r\n}";
	}

}
