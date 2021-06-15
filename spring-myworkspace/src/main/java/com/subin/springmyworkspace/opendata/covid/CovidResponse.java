package com.subin.springmyworkspace.opendata.covid;

import java.util.List;

import lombok.Data;

@Data
public class CovidResponse {
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
		private String confCase;
		private String confCaseRate;
		private String createDt;
		private String criticalRate;
		private String death;
		private String deathRate;
		private String gubun;
		private String seq;

	}

}
