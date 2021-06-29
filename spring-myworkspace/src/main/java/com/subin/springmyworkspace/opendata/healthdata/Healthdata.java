package com.subin.springmyworkspace.opendata.healthdata;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(HealthdataId.class)
public class Healthdata {

	// dataTime + itemCode �ΰ��� ��ġ�� ���ϰ� �� ��ǥ���� �� -> PK, Id(Identifier)
	// DB -> �ռ�Ű(Composite Key): �������� �÷��� ���ļ� Primary Key�� �����
	// JPA -> �ռ�Id(Composite Id): �������� �ʵ带 ���ļ� Id�� �����

	// �������� �ߺ� ���� ���������� id�� ������

	private String ageClass; // ���ɴ�
	private String ageDegree; // ����
	@Id
	private String testYm; // �������
	@Id
	private String itemF001; // ����
	@Id
	private String itemF002; // ü��
	private String certGbn; // ���屸��
	private String itemF003; // ü������
	private String itemF005; // ��������
	private String itemF006; // �ְ�����
	private String itemF007; // �Ƿ�
	private String itemF012; // �ɾ����������α�����
	private String itemF018; // BMI
	private String itemF019; // ������������Ű��
	private String itemF020; // �պ������޸���
	private String itemF021; // 10M 4ȸ �պ��޸���
	private String itemF022; // ���ڸ� �ָ��ٱ�

	public Healthdata(HealthdataResponse.Item item) {
		this.ageClass = item.getAgeClass();
		this.ageDegree = item.getAgeDegree();
		this.certGbn = item.getCertGbn();
		this.itemF001 = item.getItemF001();
		this.itemF002 = item.getItemF002();
		this.itemF003 = item.getItemF003();
		this.itemF005 = item.getItemF005();
		this.itemF006 = item.getItemF006();
		this.itemF007 = item.getItemF007();
		this.itemF012 = item.getItemF012();
		this.itemF018 = item.getItemF018();
		this.itemF019 = item.getItemF019();
		this.itemF020 = item.getItemF020();
		this.itemF021 = item.getItemF021();
		this.itemF022 = item.getItemF022();
		this.testYm = item.getTestYm();

	}

}