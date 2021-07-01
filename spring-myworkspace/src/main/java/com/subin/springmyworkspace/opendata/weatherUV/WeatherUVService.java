package com.subin.springmyworkspace.opendata.weatherUV;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class WeatherUVService {
	WeatherUVRepository repo;

	@Autowired
	public WeatherUVService(WeatherUVRepository repo) {
		this.repo = repo;
	}

	@CacheEvict(cacheNames = "weatherUV", allEntries = true)
	@SuppressWarnings("deprecation")
	@Scheduled(fixedRate = 1000 * 60 * 60 * 23) // 하루마다 받아옴
	public void requestWeatherData() throws IOException {
		System.out.println(new Date().toLocaleString() + "==== 실행 ====");
		/* 반복문 처리 */
		getWeather("1100000000"); // 서울
		getWeather("2600000000"); // 부산
		getWeather("2800000000"); // 인천
		getWeather("4100000000"); // 경기
		getWeather("4200000000"); // 강원
		getWeather("4300000000"); // 충북
		getWeather("4500000000"); // 전북
		getWeather("4700000000"); // 경북
		getWeather("5000000000"); // 제주
	}

	private void getWeather(String areaNo) throws IOException {
		String serviceKey = "zxzusPAITqXFSRzyFPYRAG%2FXkzZS4gIvTP6uxs94OjIx2oj0IMJAuDeYKUuvwfbQgaIGbPvBPNnsTiwFaO6MJQ%3D%3D";

		StringBuilder builder = new StringBuilder();
		builder.append("http://apis.data.go.kr/1360000/LivingWthrIdxService01"); // 서비스주소
		builder.append("/getUVIdx"); // 상세기능주소
		builder.append("?serviceKey=" + serviceKey); // 서비스키
		builder.append("&pageNo=1");
		builder.append("&dataType=JSON");
		builder.append("&areaNo=" + areaNo);
		builder.append("&time=2021070106");
		builder.append("&requestCode=A41");

		System.out.println(builder.toString());

		URL url = new URL(builder.toString()); // 문자열로부터 URL 객체 생성
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL 주소에 접속을 함
//		connect.addRequestProperty("Accept", "*/*");
//		connect.addRequestProperty("Host", "<calculated when request is sent>");
//		connect.addRequestProperty("User-Agent", "PostmanRuntime/7.28.0");
//		connect.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
//		connect.addRequestProperty("Connection", "keep-alive");

		// 2. byte[] -> String(XML)으로 변환
		byte[] result = connect.getInputStream().readAllBytes(); // 본문(body)데이터를 바이트 단위로 읽어들임

		String data = new String(result, "utf-8");
		System.out.println(data);

//		// 3. String(XML) -> String(JSON)으로 변환
//		JSONObject jObject = XML.toJSONObject(data);
//		System.out.println(jObject.toString());

		// 4. String(JSON) -> Object로 변환을 해야함
		// 구조가 있는 형식(Class로 찍어낸 Object)으로 변환해야 사용할 수 있음
		// fromJson(JSON문자열, 변환할타입)
		WeatherUVResponse response = new Gson().fromJson(data, WeatherUVResponse.class);
		System.out.println(response);
		// 5. 응답객체를 Entity 객체로 변환하여 저장
		for (WeatherUVResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			repo.save(new WeatherUV(item));
		}
	}
}