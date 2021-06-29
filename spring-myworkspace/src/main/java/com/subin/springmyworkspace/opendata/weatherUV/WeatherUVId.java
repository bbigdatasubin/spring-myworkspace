package com.subin.springmyworkspace.opendata.weatherUV;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherUVId implements Serializable {
	private static final long serialVersionUID = 1743176488971107945L;
	private String areaNo;
	private String date;
}
