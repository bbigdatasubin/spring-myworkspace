package com.subin.springmyworkspace.opendata.fitness;

import java.util.List;

import lombok.Data;

@Data
public class FitnessResponse {
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
		private String ageClass; // 연령대
		private String ageDegree; // 나이

		private String certGbn; // 상장구분
		private String itemF001; // 신장
		private String itemF002; // 체중
		private String itemF003; // 체지방율
		private String itemF004; // 허리둘레
		private String itemF005; // 최저혈압
		private String itemF006; // 최고혈압
		private String itemF007; // 악력

		private String itemF012; // 앉아윗몸앞으로굽히기

		private String itemF018; // BMI
		private String itemF019; // 교차윗몸일으키기
		private String itemF020; // 왕복오래달리기
		private String itemF021; // 10M 4회 왕복달리기
		private String itemF022; // 제자리 멀리뛰기

		private String testYm; // 측정년월
	}

}
