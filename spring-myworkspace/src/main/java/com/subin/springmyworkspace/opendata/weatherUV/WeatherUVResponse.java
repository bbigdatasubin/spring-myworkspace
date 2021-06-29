package com.subin.springmyworkspace.opendata.weatherUV;

import java.util.List;

import lombok.Data;

@Data // getter/setter
public class WeatherUVResponse {
	private Response response;

	@Data
	public class Response {
		private Body body;
	}

	@Data
	public class Body {
		private Items items;
	}

	@Data
	public class Items {
		private List<Item> item;
	}

	@Data
	public class Item {
		private String areaNo;
		private String date;
		private String today;
		private String tomorrow;
		private String theDayAfterTomorrow;

	}
}