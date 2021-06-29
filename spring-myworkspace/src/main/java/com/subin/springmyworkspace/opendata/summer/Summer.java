package com.subin.springmyworkspace.opendata.summer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(SummerId.class)
public class Summer {

	// dataTime + itemCode �ΰ��� ��ġ�� ���ϰ� �� ��ǥ���� �� -> PK, Id(Identifier)
	// DB -> �ռ�Ű(Composite Key): �������� �÷��� ���ļ� Primary Key�� �����
	// JPA -> �ռ�Id(Composite Id): �������� �ʵ带 ���ļ� Id�� �����

	// �������� �ߺ� ���� ���������� id�� ������
	@Id
	private String date; // ��ǥ�ð�
	@Id
	private String areaNo; // ������ȣ
	private String h3;
	private String h6;
	private String h9;
	private String h12;
	private String h15;
	private String h18;
	private String h21;
	private String h24;
	private String h27;
	private String h30;
	private String h33;
	private String h36;
	private String h39;
	private String h42;
	private String h45;
	private String h48;

	public Summer(SummerResponse.Item item) {
		this.date = item.getDate();
		this.areaNo = item.getAreaNo();
		this.h3 = item.getH3();
		this.h6 = item.getH6();
		this.h9 = item.getH9();
		this.h12 = item.getH12();
		this.h15 = item.getH15();
		this.h18 = item.getH18();
		this.h21 = item.getH21();
		this.h24 = item.getH24();
		this.h27 = item.getH27();
		this.h30 = item.getH30();
		this.h33 = item.getH33();
		this.h36 = item.getH36();
		this.h39 = item.getH39();
		this.h42 = item.getH42();
		this.h45 = item.getH45();
		this.h48 = item.getH48();
	}

}