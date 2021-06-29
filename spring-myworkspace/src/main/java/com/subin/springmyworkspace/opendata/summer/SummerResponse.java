package com.subin.springmyworkspace.opendata.summer;

import java.util.List;

import lombok.Data;

@Data // getter/setter
public class SummerResponse {
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

		private String date; // 발표시간
		private String areaNo; // 지역번호
		private String h3;
		private String h6;
		private String h9;
		private String h12;
		private String h15;
		private String h18;
		private String h21;
		private String h24;
		private String h27;
		private String h30;
		private String h33;
		private String h36;
		private String h39;
		private String h42;
		private String h45;
		private String h48;
	}
}