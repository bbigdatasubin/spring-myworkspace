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
	@Scheduled(fixedRate = 1000 * 60 * 60 * 23) // �Ϸ縶�� �޾ƿ�
	public void requestWeatherData() throws IOException {
		System.out.println(new Date().toLocaleString() + "==== ���� ====");
		/* �ݺ��� ó�� */
		getWeather("1100000000"); // ����
		getWeather("2600000000"); // �λ�
		getWeather("2800000000"); // ��õ
		getWeather("4100000000"); // ���
		getWeather("4200000000"); // ����
		getWeather("4300000000"); // ���
		getWeather("4500000000"); // ����
		getWeather("4700000000"); // ���
		getWeather("5000000000"); // ����
	}

	private void getWeather(String areaNo) throws IOException {
		String serviceKey = "zxzusPAITqXFSRzyFPYRAG%2FXkzZS4gIvTP6uxs94OjIx2oj0IMJAuDeYKUuvwfbQgaIGbPvBPNnsTiwFaO6MJQ%3D%3D";

		StringBuilder builder = new StringBuilder();
		builder.append("http://apis.data.go.kr/1360000/LivingWthrIdxService01"); // �����ּ�
		builder.append("/getUVIdx"); // �󼼱���ּ�
		builder.append("?serviceKey=" + serviceKey); // ����Ű
		builder.append("&pageNo=1");
		builder.append("&dataType=JSON");
		builder.append("&areaNo=" + areaNo);
		builder.append("&time=2021062906");
		builder.append("&requestCode=A41");

		System.out.println(builder.toString());

		URL url = new URL(builder.toString()); // ���ڿ��κ��� URL ��ü ����
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL �ּҿ� ������ ��
//		connect.addRequestProperty("Accept", "*/*");
//		connect.addRequestProperty("Host", "<calculated when request is sent>");
//		connect.addRequestProperty("User-Agent", "PostmanRuntime/7.28.0");
//		connect.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
//		connect.addRequestProperty("Connection", "keep-alive");

		// 2. byte[] -> String(XML)���� ��ȯ
		byte[] result = connect.getInputStream().readAllBytes(); // ����(body)�����͸� ����Ʈ ������ �о����

		String data = new String(result, "utf-8");
		System.out.println(data);

//		// 3. String(XML) -> String(JSON)���� ��ȯ
//		JSONObject jObject = XML.toJSONObject(data);
//		System.out.println(jObject.toString());

		// 4. String(JSON) -> Object�� ��ȯ�� �ؾ���
		// ������ �ִ� ����(Class�� �� Object)���� ��ȯ�ؾ� ����� �� ����
		// fromJson(JSON���ڿ�, ��ȯ��Ÿ��)
		WeatherUVResponse response = new Gson().fromJson(data, WeatherUVResponse.class);
		System.out.println(response);
		// 5. ���䰴ü�� Entity ��ü�� ��ȯ�Ͽ� ����
		for (WeatherUVResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			repo.save(new WeatherUV(item));
		}
	}
}