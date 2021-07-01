package com.subin.springmyworkspace.opendata.summer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

@Service
public class SummerService {
	SummerRepository repo;

	@Autowired
	public SummerService(SummerRepository repo) {
		this.repo = repo;
	}

	@CacheEvict(cacheNames = "summer", allEntries = true)
	@RequestMapping(value = "/opendata/summer/Summer", method = RequestMethod.GET)
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
		builder.append("/getSenTaIdx"); // �󼼱���ּ�
		builder.append("?serviceKey=" + serviceKey); // ����Ű
		builder.append("&dataType=JSON");
		builder.append("&areaNo=" + areaNo);
		builder.append("&time=2021070106");
		builder.append("&requestCode=A41");

		System.out.println(builder.toString());

		URL url = new URL(builder.toString()); // ���ڿ��κ��� URL ��ü ����
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL �ּҿ� ������ ��

		// 2. byte[] -> String(XML)���� ��ȯ
		byte[] result = connect.getInputStream().readAllBytes(); // ����(body)�����͸� ����Ʈ ������ �о����

		String data = new String(result, "utf-8");
		System.out.println(data);

		// 4. String(JSON) -> Object�� ��ȯ�� �ؾ���
		// ������ �ִ� ����(Class�� �� Object)���� ��ȯ�ؾ� ����� �� ����
		// fromJson(JSON���ڿ�, ��ȯ��Ÿ��)
		SummerResponse response = new Gson().fromJson(data, SummerResponse.class);
		System.out.println(response);
		// 5. ���䰴ü�� Entity ��ü�� ��ȯ�Ͽ� ����
		for (SummerResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			repo.save(new Summer(item));
		}
	}
}