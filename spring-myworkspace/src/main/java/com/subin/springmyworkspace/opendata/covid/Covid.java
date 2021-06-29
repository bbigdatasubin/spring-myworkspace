//package com.subin.springmyworkspace.opendata.covid;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.IdClass;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//@IdClass(CovidId.class)
//public class Covid {
//
//	// dataTime + itemCode �ΰ��� ��ġ�� ���ϰ� �� ��ǥ���� �� -> PK, Id(Identifier)
//	// DB -> �ռ�Ű(Composite Key): �������� �÷��� ���ļ� Primary Key�� �����
//	// JPA -> �ռ�Id(Composite Id): �������� �ʵ带 ���ļ� Id�� �����
//
//	// �������� �ߺ� ���� ���������� id�� ������
//
//	private String confCase; // Ȯ����
//	private String confCaseRate; // Ȯ����
//	@Id
//	private String createDt; // ����Ͻú���
//	private String criticalRate; // ġ���
//	private String death; // ���
//	private String deathRate; // �����
//	private String gubun; // ����
//	@Id
//	private String seq;
//
//	public Covid(CovidResponse.Item item) {
//		this.confCase = item.getConfCase();
//		this.confCaseRate = item.getConfCaseRate();
//		this.createDt = item.getCreateDt();
//		this.criticalRate = item.getCriticalRate();
//		this.death = item.getDeath();
//		this.deathRate = item.getDeathRate();
//		this.gubun = item.getGubun();
//		this.seq = item.getSeq();
//
//	}
//
//}