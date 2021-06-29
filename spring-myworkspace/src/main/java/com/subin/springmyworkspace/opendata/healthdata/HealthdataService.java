package com.subin.springmyworkspace.opendata.healthdata;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class HealthdataService {
	HealthdataRepository repo;

	@Autowired
	public HealthdataService(HealthdataRepository repo) {
		this.repo = repo;
	}

	@CacheEvict(cacheNames = "healthdata", allEntries = true)
	@SuppressWarnings("deprecation")
	@Scheduled(fixedRate = 1000 * 60 * 60 * 23) // 하루마다 받아옴
	public void requestFitnessData() throws IOException {
		System.out.println(new Date().toLocaleString() + "==== 실행 ====");
		/* 반복문 처리 */
//		getFitness("201801");
//		getFitness("201802");
//		getFitness("201803");
//		getFitness("201804");
//		getFitness("201805");
//		getFitness("201806");
//		getFitness("201807");
//		getFitness("201808");
//		getFitness("201809");
//		getFitness("201810");
//		getFitness("201811");
//		getFitness("201812");

//		getFitness("201901");
//		getFitness("201902");
//		getFitness("201903");
//		getFitness("201904");
//		getFitness("201905");
//		getFitness("201906");
//		getFitness("201907");
//		getFitness("201908");
//		getFitness("201909");
//		getFitness("201910");
//		getFitness("201911");
//		getFitness("201912");

//		getFitness("202001");
//		getFitness("202002");
//		getFitness("202003");
//		getFitness("202004");
//		getFitness("202005");
//		getFitness("202006");
//		getFitness("202007");
//		getFitness("202008");
//		getFitness("202009");
//		getFitness("202010");
//		getFitness("202011");
//		getFitness("202012");
	}

	private void getFitness(String testYm) throws IOException {
		String serviceKey = "zxzusPAITqXFSRzyFPYRAG%2FXkzZS4gIvTP6uxs94OjIx2oj0IMJAuDeYKUuvwfbQgaIGbPvBPNnsTiwFaO6MJQ%3D%3D";

		StringBuilder builder = new StringBuilder();
		builder.append("http://www.kspo.or.kr/openapi/service/nfaTestInfoService"); // 서비스주소
		builder.append("/getNfaTestRsltList"); // 상세기능주소
		builder.append("?serviceKey=" + serviceKey); // 서비스키
		builder.append("&testYm=" + testYm); // 년월

		// System.out.println(builder.toString());

		URL url = new URL(builder.toString());
		HttpURLConnection connect = (HttpURLConnection) url.openConnection(); // URL 주소에 접속
		connect.addRequestProperty("Accept", "*/*");
		connect.addRequestProperty("Host", "www.kspo.or.kr");
		connect.addRequestProperty("User-Agent", "PostmanRuntime/7.28.0");
		connect.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
		connect.addRequestProperty("Connection", "keep-alive");
		byte[] result = connect.getInputStream().readAllBytes();

		String data = new String(result, "utf-8");
		// System.out.println(data);

		JSONObject jObject = XML.toJSONObject(data);
		// System.out.println(jObject.toString());

		HealthdataResponse response = new Gson().fromJson(jObject.toString(), HealthdataResponse.class);
		// System.out.println(response);

		for (HealthdataResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			repo.save(new Healthdata(item));
		}
	}
}