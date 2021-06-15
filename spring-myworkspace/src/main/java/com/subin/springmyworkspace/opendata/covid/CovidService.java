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
//	// ���� ����, ms(milli second ����), 1000 == 1��
	@Scheduled(fixedRate = 1000 * 60 * 60 * 23) // 23�ð�����, �׽�Ʈ�� ������, ���α׷��� ���۵� �� �ѹ��� �ٷ� �����
	public void requesCovidData() throws IOException {
		System.out.println(new Date().toLocaleString() + "== ���� ==");
		getCovid("20210610");
	}

	private void getCovid(String startCreateDt) throws IOException {
		String serviceKey = "zxzusPAITqXFSRzyFPYRAG%2FXkzZS4gIvTP6uxs94OjIx2oj0IMJAuDeYKUuvwfbQgaIGbPvBPNnsTiwFaO6MJQ%3D%3D";

		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi/service/rest/Covid19"); // �����ּ�
		builder.append("/getCovid19GenAgeCaseInfJson"); // �󼼱���ּ�
		builder.append("?serviceKey=" + serviceKey); // ����Ű
		builder.append("&numOfRows=24"); // ������� 24�ð��� ������ ��ȸ
		builder.append("&pageNo=1"); // 1������
		builder.append("&startCreateDt=" + startCreateDt);

		System.out.println(builder.toString());

		URL url = new URL(builder.toString());
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL �ּҿ� ����
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
