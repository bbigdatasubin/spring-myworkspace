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
		private String ageClass; // ���ɴ�
		private String ageDegree; // ����

		private String certGbn; // ���屸��
		private String itemF001; // ����
		private String itemF002; // ü��
		private String itemF003; // ü������
		private String itemF004; // �㸮�ѷ�
		private String itemF005; // ��������
		private String itemF006; // �ְ�����
		private String itemF007; // �Ƿ�

		private String itemF012; // �ɾ����������α�����

		private String itemF018; // BMI
		private String itemF019; // ������������Ű��
		private String itemF020; // �պ������޸���
		private String itemF021; // 10M 4ȸ �պ��޸���
		private String itemF022; // ���ڸ� �ָ��ٱ�

		private String testYm; // �������
	}

}
