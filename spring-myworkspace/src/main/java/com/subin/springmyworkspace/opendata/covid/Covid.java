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
//	// dataTime + itemCode 두개를 합치면 유일값 및 대표값이 됨 -> PK, Id(Identifier)
//	// DB -> 합성키(Composite Key): 여러개의 컬럼을 합쳐서 Primary Key로 사용함
//	// JPA -> 합성Id(Composite Id): 여러개의 필드를 합쳐서 Id로 사용함
//
//	// 데이터의 중복 저장 방지용으로 id를 지정함
//
//	private String confCase; // 확진자
//	private String confCaseRate; // 확진률
//	@Id
//	private String createDt; // 등록일시분초
//	private String criticalRate; // 치명률
//	private String death; // 사망
//	private String deathRate; // 사망률
//	private String gubun; // 구분
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