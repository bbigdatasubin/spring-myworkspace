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

	// dataTime + itemCode 두개를 합치면 유일값 및 대표값이 됨 -> PK, Id(Identifier)
	// DB -> 합성키(Composite Key): 여러개의 컬럼을 합쳐서 Primary Key로 사용함
	// JPA -> 합성Id(Composite Id): 여러개의 필드를 합쳐서 Id로 사용함

	// 데이터의 중복 저장 방지용으로 id를 지정함
	@Id
	private String date; // 발표시간
	@Id
	private String areaNo; // 지역번호
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