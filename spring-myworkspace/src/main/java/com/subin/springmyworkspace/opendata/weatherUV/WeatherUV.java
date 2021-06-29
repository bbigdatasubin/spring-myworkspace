package com.subin.springmyworkspace.opendata.weatherUV;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(WeatherUVId.class)
public class WeatherUV {

	// dataTime + itemCode 두개를 합치면 유일값 및 대표값이 됨 -> PK, Id(Identifier)
	// DB -> 합성키(Composite Key): 여러개의 컬럼을 합쳐서 Primary Key로 사용함
	// JPA -> 합성Id(Composite Id): 여러개의 필드를 합쳐서 Id로 사용함

	// 데이터의 중복 저장 방지용으로 id를 지정함\

	@Id
	private String date; // 발표시간
	@Id
	private String areaNo;
	private String today; // 오늘 예측값
	private String tomorrow; // 내일 예측값
	private String theDayAfterTomorrow; // 모레 예측값

	public WeatherUV(WeatherUVResponse.Item item) {
		this.date = item.getDate();
		this.today = item.getToday();
		this.tomorrow = item.getTomorrow();
		this.theDayAfterTomorrow = item.getTheDayAfterTomorrow();
		this.areaNo = item.getAreaNo();

	}

}