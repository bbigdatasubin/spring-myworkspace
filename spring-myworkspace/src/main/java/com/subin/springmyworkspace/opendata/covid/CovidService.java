package com.subin.springmyworkspace.opendata.covid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CovidService {
	CovidRepository repo;

	@Autowired
	public CovidService(CovidRepository repo) {
		this.repo = repo;
	}

	@SuppressWarnings("deprecation")
//	// 고정 비율, ms(milli second 단위), 1000 == 1초
	@Scheduled(fixedRate = 1000 * 60 * 60 * 23) // 23시간마다, 테스트용 스케줄, 프로그램이 시작될 때 한번은 바로 실행됨
	public void requesCovidData() throws IOException {
		System.out.println(new Date().toLocaleString() + "== 실행 ==");
		getCovid("20210610");
	}

	private void getCovid(String startCreateDt) throws IOException {
		String serviceKey = "zxzusPAITqXFSRzyFPYRAG%2FXkzZS4gIvTP6uxs94OjIx2oj0IMJAuDeYKUuvwfbQgaIGbPvBPNnsTiwFaO6MJQ%3D%3D";

		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi/service/rest/Covid19"); // 서비스주소
		builder.append("/getCovid19GenAgeCaseInfJson"); // 상세기능주소
		builder.append("?serviceKey=" + serviceKey); // 서비스키
		builder.append("&numOfRows=24"); // 현재부터 24시간의 데이터 조회
		builder.append("&pageNo=1"); // 1페이지
		builder.append("&startCreateDt=" + startCreateDt);

		System.out.println(builder.toString());

		URL url = new URL(builder.toString());
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL 주소에 접속
		byte[] result = connect.getInputStream().readAllBytes();

		String data = new String(result, "utf-8");
		System.out.println(data);

		JSONObject jObject = XML.toJSONObject(data);
		System.out.println(jObject.toString());

		CovidResponse response = new Gson().fromJson(jObject.toString(), CovidResponse.class);
		System.out.println(response);

		for (CovidResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			repo.save(new Covid(item));
		}
	}

}
