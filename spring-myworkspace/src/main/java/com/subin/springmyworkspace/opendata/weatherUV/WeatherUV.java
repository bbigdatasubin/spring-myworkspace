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

	// dataTime + itemCode �ΰ��� ��ġ�� ���ϰ� �� ��ǥ���� �� -> PK, Id(Identifier)
	// DB -> �ռ�Ű(Composite Key): �������� �÷��� ���ļ� Primary Key�� �����
	// JPA -> �ռ�Id(Composite Id): �������� �ʵ带 ���ļ� Id�� �����

	// �������� �ߺ� ���� ���������� id�� ������\

	@Id
	private String date; // ��ǥ�ð�
	@Id
	private String areaNo;
	private String today; // ���� ������
	private String tomorrow; // ���� ������
	private String theDayAfterTomorrow; // �� ������

	public WeatherUV(WeatherUVResponse.Item item) {
		this.date = item.getDate();
		this.today = item.getToday();
		this.tomorrow = item.getTomorrow();
		this.theDayAfterTomorrow = item.getTheDayAfterTomorrow();
		this.areaNo = item.getAreaNo();

	}

}