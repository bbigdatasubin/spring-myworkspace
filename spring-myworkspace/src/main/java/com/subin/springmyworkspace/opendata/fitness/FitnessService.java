package com.subin.springmyworkspace.opendata.fitness;

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
public class FitnessService {
	FitnessRepository repo;

	@Autowired
	public FitnessService(FitnessRepository repo) {
		this.repo = repo;
	}

	@SuppressWarnings("deprecation")
	@Scheduled(fixedRate = 1000 * 60 * 60 * 23) // �Ϸ縶�� �޾ƿ�
	public void requestFitnessData() throws IOException {
		System.out.println(new Date().toLocaleString() + "==== ���� ====");
		/* �ݺ��� ó�� */
		getFitness("201506");

	}

	private void getFitness(String testYm) throws IOException {
		String serviceKey = "zxzusPAITqXFSRzyFPYRAG/XkzZS4gIvTP6uxs94OjIx2oj0IMJAuDeYKUuvwfbQgaIGbPvBPNnsTiwFaO6MJQ==";

		StringBuilder builder = new StringBuilder();
		builder.append("http://www.kspo.or.kr/openapi/service/nfaTestInfoService"); // �����ּ�
		builder.append("/getNfaTestRsltList"); // �󼼱���ּ�
		builder.append("?serviceKey=" + serviceKey); // ����Ű
		builder.append("&testYm=" + testYm); // ���

		System.out.println(builder.toString());

		URL url = new URL(builder.toString());
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL �ּҿ� ����
		byte[] result = connect.getInputStream().readAllBytes();

		String data = new String(result, "utf-8");
		System.out.println("dfdfdf" + data);

		JSONObject jObject = XML.toJSONObject(data);
		System.out.println(jObject.toString());

		FitnessResponse response = new Gson().fromJson(jObject.toString(), FitnessResponse.class);
		System.out.println(response);

		for (FitnessResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			repo.save(new Fitness(item));
		}
	}
}