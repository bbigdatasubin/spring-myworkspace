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

	// dataTime + itemCode 두개를 합치면 유일값 및 대표값이 됨 -> PK, Id(Identifier)
	// DB -> 합성키(Composite Key): 여러개의 컬럼을 합쳐서 Primary Key로 사용함
	// JPA -> 합성Id(Composite Id): 여러개의 필드를 합쳐서 Id로 사용함

	// 데이터의 중복 저장 방지용으로 id를 지정함

	private String ageClass; // 연령대
	private String ageDegree; // 나이
	@Id
	private String testYm; // 측정년월
	@Id
	private String itemF001; // 신장
	@Id
	private String itemF002; // 체중
	private String certGbn; // 상장구분
	private String itemF003; // 체지방율
	private String itemF005; // 최저혈압
	private String itemF006; // 최고혈압
	private String itemF007; // 악력
	private String itemF012; // 앉아윗몸앞으로굽히기
	private String itemF018; // BMI
	private String itemF019; // 교차윗몸일으키기
	private String itemF020; // 왕복오래달리기
	private String itemF021; // 10M 4회 왕복달리기
	private String itemF022; // 제자리 멀리뛰기

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